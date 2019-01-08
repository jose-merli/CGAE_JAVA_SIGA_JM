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
