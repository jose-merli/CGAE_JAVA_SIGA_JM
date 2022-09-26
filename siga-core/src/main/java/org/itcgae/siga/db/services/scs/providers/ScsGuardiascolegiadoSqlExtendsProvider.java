package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
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
        
        sql.SELECT_DISTINCT("gc.idturno",
        		"(\r\n"
        		+ "        SELECT\r\n"
        		+ "            turno.nombre\r\n"
        		+ "        FROM\r\n"
        		+ "            scs_turno turno\r\n"
        		+ "        WHERE\r\n"
        		+ "                gc.idinstitucion = turno.idinstitucion\r\n"
        		+ "            AND gc.idturno = turno.idturno\r\n"
        		+ "    )          AS nombre");
        sql.FROM("scs_calendarioguardias  gc");
        sql.INNER_JOIN("SCS_TURNO turno ON gc.idinstitucion = turno.idinstitucion AND gc.idturno = turno.idturno",
                "SCS_CABECERAGUARDIAS CG on GC.IDINSTITUCION = CG.IDINSTITUCION " +
                        "AND GC.IDTURNO = CG.IDTURNO " +
                        "AND GC.IDGUARDIA = CG.IDGUARDIA " +
                        "AND GC.IDCALENDARIOGUARDIAS = CG.IDCALENDARIOGUARDIAS");
        sql.WHERE("turno.idinstitucion = " + idInstitucion);
        sql.WHERE("nvl(turno.idtipoturno, 2) = 2");
        sql.WHERE("TO_DATE('"+ guardiaDia +"', 'dd/MM/yyyy') BETWEEN trunc(gc.FECHAINICIO) AND trunc(gc.FECHAFIN)");
        if(idPersona != null
        		&& !"".equals(idPersona)) {
        	sql.WHERE("CG.IDPERSONA = " + idPersona);
        }
        sql.ORDER_BY("nombre");
        return sql.toString();
    }

    public String getGuardiasByTurnoColegiadoFecha(String idPersona, Short idInstitucion, String guardiaDia, String idTurno){
        SQL SQL = new SQL();

        if(!UtilidadesString.esCadenaVacia(idPersona)) {
            SQL.SELECT_DISTINCT("GUA.IDGUARDIA",
                    "GUA.NOMBRE");
            SQL.FROM("SCS_GUARDIASTURNO GUA");
            SQL.INNER_JOIN("SCS_CALENDARIOGUARDIAS GC ON GC.IDINSTITUCION = GUA.IDINSTITUCION " +
                            "AND GC.IDTURNO = GUA.IDTURNO " +
                            "AND GC.IDGUARDIA = GUA.IDGUARDIA ",
                    "SCS_CABECERAGUARDIAS CG ON GC.IDINSTITUCION = CG.IDINSTITUCION " +
                            "AND GC.IDTURNO = CG.IDTURNO " +
                            "AND GC.IDGUARDIA = CG.IDGUARDIA " +
                            "AND GC.IDCALENDARIOGUARDIAS = CG.IDCALENDARIOGUARDIAS");
            SQL.WHERE("GUA.IDTURNO = '" + idTurno + "'",
                    "GUA.IDINSTITUCION = '" + idInstitucion + "'",
                    "CG.IDPERSONA = '"+idPersona+"'",
                    "TO_DATE('"+ guardiaDia +"', 'dd/MM/yyyy') BETWEEN trunc(gc.FECHAINICIO) AND trunc(gc.FECHAFIN)");
            SQL.ORDER_BY("GUA.NOMBRE");
        }else{
            SQL.SELECT_DISTINCT("GUA.IDGUARDIA", "GUA.NOMBRE");
            SQL.FROM("SCS_GUARDIASTURNO GUA");

            SQL.INNER_JOIN("SCS_CALENDARIOGUARDIAS GC ON GC.IDINSTITUCION = GUA.IDINSTITUCION " +
                            "AND GC.IDTURNO = GUA.IDTURNO " +
                            "AND GC.IDGUARDIA = GUA.IDGUARDIA ",
                    "SCS_CABECERAGUARDIAS CG ON GC.IDINSTITUCION = CG.IDINSTITUCION " +
                            "AND GC.IDTURNO = CG.IDTURNO " +
                            "AND GC.IDGUARDIA = CG.IDGUARDIA " +
                            "AND GC.IDCALENDARIOGUARDIAS = CG.IDCALENDARIOGUARDIAS");
            SQL.WHERE("GUA.IDTURNO = '" + idTurno + "'",
                    "GUA.IDINSTITUCION = '" + idInstitucion + "'",
                    "TO_DATE('"+ guardiaDia +"', 'dd/MM/yyyy') BETWEEN trunc(gc.FECHAINICIO) AND trunc(gc.FECHAFIN)");

            SQL.WHERE("GUA.FECHABAJA IS NULL");
            SQL.ORDER_BY("GUA.NOMBRE");
        }

        return SQL.toString();
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
        sql.WHERE("gc.idguardia IN ("+idGuardia+")");
        sql.AND();
        sql.WHERE("gc.idturno IN ("+idTurno+")");
        sql.AND();
        sql.WHERE("TO_DATE('"+guardiaDia+"', 'DD/MM/YYYY') BETWEEN trunc(gc.fechainicio) AND trunc(gc.fechafin)");
        sql.ORDER_BY("posicion,nombre");


        return sql.toString();
    }

    public String getGuardiasColegiadoNoSustitucion (TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem, Short idInstitucion){
        SQL SQL = new SQL();
        SQL SQL2 = new SQL();
        SQL SQL3 = new SQL();
        String fechaAsistencia = tarjetaAsistenciaResponseItem.getFechaAsistencia().substring(0,11);
        SQL2.SELECT("C.*");
        SQL2.FROM("SCS_GUARDIASCOLEGIADO C");
        SQL2.WHERE("IDPERSONA = '"+tarjetaAsistenciaResponseItem.getIdLetradoGuardia()+"'",
                "IDGUARDIA = '"+tarjetaAsistenciaResponseItem.getIdGuardia()+"'",
                "IDTURNO = '"+tarjetaAsistenciaResponseItem.getIdTurno()+"'",
                "FECHAFIN = TO_DATE('"+fechaAsistencia+"','DD/MM/YYYY')",
                "IDINSTITUCION = '"+idInstitucion+"'");

        SQL3.SELECT("C.IDGUARDIA");
        SQL3.FROM("SCS_GUARDIASCOLEGIADO C");
        SQL3.WHERE("IDPERSONA = '"+tarjetaAsistenciaResponseItem.getIdLetradoGuardia()+"'",
                "IDGUARDIA = '"+tarjetaAsistenciaResponseItem.getIdGuardia()+"'",
                "IDTURNO = '"+tarjetaAsistenciaResponseItem.getIdTurno()+"'",
                "FECHAFIN = TO_DATE('"+fechaAsistencia+"','DD/MM/YYYY')",
                "IDINSTITUCION = '"+idInstitucion+"'");

        SQL.SELECT("C.*");
        SQL.FROM("("+SQL2.toString()+") C");
        SQL.INNER_JOIN("SCS_GUARDIASTURNO G ON G.IDINSTITUCION = C.IDINSTITUCION AND G.IDTURNO = C.IDTURNO AND G.IDGUARDIA = C.IDGUARDIA");
        SQL.WHERE("G.IDGUARDIASUSTITUCION IS NULL OR (G.IDGUARDIASUSTITUCION IS NOT NULL AND G.IDGUARDIASUSTITUCION NOT IN ("+SQL3.toString()+"))");

        return SQL.toString();
    }

    public String getGuardiasColegiadoEnFecha (TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem, Short idInstitucion){
        SQL SQL = new SQL();

        String fechaAsistencia = tarjetaAsistenciaResponseItem.getFechaAsistencia().substring(0,11);
        SQL.SELECT("GC.IDPERSONA");
        SQL.FROM("SCS_GUARDIASCOLEGIADO GC");
        SQL.INNER_JOIN("SCS_CABECERAGUARDIAS CG ON GC.IDINSTITUCION =CG.IDINSTITUCION AND GC.IDTURNO =CG.IDTURNO AND GC.IDGUARDIA = CG.IDGUARDIA AND GC.IDPERSONA = CG.IDPERSONA AND GC.FECHAINICIO = CG.FECHAINICIO ");
        SQL.WHERE("GC.IDINSTITUCION = '"+idInstitucion+"'",
                " TO_DATE('"+fechaAsistencia+"', 'DD/MM/YYYY') =  GC.FECHAFIN",
                " GC.IDGUARDIA = '"+tarjetaAsistenciaResponseItem.getIdGuardia()+"'",
                " GC.IDTURNO = '"+tarjetaAsistenciaResponseItem.getIdTurno()+"'");
        SQL.ORDER_BY("CG.POSICION DESC");


        return SQL.toString();
    }

    public String getGuardiasColegiado(TarjetaAsistenciaResponseItem asistencia, Short idInstitucion, String idPersona){
        SQL SQL = new SQL();
        String fechaAsistencia = asistencia.getFechaAsistencia().substring(0,11);
        SQL.SELECT("GUACOL.*");
        SQL.FROM("SCS_GUARDIASCOLEGIADO GUACOL");
        SQL.INNER_JOIN("SCS_CABECERAGUARDIAS CAB ON GUACOL.IDINSTITUCION = CAB.IDINSTITUCION AND GUACOL.IDTURNO = CAB.IDTURNO AND GUACOL.IDGUARDIA = CAB.IDGUARDIA AND GUACOL.IDPERSONA = CAB.IDPERSONA AND GUACOL.FECHAINICIO = CAB.FECHAINICIO");
        SQL.WHERE("GUACOL.IDINSTITUCION = '"+idInstitucion+"'",
                "TO_DATE('"+fechaAsistencia+"', 'DD/MM/YYYY') BETWEEN CAB.FECHAINICIO AND CAB.FECHA_FIN",
                "GUACOL.IDGUARDIA = '"+asistencia.getIdGuardia()+"'",
                "GUACOL.IDTURNO = '"+asistencia.getIdTurno()+"'",
                "GUACOL.IDPERSONA = '"+idPersona+"'");


        return SQL.toString();
    }

    public String deleteGuardiasCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia) {

        SQL sql = new SQL();
        SQL sql2 = new SQL();

        sql2.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, IDPERSONA, FECHAINICIO");
        sql2.FROM("SCS_CABECERAGUARDIAS");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDCALENDARIOGUARDIAS = " + idCalendarioGuardias);
        sql2.WHERE("IDTURNO = " + idTurno);
        sql2.WHERE("IDGUARDIA = " + idGuardia);

        sql.DELETE_FROM("SCS_GUARDIASCOLEGIADO");
        sql.WHERE("(IDINSTITUCION, IDTURNO, IDGUARDIA, IDPERSONA, FECHAINICIO) in (" + sql2 + ")");

        return sql.toString();

    }

}

