package org.itcgae.siga.db.mappers;

import org.apache.log4j.Logger;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.db.entities.PysServicioanticipo;

import java.text.SimpleDateFormat;

public class PysLineaanticipoExtendsSqlProvider extends PysLineaanticipoSqlProvider {

    private Logger LOGGER = Logger.getLogger(PysLineaanticipoExtendsSqlProvider.class);

	public String selectByPersonIdAndCreationDate(Short institutionId, FiltroMonederoItem filter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SQL query = new SQL();
        query.SELECT("anti.fecha", "pers.nifcif", "pers.idpersona", "(pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre) as nombre_completo", "anti.idanticipo", "anti.descripcion", "anti.importeinicial as importe_inicial"
        		, "sum(nvl(linea.importeanticipado, 0)) as importe_usado", "anti.importeinicial- sum(nvl(linea.importeanticipado, 0)) as importe_restante");
        query.FROM("pys_anticipoletrado anti");
        query.INNER_JOIN("cen_persona pers on pers.idpersona = anti.idpersona");
        query.LEFT_OUTER_JOIN("pys_lineaanticipo linea on linea.idpersona = pers.idpersona and linea.idanticipo = anti.idanticipo");

        query.WHERE("anti.idinstitucion = " + institutionId);
        
        if (filter.getFechaDesde() != null ){

            String since = dateFormat.format(filter.getFechaDesde());
            
            query.WHERE("TO_CHAR(anti.FECHA, 'DD/MM/YYYY') >= TO_DATE('" + since
                    + "','DD/MM/YYYY') ");
        }
        
        if(filter.getFechaHasta() != null) {
        	String until = dateFormat.format(filter.getFechaHasta());
        	query.WHERE(" TO_CHAR(anti.FECHA, 'DD/MM/YYYY') <= TO_DATE('"
                    + until + "','DD/MM/YYYY')");
        }
        
        if(filter.getIdPersonaColegiado() != null) {
        	query.WHERE("pers.idpersona = "+filter.getIdPersonaColegiado());
        }

        query.GROUP_BY("anti.idanticipo", "anti.FECHA", "pers.NIFCIF", "pers.idpersona", "anti.idanticipo", "pers.APELLIDOS1", "pers.APELLIDOS2", "pers.NOMBRE", "anti.descripcion", "anti.importeinicial");
        
        query.ORDER_BY("anti.FECHA desc");
        
        LOGGER.info("CONSULTA DE LISTA DE BUSQUEDA DE MONEDEROS: \r\n" + query.toString());



        return query.toString();
    }
    
    public String selectMaxIdLinea(Short idInstitucion, Long idPersona) {
    	SQL query = new SQL();
    	
    	query.SELECT("nvl(MAX(idLinea), 0) as id");
    	
    	query.FROM("PYS_LINEAANTICIPO lineas");
    	
    	query.WHERE("idpersona = "+idPersona);
    	query.WHERE("idInstitucion = "+idInstitucion);
    	
    	return query.toString();
    	
    }
    
    public String getListaMovimientosMonedero(Short idInstitucion, String idAnticipo, String idPersona) {
    	
    	SQL queryAnti = new SQL();
    	
    	queryAnti.SELECT("anti.fecha"); 
    	queryAnti.SELECT("anti.descripcion as concepto"); 
    	queryAnti.SELECT("anti.CTACONTABLE as cuentacontable "); 
    	queryAnti.SELECT("anti.importeinicial as importe"); 
    	queryAnti.SELECT("0 as nuevo");

    	queryAnti.FROM("pys_anticipoletrado anti"); 
        
        queryAnti.WHERE("anti.idinstitucion = " + idInstitucion);
        queryAnti.WHERE("anti.idPersona = " + idPersona);
        queryAnti.WHERE("anti.idanticipo = " + idAnticipo);
        
        
        SQL queryLineas = new SQL();
        
        queryLineas.SELECT("linea.fechaefectiva as fecha"); 
    	queryLineas.SELECT("servIns.DESCRIPCION as concepto"); 
    	queryLineas.SELECT("'' as cuentacontable "); 
    	queryLineas.SELECT("-linea.importeanticipado as importe"); 
    	queryLineas.SELECT("0 as nuevo");

    	queryLineas.FROM("pys_anticipoletrado anti"); 
    	queryLineas.INNER_JOIN("pys_lineaanticipo linea on linea.idinstitucion = anti.idinstitucion and linea.idanticipo = anti.idanticipo "
    			+ "and linea.idpersona = anti.idpersona ");
    	queryLineas.LEFT_OUTER_JOIN("pys_servicioanticipo servAnti on servAnti.IDINSTITUCION = linea.idInstitucion and servAnti.IDPERSONA = linea.idPersona and servAnti.IDANTICIPO = anti.idanticipo");
    	queryLineas.LEFT_OUTER_JOIN("fac_facturacionsuscripcion factServ on linea.idinstitucion = factServ.idinstitucion and linea.numerolinea = factServ.numerolinea "
    			+ "and linea.idfactura = factServ.idfactura");
    	queryLineas.LEFT_OUTER_JOIN("pys_serviciosinstitucion servIns on servIns.idinstitucion = factServ.idinstitucion and factServ.idservicio = servIns.idservicio "
    			+ "and factServ.idtiposervicios = servIns.idtiposervicios and factServ.idserviciosinstitucion = servIns.idserviciosinstitucion");
    	
        queryLineas.WHERE("anti.idinstitucion = " + idInstitucion);
        queryLineas.WHERE("anti.idPersona = " + idPersona);
        queryLineas.WHERE("anti.idanticipo = " + idAnticipo);        
        
    	
        String query =  queryLineas.toString() +" \r\n UNION \r\n"+ queryAnti.toString()+ " order by fecha desc";

        LOGGER.info("CONSULTA DE LISTA DE BUSQUEDA DE MOVIMIENTOS DE UN MONEDERO: \r\n" + query.toString());
        
    	return query;
    }
    
public String getListaServiciosMonedero(Short idInstitucion, String idAnticipo, String idPersona) {
    	
    	SQL query = new SQL();
    	
    	query.SELECT("servAnti.fechaModificacion"); 
    	query.SELECT("servin.descripcion"); 
    	query.SELECT("servin.idServicio");
    	query.SELECT("servin.idServiciosInstitucion");
    	query.SELECT("servin.idTipoServicios");
    	query.SELECT("  DECODE(\r\n"
				+ " valor_minimo,\r\n"
				+ " NULL,\r\n"
				+ " 'Sin precio',\r\n"
				+ " (valor_minimo\r\n"
				+ "  || '/'\r\n"
				+ " || f_siga_getrecurso(\r\n"
				+ " perio_min.descripcion,\r\n"
				+ " 1\r\n"
				+ " )\r\n"
				+ " || ' - '\r\n"
				+ " || valor_maximo\r\n"
				+ " || '/'\r\n"
				+ " || f_siga_getrecurso(\r\n"
				+ " perio_max.descripcion,\r\n"
				+ " 1\r\n"
				+ " ) )\r\n"
				+ " ) precio");
    	
    	query.FROM("pys_anticipoletrado anti");
    	query.LEFT_OUTER_JOIN(" pys_periodicidad perio_min on perio_min.idperiodicidad = a.id_periodicidad_minima");
    	query.LEFT_OUTER_JOIN(" pys_periodicidad perio_max on perio_max.idperiodicidad = a.id_periodicidad_maxima");
    	query.INNER_JOIN("pys_servicioanticipo servAnti on servAnti.IDINSTITUCION = anti.idInstitucion and servAnti.IDPERSONA = anti.idPersona and servAnti.IDANTICIPO = anti.idanticipo");
		
    	query.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = anti.idpersona");
        query.INNER_JOIN(" pys_serviciosinstitucion servin on servin.idtiposervicios = servAnti.idtiposervicios and "+
				"servin.idserviciosinstitucion = servAnti.idserviciosinstitucion and "+
				"servin.idservicio = servAnti.idservicio and servin.idinstitucion = anti.idinstitucion");
        query.INNER_JOIN("(\r\n"
    			+ "	        SELECT\r\n"
    			+ "	            MIN(precioserv_min.valor) valor_minimo,\r\n"
    			+ "	            MIN(precioserv_min.idperiodicidad) id_periodicidad_minima,\r\n"
    			+ "	            MAX(precioserv_max.valor) valor_maximo,\r\n"
    			+ "	            MAX(precioserv_max.idperiodicidad) id_periodicidad_maxima,\r\n"
    			+ "	            servin.idinstitucion,\r\n"
    			+ "	            servin.idservicio,\r\n"
    			+ "	            servin.idtiposervicios,\r\n"
    			+ "	            servin.idserviciosinstitucion\r\n"
    			+ "	        FROM\r\n"
    			+ "	            pys_serviciosinstitucion servin,\r\n"
    			+ "	            pys_preciosservicios precioserv_min,\r\n"
    			+ "	            pys_periodicidad perio_min,\r\n"
    			+ "	            pys_preciosservicios precioserv_max,\r\n"
    			+ "	            pys_periodicidad perio_max\r\n"
    			+ "	        WHERE  precioserv_min.idservicio (+) = servin.idservicio\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_min.idtiposervicios (+) = servin.idtiposervicios\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_min.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_min.idinstitucion (+) = servin.idinstitucion\r\n"
    			+ "	            AND\r\n"
    			+ "	                perio_min.idperiodicidad (+) = precioserv_min.idperiodicidad\r\n"
    			+ "	            AND\r\n"
    			+ "	                ( precioserv_min.valor / perio_min.periodosmes ) = (\r\n"
    			+ "	                    SELECT\r\n"
    			+ "	                        MIN(precioserv.valor / perio.periodosmes)\r\n"
    			+ "	                    FROM\r\n"
    			+ "	                        pys_preciosservicios precioserv,\r\n"
    			+ "	                        pys_periodicidad perio\r\n"
    			+ "	                    WHERE\r\n"
    			+ "	                            precioserv.idservicio (+) = servin.idservicio\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv.idtiposervicios (+) = servin.idtiposervicios\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv.idinstitucion (+) = servin.idinstitucion\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            perio.idperiodicidad (+) = precioserv.idperiodicidad\r\n"
    			+ "	                )\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_max.idservicio (+) = servin.idservicio\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_max.idtiposervicios (+) = servin.idtiposervicios\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_max.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
    			+ "	            AND\r\n"
    			+ "	                precioserv_max.idinstitucion (+) = servin.idinstitucion\r\n"
    			+ "	            AND\r\n"
    			+ "	                perio_max.idperiodicidad (+) = precioserv_max.idperiodicidad\r\n"
    			+ "	            AND\r\n"
    			+ "	                ( precioserv_max.valor / perio_max.periodosmes ) = (\r\n"
    			+ "	                    SELECT\r\n"
    			+ "	                        MAX(precioserv.valor / perio.periodosmes)\r\n"
    			+ "	                    FROM\r\n"
    			+ "	                        pys_preciosservicios precioserv,\r\n"
    			+ "	                        pys_periodicidad perio\r\n"
    			+ "	                    WHERE\r\n"
    			+ "	                            precioserv.idservicio (+) = servin.idservicio\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv.idtiposervicios (+) = servin.idtiposervicios\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv.idserviciosinstitucion (+) = servin.idserviciosinstitucion\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv.idinstitucion (+) = servin.idinstitucion\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            perio.idperiodicidad (+) = precioserv.idperiodicidad\r\n"
    			+ "	                )\r\n"
    			+ "	        GROUP BY\r\n"
    			+ "	            servin.idinstitucion,\r\n"
    			+ "	            servin.idservicio,\r\n"
    			+ "	            servin.idtiposervicios,\r\n"
    			+ "	            servin.idserviciosinstitucion\r\n"
    			+ "	        UNION\r\n"
    			+ "	        SELECT\r\n"
    			+ "	            NULL,\r\n"
    			+ "	            NULL,\r\n"
    			+ "	            NULL,\r\n"
    			+ "	            NULL,\r\n"
    			+ "	            servin.idinstitucion,\r\n"
    			+ "	            servin.idservicio,\r\n"
    			+ "	            servin.idtiposervicios,\r\n"
    			+ "	            servin.idserviciosinstitucion\r\n"
    			+ "	        FROM\r\n"
    			+ "	            pys_serviciosinstitucion servin\r\n"
    			+ "	        WHERE NOT\r\n"
    			+ "	                EXISTS (\r\n"
    			+ "	                    SELECT\r\n"
    			+ "	                        1\r\n"
    			+ "	                    FROM\r\n"
    			+ "	                        pys_preciosservicios precioserv_min\r\n"
    			+ "	                    WHERE\r\n"
    			+ "	                            precioserv_min.idservicio = servin.idservicio\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv_min.idtiposervicios = servin.idtiposervicios\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv_min.idserviciosinstitucion = servin.idserviciosinstitucion\r\n"
    			+ "	                        AND\r\n"
    			+ "	                            precioserv_min.idinstitucion = servin.idinstitucion\r\n"
    			+ "	                )\r\n"
    			+ "	    ) a on a.idinstitucion = anti.idinstitucion and servin.idtiposervicios = a.idtiposervicios and servin.idserviciosinstitucion = a.idserviciosinstitucion and servin.idservicio = a.idservicio");
    	
        
        query.WHERE("anti.idPersona = "+ idPersona +" and anti.idinstitucion = " + idInstitucion + "  and anti.idAnticipo = "+idAnticipo);

        LOGGER.info("CONSULTA DE LISTA DE BUSQUEDA DE SERVICIOS DE UN MONEDERO: \r\n" + query.toString());
        
    	return query.toString();
    }

	public String getMonederoServicio(PysServicioanticipo servicio ) {
		
		SQL query = new SQL();
        query.SELECT("anti.fecha", "pers.nifcif", "pers.idpersona", "(pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre) as nombre_completo", "anti.idanticipo", "anti.descripcion", "anti.importeinicial as importe_inicial", 
        		"sum(nvl(linea.importeanticipado, 0)) as importe_usado","sum(anti.importeinicial)- sum(nvl(linea.importeanticipado, 0)) as importe_restante")
                .FROM("pys_anticipoletrado anti", "CEN_PERSONA pers")
                .LEFT_OUTER_JOIN("pys_lineaanticipo linea on linea.idpersona = pers.idpersona");

        query.WHERE("pers.idpersona = anti.idpersona");
        query.WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)");

        
        query.ORDER_BY("anti.FECHA desc");
        query.WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)");
        query.WHERE("anti.idinstitucion = " + servicio.getIdinstitucion());
        query.WHERE("anti.idPersona = "+ servicio.getIdpersona());
        query.WHERE("servAnti.idServicio = "+ servicio.getIdservicio());
        query.WHERE("servAnti.idTipoServicios = "+ servicio.getIdtiposervicios());
        query.WHERE("servAnti.idServiciosInstitucion = "+ servicio.getIdserviciosinstitucion());

        
        query.GROUP_BY("anti.idanticipo", "anti.FECHA", "pers.NIFCIF", "pers.idpersona", "anti.idanticipo", "pers.APELLIDOS1", "pers.APELLIDOS2", "pers.NOMBRE", "anti.descripcion", "anti.importeinicial", "linea.importeanticipado");
        
        query.HAVING("sum(anti.importeinicial)- sum(nvl(linea.importeanticipado, 0)) > 0 and rownum <= 1");
        
        query.ORDER_BY("consulta.fecha desc");
        
        return query.toString();
		
	}

}
