package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.AgeCalendarioMapper;
import org.itcgae.siga.db.services.age.providers.AgeCalendarioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeCalendarioExtendsMapper extends AgeCalendarioMapper{
		
		
	@SelectProvider(type = AgeCalendarioSqlExtendsProvider.class, method = "selectMaxCalendar")
	@Results({
		@Result(column = "IDCALENDARIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectMaxCalendar();
	
	@SelectProvider(type = AgeCalendarioSqlExtendsProvider.class, method = "getCalendars")
	@Results({
		@Result(column = "IDCALENDARIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getCalendars(String idInstitucion);
	
	@SelectProvider(type = AgeCalendarioSqlExtendsProvider.class, method = "getCalendariosPermisos")
	@Results({
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCALENDARIO", property = "idTipoCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLOR", property = "color", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOACCESO", property = "tipoAcceso", jdbcType = JdbcType.NUMERIC),
	})
	List<CalendarItem> getCalendariosPermisos(Short idInstitucion, String perfiles);
	
	
	@SelectProvider(type = AgeCalendarioSqlExtendsProvider.class, method = "getCalendarioEventos")
	@Results({
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEVENTO", property = "id", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "start", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "end", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULO", property = "title", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLOR", property = "color", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOACCESO", property = "tipoAcceso", jdbcType = JdbcType.NUMERIC),
	})
	List<EventoItem> getCalendarioEventos(Short idInstitucion, String perfiles, String idCalendario);
	
}
