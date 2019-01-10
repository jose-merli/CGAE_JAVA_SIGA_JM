package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
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
	EventoItem searchEvent(String idTipoEvento, String idCurso, String idInstitucion);
	
	@SelectProvider(type = AgeEventoSqlExtendsProvider.class, method = "getSessionsCourse")
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
		@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.NUMERIC),
	})
	List<EventoItem> getSessionsCourse(String idTipoEvento, String idCurso, String idInstitucion);
	
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
	List<EventoItem> getSessionsCourseByState(String idTipoEvento, String idCurso, String idInstitucion, String idEstado);
}
