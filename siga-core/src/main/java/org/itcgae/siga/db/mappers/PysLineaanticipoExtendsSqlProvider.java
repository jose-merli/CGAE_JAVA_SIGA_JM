package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;

import java.text.SimpleDateFormat;

public class PysLineaanticipoExtendsSqlProvider extends PysLineaanticipoSqlProvider {


    public String selectByPersonIdAndCreationDate(Short institutionId, FiltroMonederoItem filter) {
    	
    	
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SQL query = new SQL();
        query.SELECT("min(anti.fecha) as fecha, pers.nifcif, pers.idpersona, (pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre) as nombre_completo, linea.idlinea, --(select a.descripcion from a where a.roworder = 1),  \r\n"
        		+ "anti.descripcion,\r\n"
        		+ "sum(anti.importeinicial) as importe_inicial, sum(nvl(linea.importeanticipado, 0)) as importe_usado , sum(anti.importeinicial)- sum(nvl(linea.importeanticipado, 0)) as importe_restante" );
       query.FROM("pys_anticipoletrado anti");
                
       query.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = anti.idpersona");
       query.LEFT_OUTER_JOIN("pys_lineaanticipo linea on linea.idpersona = pers.idpersona");

        query.WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)").AND()
                .WHERE("anti.idinstitucion = " + institutionId);

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

        query.GROUP_BY("pers.nifcif, pers.idpersona, linea.idlinea, (pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre)\r\n"
        		+ "    	, anti.descripcion");
        query.ORDER_BY("min(anti.FECHA) desc");
        
        return query.toString();
        
        
        
        //REVISAR CONSULTA
//        select min(anti.fecha) as fecha, pers.nifcif, pers.idpersona, (pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre) as nombre_completo, linea.idlinea, --(select a.descripcion from a where a.roworder = 1),  
//        anti.descripcion,
//        sum(anti.importeinicial) as importe_inicial, sum(nvl(linea.importeanticipado, 0)) as importe_usado , sum(anti.importeinicial)- sum(nvl(linea.importeanticipado, 0)) as importe_restante  
//        FROM pys_anticipoletrado anti
//        INNER JOIN CEN_PERSONA pers on pers.idpersona = anti.idpersona
//        LEFT OUTER JOIN pys_lineaanticipo linea on linea.idpersona = pers.idpersona
//        left outer join pys_anticipoletrado antiFirst on anti.idanticipo = antiFirst.idanticipo and anti.idanticipo = antiFirst.idanticipo
//        WHERE ((linea.idanticipo = anti.idanticipo or linea.idanticipo is null)) 
//        AND (anti.idinstitucion = 2005 AND TO_CHAR(anti.FECHA, 'DD/MM/YYYY') >= TO_DATE('26/11/2019','DD/MM/YYYY')  AND  TO_CHAR(anti.FECHA, 'DD/MM/YYYY') <= TO_DATE('26/11/2021','DD/MM/YYYY'))
//        group by  pers.nifcif, pers.idpersona, linea.idlinea, (pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre)
//        , anti.descripcion
//        ORDER BY min(anti.FECHA) desc;
    }
    
    public String selectMaxIdLinea(Short idInstitucion, Long idPersona) {
    	SQL query = new SQL();
    	
    	query.SELECT("nvl(MAX(idLinea), 0) as id");
    	
    	query.FROM("PYS_LINEAANTICIPO lineas");
    	
    	query.WHERE("idpersona = "+idPersona);
    	query.WHERE("idInstitucion = "+idInstitucion);
    	
    	return query.toString();
    	
    }
    
    public String getListaMovimientosMonedero(Short idInstitucion, String idLinea, String idPersona) {
    	
    	SQL query = new SQL();
    	
    	query.SELECT("anti.fecha"); 
    	query.SELECT("anti.descripcion as concepto"); 
    	query.SELECT("anti.CTACONTABLE as cuentacontable "); 
    	query.SELECT("anti.importeinicial"); 
    	query.SELECT("linea.liquidacion");
    	query.SELECT("linea.idfactura"); 
    	query.SELECT("linea.NUMEROLINEA as nlineafactura"); 
    	query.SELECT("servAnti.idservicio"); 
    	query.SELECT("servAnti.idserviciosinstitucion"); 
    	query.SELECT("servAnti.idtiposervicios"); 
    	query.SELECT("0 as nuevo");

    	query.FROM("pys_lineaanticipo linea"); 
    	query.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = linea.idpersona");
        query.LEFT_OUTER_JOIN("pys_anticipoletrado anti on linea.idpersona = pers.idpersona and (linea.idanticipo = anti.idanticipo or linea.idanticipo is null) and linea.idinstitucion = anti.idinstitucion");
        query.LEFT_OUTER_JOIN("pys_servicioanticipo servAnti on servAnti.IDINSTITUCION = linea.idInstitucion and servAnti.IDPERSONA = linea.idPersona and servAnti.IDANTICIPO = anti.idanticipo");

        query.WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)");
        query.WHERE("linea.idinstitucion = " + idInstitucion);
        query.WHERE("linea.idPersona = " + idPersona);
        query.WHERE("linea.idLinea = " + idLinea);
        
        query.ORDER_BY("anti.FECHA desc");
    	
    	return query.toString();
    }
    
public String getListaServiciosMonedero(Short idInstitucion, String idLinea, String idPersona) {
    	
    	SQL query = new SQL();
    	
    	query.SELECT("servAnti.fechaModificacion"); 
    	query.SELECT("servin.descripcion"); 
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
    	
    	query.FROM("pys_lineaanticipo linea");
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
    			+ "	    ) a on a.idinstitucion = linea.idinstitucion");
    	query.LEFT_OUTER_JOIN(" pys_periodicidad perio_min on perio_min.idperiodicidad = a.id_periodicidad_minima");
    	query.LEFT_OUTER_JOIN(" pys_periodicidad perio_max on perio_max.idperiodicidad = a.id_periodicidad_maxima");
    	query.INNER_JOIN("pys_anticipoletrado anti on anti.idinstitucion = linea.idinstitucion and anti.idPersona = linea.idPersona");
    	query.INNER_JOIN("pys_servicioanticipo servAnti on servAnti.IDINSTITUCION = linea.idInstitucion and servAnti.IDPERSONA = linea.idPersona and servAnti.IDANTICIPO = anti.idanticipo");
		
    	query.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = linea.idpersona");
        query.INNER_JOIN(" pys_serviciosinstitucion servin on servin.idtiposervicios = servAnti.idtiposervicios and "+
				"servin.idserviciosinstitucion = servAnti.idserviciosinstitucion and "+
				"servin.idservicio = servAnti.idservicio");
        
        query.WHERE("linea.idPersona = "+ idPersona +" and linea.idinstitucion = " + idInstitucion + "  and linea.idLinea = "+idLinea);
    	
    	return query.toString();
    }

}
