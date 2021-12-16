package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.db.entities.PysServicioanticipo;

import java.text.SimpleDateFormat;

public class PysLineaanticipoExtendsSqlProvider extends PysLineaanticipoSqlProvider {


    public String selectByPersonIdAndCreationDate(Short institutionId, FiltroMonederoItem filter) {
    	
    	
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        SQL subQuery = new SQL();
        subQuery.SELECT("anti.idinstitucion,\r\n"
        		+ "        		    MIN(anti.fecha) AS fecha,\r\n"
        		+ "        		    pers.nifcif,\r\n"
        		+ "        		   pers.idpersona,\r\n"
        		+ "        		    ( pers.apellidos1\r\n"
        		+ "        		      || ' '\r\n"
        		+ "        		      || pers.apellidos2\r\n"
        		+ "        		      || ', '\r\n"
        		+ "        		      || pers.nombre ) AS nombre_completo,\r\n"
        		+ "        		    linea.idlinea, \r\n"
        		+ "        		    SUM(anti.importeinicial) AS importe_inicial,\r\n"
        		+ "        		    SUM(nvl(linea.importeanticipado, 0)) AS importe_usado,\r\n"
        		+ "        		    SUM(anti.importeinicial) - SUM(nvl(linea.importeanticipado, 0)) AS importe_restante" );
                
       subQuery.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = anti.idpersona");
       subQuery.LEFT_OUTER_JOIN("pys_lineaanticipo linea on linea.idpersona = pers.idpersona and linea.idinstitucion = anti.idinstitucion");

        subQuery.WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)");
        subQuery.WHERE("anti.idinstitucion = " + institutionId);

        if (filter.getFechaDesde() != null ){

            String since = dateFormat.format(filter.getFechaDesde());
            
            subQuery.WHERE("TO_CHAR(anti.FECHA, 'DD/MM/YYYY') >= TO_DATE('" + since
                    + "','DD/MM/YYYY') ");
        }
        
        if(filter.getFechaHasta() != null) {
        	String until = dateFormat.format(filter.getFechaHasta());
        	subQuery.WHERE(" TO_CHAR(anti.FECHA, 'DD/MM/YYYY') <= TO_DATE('"
                    + until + "','DD/MM/YYYY')");
        }
        
        if(filter.getIdPersonaColegiado() != null) {
        	subQuery.WHERE("pers.idpersona = "+filter.getIdPersonaColegiado());
        }

        subQuery.GROUP_BY("anti.idinstitucion,\r\n"
        		+ "        		    pers.nifcif,\r\n"
        		+ "        		    pers.idpersona,\r\n"
        		+ "        		    linea.idlinea,\r\n"
        		+ "        		    ( pers.apellidos1\r\n"
        		+ "        		      || ' '\r\n"
        		+ "        		      || pers.apellidos2\r\n"
        		+ "        		      || ', '\r\n"
        		+ "        		      || pers.nombre )");
        
        SQL query = new SQL();
        
        query.SELECT("consulta.*");
        query.SELECT("select a.descripcion from pys_anticipoletrado a \r\n"
        		+ "        		where a.idinstitucion = consulta.idinstitucion \r\n"
        		+ "        		and a.idpersona = consulta.idpersona \r\n"
        		+ "        		and a.idanticipo = (select MIN(idanticipo) from pys_anticipoletrado b \r\n"
        		+ "        		inner join pys_lineaanticipo linea on  linea.idinstitucion = b.idinstitucion and b.idpersona = linea.idpersona "
        		+ "and b.idanticipo = linea.idanticipo and linea.idlinea = consulta.idlinea\r\n"
        		+ "        		where b.idinstitucion = consulta.idinstitucion \r\n"
        		+ "        		and b.idpersona = consulta.idpersona) descripcion" );
        
        query.FROM("("+subQuery.toString()+ ") consulta");
        
        query.ORDER_BY("consulta.fecha desc");
        
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
    	
    	query.FROM("pys_lineaanticipo linea");
    	query.LEFT_OUTER_JOIN(" pys_periodicidad perio_min on perio_min.idperiodicidad = a.id_periodicidad_minima");
    	query.LEFT_OUTER_JOIN(" pys_periodicidad perio_max on perio_max.idperiodicidad = a.id_periodicidad_maxima");
    	query.INNER_JOIN("pys_anticipoletrado anti on anti.idinstitucion = linea.idinstitucion and anti.idPersona = linea.idPersona");
    	query.INNER_JOIN("pys_servicioanticipo servAnti on servAnti.IDINSTITUCION = linea.idInstitucion and servAnti.IDPERSONA = linea.idPersona and servAnti.IDANTICIPO = anti.idanticipo");
		
    	query.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = linea.idpersona");
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
    			+ "	    ) a on a.idinstitucion = linea.idinstitucion and servin.idtiposervicios = a.idtiposervicios and servin.idserviciosinstitucion = a.idserviciosinstitucion and servin.idservicio = a.idservicio");
    	
        
        query.WHERE("linea.idPersona = "+ idPersona +" and linea.idinstitucion = " + idInstitucion + "  and linea.idLinea = "+idLinea);
    	
    	return query.toString();
    }

	public String getMonederoServicio(PysServicioanticipo servicio ) {
		
		SQL subQuery = new SQL();
        subQuery.SELECT("anti.idinstitucion,\r\n"
        		+ "        		    MIN(anti.fecha) AS fecha,\r\n"
        		+ "        		    pers.nifcif,\r\n"
        		+ "        		   pers.idpersona,\r\n"
        		+ "        		    ( pers.apellidos1\r\n"
        		+ "        		      || ' '\r\n"
        		+ "        		      || pers.apellidos2\r\n"
        		+ "        		      || ', '\r\n"
        		+ "        		      || pers.nombre ) AS nombre_completo,\r\n"
        		+ "        		    linea.idlinea, \r\n"
        		+ "        		    SUM(anti.importeinicial) AS importe_inicial,\r\n"
        		+ "        		    SUM(nvl(linea.importeanticipado, 0)) AS importe_usado,\r\n"
        		+ "        		    SUM(anti.importeinicial) - SUM(nvl(linea.importeanticipado, 0)) AS importe_restante" );
                
       subQuery.INNER_JOIN("CEN_PERSONA pers on pers.idpersona = anti.idpersona");
       subQuery.LEFT_OUTER_JOIN("pys_lineaanticipo linea on linea.idpersona = pers.idpersona and linea.idinstitucion = anti.idinstitucion");
       subQuery.INNER_JOIN("PYS_SERVICIOANTICIPOS servAnti on servAnti.idpersona = pers.idPersona and servAnti.idAnticipo =  anti.idAnticipo");
       
        subQuery.WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)");
        subQuery.WHERE("anti.idinstitucion = " + servicio.getIdinstitucion());
        subQuery.WHERE("anti.idPersona = "+ servicio.getIdpersona());
        subQuery.WHERE("servAnti.idServicio = "+ servicio.getIdservicio());
        subQuery.WHERE("servAnti.idTipoServicios = "+ servicio.getIdtiposervicios());
        subQuery.WHERE("servAnti.idServiciosInstitucion = "+ servicio.getIdserviciosinstitucion());

        subQuery.GROUP_BY("anti.idinstitucion,\r\n"
        		+ "        		    pers.nifcif,\r\n"
        		+ "        		    pers.idpersona,\r\n"
        		+ "        		    linea.idlinea,\r\n"
        		+ "        		    ( pers.apellidos1\r\n"
        		+ "        		      || ' '\r\n"
        		+ "        		      || pers.apellidos2\r\n"
        		+ "        		      || ', '\r\n"
        		+ "        		      || pers.nombre )");
        
        SQL query = new SQL();
        
        query.SELECT("consulta.*");
        query.SELECT("select a.descripcion from pys_anticipoletrado a \r\n"
        		+ "        		where a.idinstitucion = consulta.idinstitucion \r\n"
        		+ "        		and a.idpersona = consulta.idpersona \r\n"
        		+ "        		and a.idanticipo = (select MIN(idanticipo) from pys_anticipoletrado b \r\n"
        		+ "        		inner join pys_lineaanticipo linea on  linea.idinstitucion = b.idinstitucion and b.idpersona = linea.idpersona "
        		+ "and b.idanticipo = linea.idanticipo and linea.idlinea = consulta.idlinea\r\n"
        		+ "        		where b.idinstitucion = consulta.idinstitucion \r\n"
        		+ "        		and b.idpersona = consulta.idpersona) descripcion" );
        
        query.FROM("("+subQuery.toString()+ ") consulta");
        
        query.WHERE("IMPORTE_RESTANTE > 0 and rownum <= 1");
        
        query.ORDER_BY("consulta.fecha desc");
        
        return query.toString();
		
	}

}
