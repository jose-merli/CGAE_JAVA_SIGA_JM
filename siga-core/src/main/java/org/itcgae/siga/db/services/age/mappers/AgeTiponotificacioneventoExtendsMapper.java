package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.AgeTiponotificacioneventoMapper;
import org.itcgae.siga.db.services.age.providers.AgeTiponotificacioneventoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeTiponotificacioneventoExtendsMapper extends AgeTiponotificacioneventoMapper {
	
	@SelectProvider(type = AgeTiponotificacioneventoSqlExtendsProvider.class, method = "getTypeNotifications")
	@Results({
		@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getTypeNotifications(String idLenguaje);
	
	@SelectProvider(type = AgeTiponotificacioneventoSqlExtendsProvider.class, method = "getNotificationTypeCalendarTraining")
	@Results({
		@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getNotificationTypeCalendarTraining(String idLenguaje);
	
}
