package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;

import java.text.SimpleDateFormat;

public class PysLineaanticipoExtendsSqlProvider extends PysLineaanticipoSqlProvider {


    public String selectByPersonIdAndCreationDate(Short institutionId, FiltroMonederoItem filter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SQL query = new SQL();
        query.SELECT("anti.fecha", "pers.nifcif", "pers.idpersona", "(pers.apellidos1 || ' ' || pers.apellidos2 || ', ' || pers.nombre) as nombre_completo", "anti.idanticipo", "anti.descripcion", "anti.importeinicial as importe_inicial", "sum(nvl(linea.importeanticipado, 0)) as importe_usado")
                .FROM("pys_anticipoletrado anti", "CEN_PERSONA pers")
                .LEFT_OUTER_JOIN("pys_lineaanticipo linea on linea.idpersona = pers.idpersona");

        query.WHERE("pers.idpersona = anti.idpersona").AND()
                .WHERE("(linea.idanticipo = anti.idanticipo or linea.idanticipo is null)").AND()
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

        query.GROUP_BY("anti.idanticipo", "anti.FECHA", "pers.NIFCIF", "pers.idpersona", "anti.idanticipo", "pers.APELLIDOS1", "pers.APELLIDOS2", "pers.NOMBRE", "anti.descripcion", "anti.importeinicial", "linea.importeanticipado");
        query.ORDER_BY("anti.FECHA desc")
        ;


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

}
