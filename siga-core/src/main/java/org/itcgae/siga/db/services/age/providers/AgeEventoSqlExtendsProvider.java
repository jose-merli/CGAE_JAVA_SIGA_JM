package org.itcgae.siga.db.services.age.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.mappers.AgeEventoSqlProvider;

public class AgeEventoSqlExtendsProvider extends  AgeEventoSqlProvider{

		
	public String selectMaxEvent() {

		SQL sql = new SQL();

		sql.SELECT("max(IDEVENTO) AS IDEVENTO");
		sql.FROM("AGE_EVENTO");
		
		return sql.toString();
	}
	
	public String searchEvent(String idTipoEvento, String idCurso, String idInstitucion, String idLenguaje) {
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
		sql.SELECT("CAL.DESCRIPCION AS TIPOCALENDARIO");
		sql.SELECT("REC.DESCRIPCION AS TIPOEVENTO");
		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"FOR_EVENTO_CURSO ec on (evento.idEvento = ec.idEvento and evento.idinstitucion = ec.idinstitucion)");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.INNER_JOIN("AGE_TIPOEVENTOS TIPOEVENTOS ON TIPOEVENTOS.IDTIPOEVENTO = evento.IDTIPOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = TIPOEVENTOS.DESCRIPCION AND rec.IDLENGUAJE = '" + idLenguaje +"')");
		sql.WHERE("evento.FECHABAJA is NULL");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("ec.idCurso = '" + idCurso + "'");
		sql.WHERE("evento.idTipoEvento = '" + idTipoEvento + "'");

		return sql.toString();
	}
	
	public String getSessionsCourse(String idTipoEvento, String idCurso, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("evento.idevento");
		sql.SELECT("evento.idcalendario");
		sql.SELECT("evento.idinstitucion");
		sql.SELECT("evento.titulo");
		sql.SELECT("evento.fechainicio");
		sql.SELECT("evento.fechafin");
		sql.SELECT("concat(TO_CHAR(evento.fechainicio,'DD/MM/RRRR'), ' - ' || to_char(evento.fechainicio,'hh24:mi')) as fechaHoraInicio");
		sql.SELECT("concat(TO_CHAR(evento.fechafin,'DD/MM/RRRR'), ' - ' || to_char(evento.fechafin,'hh24:mi')) as fechaHoraFin");
		sql.SELECT("evento.lugar");
		sql.SELECT("evento.descripcion");
		sql.SELECT("evento.recursos");
		sql.SELECT("evento.idestadoevento");
		sql.SELECT("cat.descripcion as estadoEvento");
		sql.SELECT("evento.idtipoevento");
		sql.SELECT("cal.IDTIPOCALENDARIO");
		sql.SELECT("cal.DESCRIPCION AS TIPOCALENDARIO");
		sql.SELECT("REC.DESCRIPCION AS TIPOEVENTO");
		sql.SELECT("TO_CHAR(EVENTO.FECHAINICIO,'DD/MM/YYYY') AS FECHAINICIOSTRING");
		sql.SELECT("ec.IDCURSO");
		sql.SELECT("LISTAGG(CONCAT(PER.NOMBRE ||' ',CONCAT(DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) || ' ',PER.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER.NOMBRE) AS FORMADORES");
		
		sql.SELECT("REP.FECHAFIN AS FECHAFINREPETICION");
		sql.SELECT("REP.FECHAINICIO AS FECHAINICIOREPETICION");
		sql.SELECT("REP.TIPODIASREPETICION");
		sql.SELECT("REP.TIPOREPETICION");
		sql.SELECT("REP.VALORESREPETICION");
		sql.SELECT("EVENTO.IDEVENTOORIGINAL");
		sql.SELECT("EVENTO.IDREPETICIONEVENTO");
				
		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"FOR_EVENTO_CURSO ec on (evento.idEvento = ec.idEvento and evento.idinstitucion = ec.idinstitucion)");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.INNER_JOIN("AGE_ESTADOEVENTOS ESTADO ON EVENTO.IDESTADOEVENTO = ESTADO.IDESTADOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.LEFT_OUTER_JOIN("AGE_PERSONA_EVENTO PEREVENT ON (evento.idevento = perevent.idevento and PEREVENT.FECHABAJA is null)");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PER ON (PEREVENT.IDPERSONA = PER.IDPERSONA)");
		sql.INNER_JOIN("AGE_TIPOEVENTOS TIPOEVENTOS ON TIPOEVENTOS.IDTIPOEVENTO = evento.IDTIPOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = TIPOEVENTOS.DESCRIPCION AND rec.IDLENGUAJE = '" + idLenguaje +"')");
		sql.LEFT_OUTER_JOIN("AGE_REPETICIONEVENTO REP ON REP.IDREPETICIONEVENTO = EVENTO.IDREPETICIONEVENTO");

		sql.WHERE("evento.FECHABAJA is NULL");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("ec.idCurso = '" + idCurso + "'");
		sql.WHERE("evento.idTipoEvento = '" + idTipoEvento + "'");

		sql.GROUP_BY(
				"evento.idevento, evento.idcalendario, evento.idinstitucion, evento.titulo, evento.fechainicio, evento.fechafin, concat(TO_CHAR(evento.fechainicio,'DD/MM/RRRR'), ' - ' || to_char(evento.fechainicio,'hh24:mm')), concat(TO_CHAR(evento.fechafin,'DD/MM/RRRR'), ' - ' || to_char(evento.fechafin,'hh24:mm')), evento.lugar,  "
						+ "evento.descripcion, evento.recursos, evento.idestadoevento, cat.descripcion, evento.idtipoevento, cal.IDTIPOCALENDARIO, ec.IDCURSO,"
						+ "cal.DESCRIPCION, REC.DESCRIPCION, REP.FECHAFIN, REP.FECHAINICIO, REP.TIPODIASREPETICION, REP.TIPOREPETICION,"
						+ "REP.VALORESREPETICION, EVENTO.IDEVENTOORIGINAL, EVENTO.IDREPETICIONEVENTO");

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

	
	public String selectEventoFechaAuto(AgeEvento evento) {
		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
		
		sql.SELECT_DISTINCT("EVENTO.IDEVENTO");
		sql.FROM("AGE_EVENTO EVENTO");
		
		if(evento.getFechafin() != null) {
			sql.WHERE("EVENTO.FECHAFIN < TO_DATE('" + dateFormat.format(evento.getFechafin()) + "','dd/MM/RRRR hh24:mi:ss') ");
		}

		sql.WHERE("EVENTO.IDESTADOEVENTO = 1");
		return sql.toString();
	}
	
	public String searchEventByIdEvento(String idEvento, String idInstitucion, String idLenguaje) {
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
		sql.SELECT("CAL.DESCRIPCION AS TIPOCALENDARIO");
		sql.SELECT("REC.DESCRIPCION AS TIPOEVENTO");
		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.INNER_JOIN("AGE_TIPOEVENTOS TIPOEVENTOS ON TIPOEVENTOS.IDTIPOEVENTO = evento.IDTIPOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = TIPOEVENTOS.DESCRIPCION AND rec.IDLENGUAJE = '" + idLenguaje +"')");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("evento.idevento = '" + idEvento + "'");

		return sql.toString();
	}
	
	public String getRepeteadEvents(String idEvento, String idInstitucion, String idLenguaje) {
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
		sql.SELECT("CAL.DESCRIPCION AS TIPOCALENDARIO");
		sql.SELECT("REC.DESCRIPCION AS TIPOEVENTO");
		sql.SELECT("evento.idEventoOriginal");

		sql.FROM("AGE_EVENTO evento");
		sql.INNER_JOIN(
				"AGE_CALENDARIO cal on (evento.idCalendario = cal.idCalendario and evento.idinstitucion = cal.idinstitucion)");
		sql.INNER_JOIN("AGE_TIPOEVENTOS TIPOEVENTOS ON TIPOEVENTOS.IDTIPOEVENTO = evento.IDTIPOEVENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = TIPOEVENTOS.DESCRIPCION AND rec.IDLENGUAJE = '" + idLenguaje +"')");
		sql.WHERE("evento.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("evento.idEventoOriginal = '" + idEvento + "'");

		return sql.toString();
	}
	
		
		

		
		// Colegio
	
		// Consejo
		
	public String searchFestivos(EventoItem eventoItem, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("DECODE(age_evento.TITULO,'Fiesta Local', age_evento.IDINSTITUCION, null) as IDINSTITUCION");
		sql.SELECT("DECODE(age_evento.TITULO,'Fiesta Local', age_evento.idevento, null) as idevento");
		sql.SELECT("age_evento.FECHAINICIO");
		sql.SELECT("age_evento.FECHAINICIO as FECHAINICIOOLD");
		sql.SELECT("age_evento.FECHAFIN");
		sql.SELECT("age_evento.DESCRIPCION AS NOMBRE"); 
		sql.SELECT("age_evento.DESCRIPCION as DESCRIPCIONOLD"); 
		sql.SELECT("age_evento.DESCRIPCION"); 
		sql.SELECT("age_evento.TITULO"); 
		sql.SELECT("TO_DATE(age_evento.FECHABAJA, 'dd/MM/yyyy') as FECHABAJA");
		sql.SELECT("age_evento.RECURSOS"); 
		sql.SELECT("age_evento.LUGAR");
		sql.SELECT("DECODE(age_evento.TITULO,'Fiesta Local',CEN_PARTIDOJUDICIAL.NOMBRE,age_evento.TITULO) as FIESTALOCALPARTIDO");
		sql.SELECT("age_evento.IDTIPOEVENTO");
		sql.SELECT("AGE_CALENDARIO.IDTIPOCALENDARIO");
		
		sql.FROM("age_evento age_evento");
		sql.LEFT_OUTER_JOIN("CEN_PARTIDOJUDICIAL CEN_PARTIDOJUDICIAL ON CEN_PARTIDOJUDICIAL.IDPARTIDO = age_evento.LUGAR");
		sql.LEFT_OUTER_JOIN("CEN_INFLUENCIA CEN_INFLUENCIA ON CEN_PARTIDOJUDICIAL.IDPARTIDO = CEN_INFLUENCIA.IDPARTIDO AND CEN_INFLUENCIA.IDINSTITUCION = age_evento.IDINSTITUCION");
		sql.INNER_JOIN("AGE_CALENDARIO AGE_CALENDARIO on age_evento.idCalendario = AGE_CALENDARIO.idCalendario and age_evento.idinstitucion = AGE_CALENDARIO.idinstitucion");
		sql.LEFT_OUTER_JOIN("CEN_INSTITUCION CEN_INSTITUCION ON CEN_INSTITUCION.IDINSTITUCION = AGE_EVENTO.IDINSTITUCION");

		
		// Colegio
		if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100")) {
			sql.WHERE("age_evento.IDINSTITUCION = '" + idInstitucion + "'");
	
		// Consejo
		} else if(!idInstitucion.equals(SigaConstants.IDINSTITUCION_2000)) {
			sql.WHERE("(age_evento.IDINSTITUCION = '" + idInstitucion + "' or cen_institucion.CEN_INST_IDINSTITUCION = '" + idInstitucion + "')");
		}
		
		sql.WHERE("age_evento.idtipoevento = '9'");
		sql.WHERE("AGE_CALENDARIO.IDTIPOCALENDARIO like '2'");
		sql.WHERE("age_evento.descripcion not like 'No Laborable'");
		
		if(eventoItem.getAnio() != null && eventoItem.getAnio() != "") {
			sql.WHERE("extract(year from age_evento.FECHAINICIO) = '" + eventoItem.getAnio() + "'");
		}
		
		if(!eventoItem.isHistorico()) {
			sql.WHERE("age_evento.FECHABAJA is null");
		}
		
		sql.ORDER_BY("age_evento.FECHAINICIO");
		
		return sql.toString();
	}
}
