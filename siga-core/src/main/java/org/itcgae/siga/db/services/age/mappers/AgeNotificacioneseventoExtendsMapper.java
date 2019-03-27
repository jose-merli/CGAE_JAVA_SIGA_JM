package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.db.mappers.AgeNotificacioneseventoMapper;
import org.itcgae.siga.db.services.age.providers.AgeNotificacioneseventoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeNotificacioneseventoExtendsMapper extends AgeNotificacioneseventoMapper{
			
	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "getCalendarNotifications")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "idTipoNotificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDUNIDADMEDIDA", property = "idUnidadMedida", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCIONCUANDO", property = "descripcionCuando", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCUANDO", property = "idTipoCuando", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRETIPONOTIFICACION", property = "nombreTipoNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDNOTIFICACIONEVENTO", property = "idNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONMEDIDA", property = "descripcionMedida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONANTES", property = "descripcionAntes", jdbcType = JdbcType.VARCHAR),
		
	})
	List<NotificacionEventoItem> getCalendarNotifications(String idCalendario, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "getHistoricCalendarNotifications")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "idTipoNotificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDUNIDADMEDIDA", property = "idUnidadMedida", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCIONCUANDO", property = "descripcionCuando", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCUANDO", property = "idTipoCuando", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRETIPONOTIFICACION", property = "nombreTipoNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDNOTIFICACIONEVENTO", property = "idNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONMEDIDA", property = "descripcionMedida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONANTES", property = "descripcionAntes", jdbcType = JdbcType.VARCHAR)
		
	})
	List<NotificacionEventoItem> getHistoricCalendarNotifications(String idCalendario, String idInstitucion, String idLenguaje);

	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "getEventNotifications")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "idTipoNotificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDUNIDADMEDIDA", property = "idUnidadMedida", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCIONCUANDO", property = "descripcionCuando", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONMEDIDA", property = "descripcionMedida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONANTES", property = "descripcionAntes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCUANDO", property = "idTipoCuando", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRETIPONOTIFICACION", property = "nombreTipoNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDNOTIFICACIONEVENTO", property = "idNotificacion", jdbcType = JdbcType.VARCHAR)
		
	})
	List<NotificacionEventoItem> getEventNotifications(String idEvento, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "getHistoricEventNotifications")
	@Results({
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCALENDARIO", property = "idCalendario", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "idTipoNotificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDUNIDADMEDIDA", property = "idUnidadMedida", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCIONCUANDO", property = "descripcionCuando", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONMEDIDA", property = "descripcionMedida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONANTES", property = "descripcionAntes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCUANDO", property = "idTipoCuando", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRETIPONOTIFICACION", property = "nombreTipoNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDNOTIFICACIONEVENTO", property = "idNotificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)
		
	})
	List<NotificacionEventoItem> getHistoricEventNotifications(String idEvento, String idInstitucion, String idLenguaje);
}
