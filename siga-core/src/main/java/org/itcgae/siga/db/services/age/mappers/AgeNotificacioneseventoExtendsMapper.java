package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.db.entities.EnvDestinatarios;
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

	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "selectDestinatariosInscripcion")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoelectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POBLACIONEXTRANJERA", property = "poblacionextranjera", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPODESTINATARIO", property = "tipodestinatario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORIGENDESTINATARIO", property = "origendestinatario", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL) })
	List<EnvDestinatarios> selectDestinatariosInscripcion(Long idcurso, Short idInstitucion, String idEnvio, String usumodificacion );
	
	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "selectDestinatariosCurso")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoelectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POBLACIONEXTRANJERA", property = "poblacionextranjera", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPODESTINATARIO", property = "tipodestinatario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORIGENDESTINATARIO", property = "origendestinatario", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL) })
	List<EnvDestinatarios> selectDestinatariosCurso(Long idcurso, Short idInstitucion, String idEnvio, String usumodificacion);
	
	@SelectProvider(type = AgeNotificacioneseventoSqlExtendsProvider.class, method = "selectDestinatariosSesion")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoelectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POBLACIONEXTRANJERA", property = "poblacionextranjera", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPODESTINATARIO", property = "tipodestinatario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORIGENDESTINATARIO", property = "origendestinatario", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL) })
	List<EnvDestinatarios> selectDestinatariosSesion(Long idevento,Long idcurso,Short idInstitucion, String idEnvio, String usumodificacion);
}

