package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeEventoSqlProvider;

public class AgeEventoSqlExtendsProvider extends  AgeEventoSqlProvider{

		
	public String selectMaxEvent() {

		SQL sql = new SQL();

		sql.SELECT("max(IDEVENTO) AS IDEVENTO");
		sql.FROM("AGE_EVENTO");
		
		return sql.toString();
	}
	
	public String searchEvent(String idTipoEvento, String idCurso, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("evento.idevento");
		sql.SELECT("evento.idcalendario");
		sql.SELECT("evento.titulo");
		sql.SELECT("evento.fechainicio");
		sql.SELECT("evento.fechafin");
		sql.SELECT("evento.lugar");
		sql.SELECT("evento.descripcion");
		sql.SELECT("evento.recursos");
		sql.SELECT("evento.idestadoevento");
		sql.SELECT("evento.idtipoevento");
		sql.SELECT("cal.IDTIPOCALENDARIO");
		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"FOR_EVENTO_CURSO ec on (evento.idEvento = ec.idEvento and evento.idinstitucion = ec.idinstitucion)");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.WHERE("evento.FECHABAJA is NULL");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("ec.idCurso = '" + idCurso + "'");
		sql.WHERE("evento.idTipoEvento = '" + idTipoEvento + "'");

		return sql.toString();
	}
	
	public String getSessionsCourse(String idTipoEvento, String idCurso, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("evento.idevento");
		sql.SELECT("evento.idcalendario");
		sql.SELECT("evento.idinstitucion");
		sql.SELECT("evento.titulo");
		sql.SELECT("evento.fechainicio");
		sql.SELECT("evento.fechafin");
		sql.SELECT("concat(TO_CHAR(evento.fechainicio,'DD/MM/RRRR'), ' - ' || to_char(evento.fechainicio,'hh24:mm')) as fechaHoraInicio");
		sql.SELECT("concat(TO_CHAR(evento.fechafin,'DD/MM/RRRR'), ' - ' || to_char(evento.fechafin,'hh24:mm')) as fechaHoraFin");
		sql.SELECT("evento.lugar");
		sql.SELECT("evento.descripcion");
		sql.SELECT("evento.recursos");
		sql.SELECT("evento.idestadoevento");
		sql.SELECT("cat.descripcion as estadoEvento");
		sql.SELECT("evento.idtipoevento");
		sql.SELECT("cal.IDTIPOCALENDARIO");
		sql.SELECT("ec.IDCURSO");
		sql.SELECT("LISTAGG(CONCAT(PER.NOMBRE ||' ',CONCAT(DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) || ' ',PER.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER.NOMBRE) AS FORMADORES");
		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"FOR_EVENTO_CURSO ec on (evento.idEvento = ec.idEvento and evento.idinstitucion = ec.idinstitucion)");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.INNER_JOIN("AGE_ESTADOEVENTOS ESTADO ON EVENTO.IDESTADOEVENTO = ESTADO.IDESTADOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("AGE_PERSONA_EVENTO PEREVENT ON (evento.idevento = perevent.idevento and PEREVENT.FECHABAJA is null)");
		sql.INNER_JOIN("CEN_PERSONA PER ON (PEREVENT.IDPERSONA = PER.IDPERSONA)");
		
		sql.WHERE("evento.FECHABAJA is NULL");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("ec.idCurso = '" + idCurso + "'");
		sql.WHERE("evento.idTipoEvento = '" + idTipoEvento + "'");

		sql.GROUP_BY(
				"evento.idevento, evento.idcalendario, evento.idinstitucion, evento.titulo, evento.fechainicio, evento.fechafin, concat(TO_CHAR(evento.fechainicio,'DD/MM/RRRR'), ' - ' || to_char(evento.fechainicio,'hh24:mm')), concat(TO_CHAR(evento.fechafin,'DD/MM/RRRR'), ' - ' || to_char(evento.fechafin,'hh24:mm')), evento.lugar,  "
						+ "evento.descripcion, evento.recursos, evento.idestadoevento, cat.descripcion, evento.idtipoevento, cal.IDTIPOCALENDARIO, ec.IDCURSO");

		sql.ORDER_BY("evento.fechainicio");

		return sql.toString();
	}
	
	public String getSessionsCourseByState(String idTipoEvento, String idCurso, String idInstitucion, String idEstado) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("evento.idevento");
		sql.SELECT("evento.idcalendario");
		sql.SELECT("evento.titulo");
		sql.SELECT("evento.fechainicio");
		sql.SELECT("evento.fechafin");
		sql.SELECT("concat(evento.fechainicio, ' - ' || to_char(evento.fechainicio,'hh24:mm')) as fechaHoraInicio");
		sql.SELECT("concat(evento.fechafin, ' - ' || to_char(evento.fechafin,'hh24:mm')) as fechaHoraFin");
		sql.SELECT("evento.lugar");
		sql.SELECT("evento.descripcion");
		sql.SELECT("evento.recursos");
		sql.SELECT("evento.idestadoevento");
		sql.SELECT("cat.descripcion as estadoEvento");
		sql.SELECT("evento.idtipoevento");
		sql.SELECT("cal.IDTIPOCALENDARIO");
		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"FOR_EVENTO_CURSO ec on (evento.idEvento = ec.idEvento and evento.idinstitucion = ec.idinstitucion)");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.INNER_JOIN("AGE_ESTADOEVENTOS ESTADO ON EVENTO.IDESTADOEVENTO = ESTADO.IDESTADOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '1' )");

		sql.WHERE("evento.FECHABAJA is NULL");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("ec.idCurso = '" + idCurso + "'");
		sql.WHERE("evento.idTipoEvento = '" + idTipoEvento + "'");
		sql.WHERE("evento.idestadoevento = '" + idEstado + "'");
		
		sql.ORDER_BY("evento.fechainicio");

		return sql.toString();
	}

}
