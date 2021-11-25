package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;

import java.text.SimpleDateFormat;

public class PysLineaanticipoExtendsSqlProvider extends PysLineaanticipoSqlProvider {


    public String selectByPersonIdAndCreationDate(Short institutionId, FiltroMonederoItem filter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SQL query = new SQL();
        query.SELECT("FIRST_VALUE(anti.fecha) OVER (ORDER BY anti.fecha) as fecha", "pers.nifcif", "pers.idpersona", "(pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre) as nombre_completo", 
        		"linea.idLinea", 
        		"FIRST_VALUE(anti.descripcion) OVER (ORDER BY anti.fecha) as descripcion", "anti.importeinicial as importe_inicial", "sum(nvl(linea.importeanticipado, 0)) as importe_usado", 
        		"anti.importeinicial - sum(nvl(linea.importeanticipado, 0))as importe_restante" );
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

        query.GROUP_BY("linea.idLinea", "anti.FECHA", "pers.NIFCIF", "pers.idpersona", "anti.idanticipo", "pers.APELLIDOS1", "pers.APELLIDOS2", "pers.NOMBRE", "anti.descripcion", "anti.importeinicial", "linea.importeanticipado");
        query.ORDER_BY("anti.FECHA desc");
        
        String sql = "select a.fecha, a.nifcif, a.idpersona, a.nombre_completo, a.idlinea, a.descripcion, sum(a.importe_inicial) as importe_inicial, sum(a.importe_usado) as importe_usado , sum(a.importe_inicial)- sum(a.importe_usado) as importe_restante from( \r\n"+ query + ") a \r\n"
        		+ "group by fecha, nifcif, idpersona, nombre_completo, idlinea, descripcion";


        return sql;
        
        
        
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

}
