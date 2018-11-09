package org.itcgae.siga.age.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.age.service.IDatosNotificacionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample;
import org.itcgae.siga.db.mappers.AgeNotificacioneseventoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeNotificacioneseventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipocuandoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTiponotificacioneventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeUnidadmedidaExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.EnvPlantillasenviosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.EnvTipoenviosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatosNotificacionesServiceImpl implements IDatosNotificacionesService {

	private Logger LOGGER = Logger.getLogger(DatosNotificacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private AgeTiponotificacioneventoExtendsMapper ageTiponotificacioneventoExtendsMapper;

	@Autowired
	private AgeUnidadmedidaExtendsMapper ageUnidadmedidaExtendsMapper;

	@Autowired
	private AgeNotificacioneseventoMapper ageNotificacioneseventoMapper;

	@Autowired
	private AgeTipocuandoExtendsMapper ageTipocuandoExtendsMapper;

	@Autowired
	private EnvPlantillasenviosExtendsMapper envPlantillasenviosExtendsMapper;

	@Autowired
	private EnvTipoenviosExtendsMapper envTipoenviosExtendsMapper;

	@Autowired
	private AgeNotificacioneseventoExtendsMapper ageNotificacioneseventoExtendsMapper;

	@Override
	public ComboDTO getTypeNotifications(HttpServletRequest request) {
		LOGGER.info(
				"getTypeNotifications() -> Entrada al servicio para obtener los tipos de notificaciones de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTypeNotifications() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeNotifications() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeNotifications() / ageTiponotificacioneventoExtendsMapper.getTypeNotifications() -> Entrada a ageTipocalendarioExtendsMapper para obtener los diferentes tipos de notificaciones");
				comboItems = ageTiponotificacioneventoExtendsMapper.getTypeNotifications(usuario.getIdlenguaje());
				LOGGER.info(
						"getTypeNotifications() / ageTiponotificacioneventoExtendsMapper.getTypeNotifications() -> Salida de ageTipocalendarioExtendsMapper para obtener los diferentes tipos de notificaciones");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTypeNotifications() -> Salida del servicio para obtener notificaciones de eventos");

		return comboDTO;
	}

	@Override
	public ComboDTO getMeasuredUnit(HttpServletRequest request) {
		LOGGER.info(
				"getMeasuredUnit() -> Entrada al servicio para obtener las unidades de medida de las notificaciones de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getMeasuredUnit() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getMeasuredUnit() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getMeasuredUnit() / ageTiponotificacioneventoExtendsMapper.getTypeNotifications() -> Entrada a ageTipocalendarioExtendsMapper para obtener los diferentes tipos de notificaciones");
				comboItems = ageUnidadmedidaExtendsMapper.getMeasuredUnit(usuario.getIdlenguaje());
				LOGGER.info(
						"getMeasuredUnit() / ageTiponotificacioneventoExtendsMapper.getTypeNotifications() -> Salida de ageTipocalendarioExtendsMapper para obtener los diferentes tipos de notificaciones");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getMeasuredUnit() -> Salida del servicio para obtener las unidades de medida de las notificaciones de eventos");

		return comboDTO;
	}

	@Override
	public ComboDTO getTypeWhere(HttpServletRequest request) {
		LOGGER.info(
				"getTypeWhere() -> Entrada al servicio para obtener el tipo cuando de las notificaciones de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTypeWhere() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeWhere() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeWhere() / ageTipocuandoExtendsMapper.getTypeWhere() -> Entrada a ageTipocuandoExtendsMapper para obtener el tipo cuando de las notificaciones de eventos");
				comboItems = ageTipocuandoExtendsMapper.getTypeWhere(usuario.getIdlenguaje());
				LOGGER.info(
						"getTypeWhere() / ageTipocuandoExtendsMapper.getTypeWhere() -> Salida de ageTipocuandoExtendsMapper para obtener el tipo cuando de las notificaciones de eventos");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getTypeWhere() -> Salida del servicio para obtener el tipo cuando de las notificaciones de eventos");

		return comboDTO;
	}

	@Override
	public InsertResponseDTO saveNotification(NotificacionEventoItem notificacionEventoItem,
			HttpServletRequest request) {
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"saveNotification() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveNotification() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Creamos un nuevo calendario
				AgeNotificacionesevento ageNotificacionEventoInsert = new AgeNotificacionesevento();
				ageNotificacionEventoInsert.setIdnotificacionevento(Long.parseLong("5"));
				// IdCalendario es una Secuencia
				ageNotificacionEventoInsert.setIdevento(Long.parseLong("1"));
				ageNotificacionEventoInsert.setIdinstitucion(idInstitucion);
				ageNotificacionEventoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
				ageNotificacionEventoInsert.setFechamodificacion(new Date());
				ageNotificacionEventoInsert.setIdcalendario(Long.parseLong(notificacionEventoItem.getIdCalendario()));
				ageNotificacionEventoInsert
						.setIdtiponotificacionevento(Long.parseLong(notificacionEventoItem.getIdTipoNotificacion()));
				ageNotificacionEventoInsert.setCuando(Long.parseLong(notificacionEventoItem.getCuando()));
				ageNotificacionEventoInsert.setIdtipocuando(Long.parseLong(notificacionEventoItem.getIdTipoCuando()));
				ageNotificacionEventoInsert
						.setIdunidadmedida(Long.parseLong(notificacionEventoItem.getIdUnidadMedida()));
				ageNotificacionEventoInsert.setIdplantilla(Long.valueOf(notificacionEventoItem.getIdPlantilla()));
				ageNotificacionEventoInsert.setIdtipoenvios(Long.parseLong(notificacionEventoItem.getIdTipoEnvio()));
				ageNotificacionEventoInsert.setFechabaja(null);

				LOGGER.info(
						"saveNotification() / ageNotificacioneseventoMapper.insert(ageNotificacionEventoInsert) -> Entrada a ageNotificacioneseventoMapper para insertar una notificacion");
				response = ageNotificacioneseventoMapper.insert(ageNotificacionEventoInsert);
				LOGGER.info(
						"saveNotification() / ageNotificacioneseventoMapper.insert(ageNotificacionEventoInsert) -> Salida a ageNotificacioneseventoMapper para insertar una notificacion");

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al insertar nueva notificacion");
				} else {
					error.setCode(200);
				}
			}
		}
		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	public ComboPlantillaEnvioDTO getTemplates(HttpServletRequest request) {
		LOGGER.info("getTemplates() -> Entrada al servicio para obtener las plantillas de envio de notificacion");

		ComboPlantillaEnvioDTO comboDTO = new ComboPlantillaEnvioDTO();
		List<NotificacionEventoItem> notificaciones = new ArrayList<NotificacionEventoItem>();
		List<ComboPlantillaEnvioItem> comboItems = new ArrayList<ComboPlantillaEnvioItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"getTemplates() / envPlantillasenviosExtendsMapper.getTemplates() -> Entrada a envPlantillasenviosExtendsMapper para obtener las plantillas");
			notificaciones = envPlantillasenviosExtendsMapper.getTemplates(idInstitucion.toString());
			LOGGER.info(
					"getTemplates() / envPlantillasenviosExtendsMapper.getTemplates() -> Salida de envPlantillasenviosExtendsMapper para obtener las plantillas");

			for (NotificacionEventoItem noti : notificaciones) {
				ComboPlantillaEnvioItem n = new ComboPlantillaEnvioItem();
				n.setLabel(noti.getNombrePlantilla());
				n.setValue(noti);
				comboItems.add(n);
			}
		}

		comboDTO.setComboAgeItems(comboItems);

		LOGGER.info("getTemplates() -> Salida del servicio para obtener las plantillas de envio de notificacion");

		return comboDTO;
	}

	@Override
	public ComboDTO getTypeSend(String idPlantillaEnvio, String idTipoEnvio, HttpServletRequest request) {
		LOGGER.info(
				"getTypeSend() -> Entrada al servicio para obtener el tipo de envio de las notificaciones de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTypeSend() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeSend() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeSend() / ageTipocuandoExtendsMapper.getTypeWhere() -> Entrada a ageTipocuandoExtendsMapper para obtener el tipo cuando de las notificaciones de eventos");
				comboItems = envTipoenviosExtendsMapper.getTypeSend(idPlantillaEnvio, idTipoEnvio,
						idInstitucion.toString(), usuario.getIdlenguaje());
				LOGGER.info(
						"getTypeSend() / ageTipocuandoExtendsMapper.getTypeWhere() -> Salida de ageTipocuandoExtendsMapper para obtener el tipo cuando de las notificaciones de eventos");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getTypeSend() -> Salida del servicio para obtener el tipo de envio de las notificaciones de eventos");

		return comboDTO;
	}

	@Override
	public UpdateResponseDTO updateNotification(NotificacionEventoItem notificacionUpdate, HttpServletRequest request) {
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateNotification() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateNotification() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				AgeNotificacioneseventoExample exampleNotificacion = new AgeNotificacioneseventoExample();
				exampleNotificacion.createCriteria()
						.andIdnotificacioneventoEqualTo(Long.valueOf(notificacionUpdate.getIdNotificacion()))
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"updateCalendar() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Entrada a ageNotificacioneseventoMapper para buscar si existe la notificacion");

				List<AgeNotificacionesevento> notificationList = ageNotificacioneseventoMapper
						.selectByExample(exampleNotificacion);

				LOGGER.info(
						"updateCalendar() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Salida a ageNotificacioneseventoMapper para buscar si existe la notificacion");

				if (null != notificationList && notificationList.size() > 0) {
					AgeNotificacionesevento notification = notificationList.get(0);

					LOGGER.info(
							"updateCalendar() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Entrada a ageCalendarioExtendsMapper para modificar la notificacion");

					notification.setFechamodificacion(new Date());
					notification.setUsumodificacion(usuario.getIdusuario().longValue());
					notification.setIdplantilla(Long.valueOf(notificacionUpdate.getIdPlantilla()));
					notification.setIdtiponotificacionevento(Long.valueOf(notificacionUpdate.getIdTipoNotificacion()));
					notification.setCuando(Long.valueOf(notificacionUpdate.getCuando()));
					notification.setIdtipocuando(Long.valueOf(notificacionUpdate.getIdTipoCuando()));
					notification.setIdunidadmedida(Long.valueOf(notificacionUpdate.getIdUnidadMedida()));
					response = ageNotificacioneseventoMapper.updateByPrimaryKey(notification);

					LOGGER.info(
							"updateCalendar() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Salida a ageCalendarioExtendsMapper para modificar la notificacion");

				}

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al modificar nuevo calendario");
				} else {
					error.setCode(200);
				}
			}
		}

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public NotificacionEventoDTO getEventNotifications(String idCalendario, HttpServletRequest request) {
		LOGGER.info(
				"getEventNotifications() -> Entrada al servicio para obtener las notificaciones de eventos de un calendario especifico");

		List<NotificacionEventoItem> eventNotifications = new ArrayList<NotificacionEventoItem>();
		NotificacionEventoDTO eventNotificationDTO = new NotificacionEventoDTO();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			LOGGER.info(
					"getEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Entrada a ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");
			eventNotifications = ageNotificacioneseventoExtendsMapper.getEventNotifications(idCalendario,
					idInstitucion.toString());
			LOGGER.info(
					"getEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Salida de ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");

			eventNotificationDTO.setEventNotificationItems(eventNotifications);

		}

		LOGGER.info(
				"getEventNotifications() -> Salida del servicio para obtener las notificaciones de eventos de un calendario especifico");

		return eventNotificationDTO;
	}
	
	@Override
	public NotificacionEventoDTO getHistoricEventNotifications(String idCalendario, HttpServletRequest request) {
		LOGGER.info(
				"getHistoricEventNotifications() -> Entrada al servicio para obtener el historico de las notificaciones de eventos de un calendario especifico");

		List<NotificacionEventoItem> eventNotifications = new ArrayList<NotificacionEventoItem>();
		NotificacionEventoDTO eventNotificationDTO = new NotificacionEventoDTO();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			LOGGER.info(
					"getHistoricEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Entrada a ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");
			eventNotifications = ageNotificacioneseventoExtendsMapper.getHistoricEventNotifications(idCalendario,
					idInstitucion.toString());
			LOGGER.info(
					"getHistoricEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Salida de ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");

			eventNotificationDTO.setEventNotificationItems(eventNotifications);

		}

		LOGGER.info(
				"getHistoricEventNotifications() -> Salida del servicio para obtener el historico de las notificaciones de eventos de un calendario especifico");

		return eventNotificationDTO;
	}

	@Override
	public UpdateResponseDTO deleteNotification(NotificacionEventoDTO notificacionDTO, HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateNotification() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateNotification() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				for (NotificacionEventoItem noti : notificacionDTO.getEventNotificationItems()) {
					
					AgeNotificacioneseventoExample exampleNotificacion = new AgeNotificacioneseventoExample();
					exampleNotificacion.createCriteria()
							.andIdnotificacioneventoEqualTo(Long.valueOf(noti.getIdNotificacion()))
							.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

					LOGGER.info(
							"updateCalendar() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Entrada a ageNotificacioneseventoMapper para buscar si existe la notificacion");

					List<AgeNotificacionesevento> notificationList = ageNotificacioneseventoMapper
							.selectByExample(exampleNotificacion);

					LOGGER.info(
							"updateCalendar() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Salida a ageNotificacioneseventoMapper para buscar si existe la notificacion");

					if (null != notificationList && notificationList.size() > 0) {
						AgeNotificacionesevento notification = notificationList.get(0);

						LOGGER.info(
								"updateCalendar() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Entrada a ageCalendarioExtendsMapper para dar de baja a la notificacion");

						notification.setFechamodificacion(new Date());
						notification.setUsumodificacion(usuario.getIdusuario().longValue());
						notification.setFechabaja(new Date());
						ageNotificacioneseventoMapper.updateByPrimaryKey(notification);

						LOGGER.info(
								"updateCalendar() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Salida a ageCalendarioExtendsMapper para dar de baja a notificacion");

					}
				}
			}
		}

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	

}
