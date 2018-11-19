package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeCalendarioSqlProvider;

public class AgeCalendarioSqlExtendsProvider extends  AgeCalendarioSqlProvider{

		
	public String selectMaxCalendar() {

		SQL sql = new SQL();

		sql.SELECT("max(IDCALENDARIO) AS IDCALENDARIO");
		sql.FROM("AGE_CALENDARIO");
		
		return sql.toString();
	}
	
	public String getCalendars(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("cal.IDCALENDARIO");
		sql.SELECT("cal.DESCRIPCION");
		sql.FROM("AGE_CALENDARIO cal");
//		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipocuando.DESCRIPCION AND rec.IDLENGUAJE = '"
//				+ idLenguaje + "')");
		sql.WHERE("cal.IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("cal.DESCRIPCION");
		
		return sql.toString();
	}
	
	public String getCalendariosPermisos(Short idInstitucion, String perfiles) {
		
		SQL sql = new SQL();

		sql.SELECT("AGE.IDCALENDARIO");
		sql.SELECT("AGE.IDINSTITUCION");
		sql.SELECT("AGE.DESCRIPCION");
		sql.SELECT("AGE.USUMODIFICACION");
		sql.SELECT("AGE.FECHAMODIFICACION");
		sql.SELECT("AGE.FECHABAJA");
		sql.SELECT("AGE.IDTIPOCALENDARIO");
		sql.SELECT("AGE.COLOR");
		sql.SELECT("NVL(DECODE(MIN(DECODE(AGE_PER.TIPOACCESO,0,5,AGE_PER.TIPOACCESO)),5,0,MIN(DECODE(AGE_PER.TIPOACCESO,0,5,AGE_PER.TIPOACCESO))),0) AS TIPOACCESO");
		
		sql.FROM("AGE_CALENDARIO AGE");
		sql.LEFT_OUTER_JOIN("AGE_PERMISOSCALENDARIO AGE_PER ON AGE.IDCALENDARIO = AGE_PER.IDCALENDARIO AND AGE_PER.IDPERFIL IN (" + perfiles + ")");
		
		sql.WHERE("AGE.IDINSTITUCION = '" + String.valueOf(idInstitucion) + "'");
		
		sql.GROUP_BY("AGE.IDCALENDARIO, AGE.IDINSTITUCION, AGE.DESCRIPCION, AGE.USUMODIFICACION, AGE.FECHAMODIFICACION, AGE.FECHABAJA, AGE.IDTIPOCALENDARIO, AGE.COLOR");
		
		return sql.toString();
	}
	
	public String getCalendarioEventos(Short idInstitucion, String perfiles, String idCalendario) {
		
		SQL sql = new SQL();

		sql.SELECT("AGE.IDCALENDARIO");
		sql.SELECT("AGE.COLOR");
		sql.SELECT("EVENTO.IDINSTITUCION");
		sql.SELECT("EVENTO.IDEVENTO");
		sql.SELECT("EVENTO.IDESTADOEVENTO");
		sql.SELECT("EVENTO.IDTIPOEVENTO");
		sql.SELECT("EVENTO.FECHAINICIO");
		sql.SELECT("EVENTO.FECHAFIN");
		sql.SELECT("EVENTO.TITULO");
		sql.SELECT("EVENTO.LUGAR");
		sql.SELECT("EVENTO.DESCRIPCION");
		sql.SELECT("EVENTO.RECURSOS");
		sql.SELECT("NVL(DECODE(MIN(DECODE(AGE_PER.TIPOACCESO,0,5,AGE_PER.TIPOACCESO)),5,0,MIN(DECODE(AGE_PER.TIPOACCESO,0,5,AGE_PER.TIPOACCESO))),0) AS TIPOACCESO");
		
		sql.FROM("AGE_EVENTO EVENTO");
		sql.INNER_JOIN("AGE_CALENDARIO AGE ON AGE.IDCALENDARIO = EVENTO.IDCALENDARIO");
		sql.LEFT_OUTER_JOIN("AGE_PERMISOSCALENDARIO AGE_PER ON AGE.IDCALENDARIO = AGE_PER.IDCALENDARIO AND AGE_PER.IDPERFIL IN (" + perfiles + ")");
		
		sql.WHERE("AGE.IDINSTITUCION = '" + String.valueOf(idInstitucion) + "'");
		sql.WHERE("AGE.IDCALENDARIO = '" + idCalendario + "'");
		sql.WHERE("AGE.FECHABAJA IS NULL");
		
		sql.GROUP_BY("AGE.IDCALENDARIO, AGE.COLOR, EVENTO.IDEVENTO, EVENTO.FECHAINICIO, EVENTO.FECHAFIN, EVENTO.TITULO, EVENTO.LUGAR, EVENTO.DESCRIPCION, EVENTO.RECURSOS, EVENTO.IDTIPOEVENTO, EVENTO.IDESTADOEVENTO, EVENTO.IDINSTITUCION");
		
		return sql.toString();
	}
	
}
