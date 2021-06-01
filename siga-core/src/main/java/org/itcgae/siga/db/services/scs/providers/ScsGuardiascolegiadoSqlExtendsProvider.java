package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;

public class ScsGuardiascolegiadoSqlExtendsProvider extends ScsCabeceraguardiasSqlProvider{

    public String getTurnosGuardias(String idPersona, Short idInstitucion) {
        SQL sql = new SQL();
        
        sql.SELECT("COUNT(1) AS COUNT");
        sql.FROM("scs_inscripcionturno");
        sql.WHERE("idinstitucion = " + idInstitucion);
        sql.WHERE("idpersona = " + idPersona);
        sql.WHERE("fechabaja IS NULL");
        sql.WHERE("fechavalidacion IS NOT NULL");
        
        return sql.toString();
    }
    
    public String getTurnosByColegiadoFecha(String idPersona, Short idInstitucion, String guardiaDia) {
    	SQL sql = new SQL();
        
        sql.SELECT_DISTINCT("turno.idinstitucion,"
        		+ " turno.idturno,"
        		+ " turno.nombre");
        sql.FROM("scs_turno turno ");
        sql.INNER_JOIN("scs_calendarioguardias   gc on gc.idturno = turno.idturno and gc.idinstitucion = turno.idinstitucion");
        sql.WHERE("turno.idinstitucion = " + idInstitucion);
        sql.WHERE("TO_DATE('"+ guardiaDia +"', 'dd/MM/yyyy') BETWEEN trunc(gc.fechainicio) AND trunc(gc.fechafin)");
        if(idPersona != null
        		&& !"".equals(idPersona)) {
        	sql.WHERE("gc.IDPERSONA_ULTIMOANTERIOR = " + idPersona);
        }
        return sql.toString();
    }
    
    public String getColegiadosGuardiaDia(String idTurno, String idGuardia, Short idInstitucion, String guardiaDia) {
    	SQL sql = new SQL();
        
        sql.SELECT_DISTINCT("per.idpersona,"
        		+ "    per.nombre"
        		+ "    || ' '"
        		+ "    || per.apellidos1"
        		+ "    || ' '"
        		+ "    || per.apellidos2    AS nombre,"
        		+ "    cg.posicion           posicion");
        sql.FROM("scs_guardiascolegiado  gc");
        sql.INNER_JOIN("scs_cabeceraguardias   cg on gc.idinstitucion = cg.idinstitucion AND gc.idturno = cg.idturno AND gc.idguardia = cg.idguardia AND gc.fechainicio = cg.fechainicio AND gc.idpersona = cg.idpersona");
        sql.INNER_JOIN("cen_persona per on per.idpersona = gc.idpersona");
        sql.WHERE("gc.idinstitucion = "+idInstitucion);
        sql.AND();
        sql.WHERE("gc.idguardia = '"+idGuardia+"'");
        sql.AND();
        sql.WHERE("gc.idturno = '"+idTurno+"'");
        sql.AND();
        sql.WHERE("'"+guardiaDia+"' BETWEEN trunc(gc.fechainicio) AND trunc(gc.fechafin)");
        sql.ORDER_BY("posicion,nombre");


        return sql.toString();
    }
    
    
}

