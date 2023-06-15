package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.mappers.AgeEventoMapper;
import org.itcgae.siga.db.services.age.providers.AgeEventoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeEventoExtendsMapper extends AgeEventoMapper{
		
		
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "selectMaxEvent")
	@Results({
		@Result(column = "IDEVENTO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectMaxEvent();
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "searchEvent")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCALENDARIO", property = "tipoCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOEVENTO", property = "tipoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULO", property = "titulo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "RECURSOS", property = "recursos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADOEVENTO", property = "idEstadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOEVENTO", property = "idTipoEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
	})
	EventoItem searchEvent(String idTipoEvento, String idCurso, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "getSessionsCourse")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULO", property = "titulo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHORAINICIO", property = "fechaHoraInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIOSTRING", property = "fechaInicioString", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHORAFIN", property = "fechaHoraFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "RECURSOS", property = "recursos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADOEVENTO", property = "idEstadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEVENTO", property = "estadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOEVENTO", property = "idTipoEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FORMADORES", property = "formadores", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFINREPETICION", property = "fechaFinRepeticion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIOREPETICION", property = "fechaInicioRepeticion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPODIASREPETICION", property = "tipoDiasRepeticion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOREPETICION", property = "tipoRepeticion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALORESREPETICION", property = "valoresRepeticionString", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEVENTOORIGINAL", property = "idEventoOriginal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDREPETICIONEVENTO", property = "idRepeticionEvento", jdbcType = JdbcType.VARCHAR),
	})
	List<EventoItem> getSessionsCourse(String idTipoEvento, String idCurso, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "getSessionsCourseByState")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULO", property = "titulo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHORAINICIO", property = "fechaHoraInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHORAFIN", property = "fechaHoraFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "RECURSOS", property = "recursos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADOEVENTO", property = "idEstadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEVENTO", property = "estadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOEVENTO", property = "idTipoEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
	})
	List<EventoItem> getSessionsCourseByState(String idTipoEvento, String idCurso, String idInstitucion, String idEstado, String idLenguaje);
	
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "selectEventoFechaAuto")
	@Results({ @Result(column = "IDEVENTO", property = "idevento", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.VARCHAR)
	})
	List<AgeEvento> selectEventoFechaAuto(AgeEvento ageEvento);
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "searchEventByIdEvento")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCALENDARIO", property = "tipoCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOEVENTO", property = "tipoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULO", property = "titulo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "RECURSOS", property = "recursos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADOEVENTO", property = "idEstadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOEVENTO", property = "idTipoEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
	})
	EventoItem searchEventByIdEvento(String idEvento, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "getRepeteadEvents")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCALENDARIO", property = "tipoCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOEVENTO", property = "tipoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULO", property = "titulo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "RECURSOS", property = "recursos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADOEVENTO", property = "idEstadoEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOEVENTO", property = "idTipoEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDEVENTOORIGINAL", property = "idEventoOriginal", jdbcType = JdbcType.VARCHAR),

	})
	List<EventoItem> getRepeteadEvents(String idEvento, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "searchFestivos")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TITULO", property = "titulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONOLD", property = "descripcionOld", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAINICIOOLD", property = "fechaInicioOld", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FIESTALOCALPARTIDO", property = "fiestaLocalPartido", jdbcType = JdbcType.NUMERIC),
		@Result(column = "RECURSOS", property = "recursos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOEVENTO", property = "idTipoEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
	})
	List<EventoItem> searchFestivos(EventoItem eventoItem, Short idInstitucion);
}