package org.itcgae.siga.age.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeGeneracionnotificaciones;
import org.itcgae.siga.db.entities.AgeGeneracionnotificacionesExample;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample;
import org.itcgae.siga.db.mappers.AgeGeneracionnotificacionesMapper;
import org.itcgae.siga.db.mappers.AgeNotificacioneseventoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeNotificacioneseventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipocuandoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTiponotificacioneventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeUnidadmedidaExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.EnvPlantillasenviosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.EnvTipoenviosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private AgeTipocuandoExtendsMapper ageTipocuandoExtendsMapper;

	@Autowired
	private EnvPlantillasenviosExtendsMapper envPlantillasenviosExtendsMapper;

	@Autowired
	private EnvTipoenviosExtendsMapper envTipoenviosExtendsMapper;

	@Autowired
	private AgeNotificacioneseventoExtendsMapper ageNotificacioneseventoExtendsMapper;

	@Autowired
	private AgeGeneracionnotificacionesMapper ageGeneracionnotificacionesMapper;

	@Autowired
	private AgeEventoExtendsMapper ageEventoExtendsMapper;

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

		LOGGER.info("saveNotification() -> Entrada al servicio para guardar una notificaciones de un evento");

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
				ageNotificacionEventoInsert.setIdinstitucion(idInstitucion);
				ageNotificacionEventoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
				ageNotificacionEventoInsert.setFechamodificacion(new Date());

				if (notificacionEventoItem.getIdEvento() != null) {
					ageNotificacionEventoInsert.setIdevento(Long.valueOf(notificacionEventoItem.getIdEvento()));
				} else {
					ageNotificacionEventoInsert
							.setIdcalendario(Long.parseLong(notificacionEventoItem.getIdCalendario()));
				}
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
				response = ageNotificacioneseventoExtendsMapper.insert(ageNotificacionEventoInsert);
				LOGGER.info(
						"saveNotification() / ageNotificacioneseventoMapper.insert(ageNotificacionEventoInsert) -> Salida a ageNotificacioneseventoMapper para insertar una notificacion");

				// Debemos guardar cuando se generará la notificación
				if (notificacionEventoItem.getIdEvento() != null) {
					AgeGeneracionnotificaciones ageGeneracionnotificaciones = new AgeGeneracionnotificaciones();
					ageGeneracionnotificaciones.setUsumodificacion(usuario.getIdusuario().longValue());
					ageGeneracionnotificaciones.setFechamodificacion(new Date());
					ageGeneracionnotificaciones.setIdinstitucion(idInstitucion);
					ageGeneracionnotificaciones
							.setIdtiponotificacionevento(ageNotificacionEventoInsert.getIdtiponotificacionevento());

					ageGeneracionnotificaciones.setIdevento(ageNotificacionEventoInsert.getIdevento());

					ageGeneracionnotificaciones
							.setIdnotificacionevento(ageNotificacionEventoInsert.getIdnotificacionevento());

					Date fechaGeneracionNotificacion = generateNotificationDate(ageNotificacionEventoInsert);
					ageGeneracionnotificaciones.setFechageneracionnotificacion(fechaGeneracionNotificacion);

					LOGGER.info(
							"saveNotification() / ageGeneracionnotificacionesMapper.insert(ageGeneracionnotificaciones) -> Entrada a ageGeneracionnotificacionesMapper para insertar cuando se generará una notificacion");

					response = ageGeneracionnotificacionesMapper.insert(ageGeneracionnotificaciones);

					LOGGER.info(
							"saveNotification() / ageGeneracionnotificacionesMapper.insert(ageGeneracionnotificaciones) -> Salida a ageGeneracionnotificacionesMapper para insertar cuando se generará una notificacion");

				}

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				} else {
					error.setCode(200);
				}
			}
		}

		LOGGER.info("saveNotification() -> Salida del servicio para guardar una notificaciones de un evento");

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	public Date generateNotificationDate(AgeNotificacionesevento ageNotificacionEventoInsert) {
		Date fechaGeneracionNotificacion = null;
		Long valor = ageNotificacionEventoInsert.getCuando();
		Calendar calendar = Calendar.getInstance();

		LOGGER.info(
				"generateNotificationDate() / ageEventoExtendsMapper.selectByPrimaryKey() -> Entrada a ageEventoExtendsMapper para obtener el evento");

		AgeEvento ageEvento = ageEventoExtendsMapper.selectByPrimaryKey(ageNotificacionEventoInsert.getIdevento());

		LOGGER.info(
				"generateNotificationDate() / ageEventoExtendsMapper.selectByPrimaryKey() -> Salida a ageEventoExtendsMapper para obtener el evento");

		if (ageEvento != null) {
			calendar.setTime(ageEvento.getFechainicio()); // tuFechaBase es un Date;

			if (ageNotificacionEventoInsert.getIdtipocuando() == SigaConstants.NOTIFICACION_TIPOCUANDO_ANTES) {

				if (ageNotificacionEventoInsert.getIdunidadmedida() == SigaConstants.NOTIFICACION_HORAS) {
					calendar.add(Calendar.HOUR, -valor.intValue());
				} else if (ageNotificacionEventoInsert.getIdunidadmedida() == SigaConstants.NOTIFICACION_MINUTOS) {
					calendar.add(Calendar.MINUTE, -valor.intValue());
				} else if (ageNotificacionEventoInsert.getIdunidadmedida() == SigaConstants.NOTIFICACION_DIAS) {
					calendar.add(Calendar.DAY_OF_YEAR, -valor.intValue());
				}

			} else if (ageNotificacionEventoInsert.getIdtipocuando() == SigaConstants.NOTIFICACION_TIPOCUANDO_DESPUES) {

				if (ageNotificacionEventoInsert.getIdunidadmedida() == SigaConstants.NOTIFICACION_HORAS) {
					calendar.add(Calendar.HOUR, valor.intValue());
				} else if (ageNotificacionEventoInsert.getIdunidadmedida() == SigaConstants.NOTIFICACION_MINUTOS) {
					calendar.add(Calendar.MINUTE, valor.intValue());
				} else if (ageNotificacionEventoInsert.getIdunidadmedida() == SigaConstants.NOTIFICACION_DIAS) {
					calendar.add(Calendar.DAY_OF_YEAR, valor.intValue());
				}
			}
		}

		fechaGeneracionNotificacion = calendar.getTime();
		return fechaGeneracionNotificacion;
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

	@Transactional
	@Override
	public UpdateResponseDTO updateNotification(NotificacionEventoDTO notificacionEventoDTO,
			HttpServletRequest request) {

		LOGGER.info("updateNotification() -> Entrada al servicio para modificar las notificaciones de eventos");

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

				try {
					for (NotificacionEventoItem notificacionUpdate : notificacionEventoDTO
							.getEventNotificationItems()) {

						AgeNotificacioneseventoExample exampleNotificacion = new AgeNotificacioneseventoExample();
						exampleNotificacion.createCriteria()
								.andIdnotificacioneventoEqualTo(Long.valueOf(notificacionUpdate.getIdNotificacion()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						LOGGER.info(
								"updateNotification() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Entrada a ageNotificacioneseventoMapper para buscar si existe la notificacion");

						List<AgeNotificacionesevento> notificationList = ageNotificacioneseventoExtendsMapper
								.selectByExample(exampleNotificacion);

						LOGGER.info(
								"updateNotification() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Salida a ageNotificacioneseventoMapper para buscar si existe la notificacion");

						if (null != notificationList && notificationList.size() > 0) {
							AgeNotificacionesevento notification = notificationList.get(0);

							LOGGER.info(
									"updateNotification() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Entrada a ageCalendarioExtendsMapper para modificar la notificacion");

							notification.setFechamodificacion(new Date());
							notification.setUsumodificacion(usuario.getIdusuario().longValue());
							notification.setIdplantilla(Long.valueOf(notificacionUpdate.getIdPlantilla()));
							notification.setIdtipoenvios(Long.valueOf(notificacionUpdate.getIdTipoEnvio()));
							notification.setIdtiponotificacionevento(
									Long.valueOf(notificacionUpdate.getIdTipoNotificacion()));
							notification.setCuando(Long.valueOf(notificacionUpdate.getCuando()));
							notification.setIdtipocuando(Long.valueOf(notificacionUpdate.getIdTipoCuando()));
							notification.setIdunidadmedida(Long.valueOf(notificacionUpdate.getIdUnidadMedida()));
							response = ageNotificacioneseventoExtendsMapper.updateByPrimaryKey(notification);

							LOGGER.info(
									"updateNotification() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Salida a ageCalendarioExtendsMapper para modificar la notificacion");

							if (notification.getIdevento() != null) {
								response = checkGenerationDateNotification(notification, usuario);
							}
						}
					}
				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (response != 0) {
					error.setCode(200);
				}
			}
		}

		LOGGER.info("updateNotification() -> Salida del servicio para modificar las notificaciones de eventos");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public int checkGenerationDateNotification(AgeNotificacionesevento notification, AdmUsuarios usuario) {

		int response = 2;
		AgeGeneracionnotificacionesExample ageGeneracionnotificacionesExample = new AgeGeneracionnotificacionesExample();
		ageGeneracionnotificacionesExample.createCriteria().andIdeventoEqualTo(notification.getIdevento())
				.andIdnotificacioneventoEqualTo(notification.getIdnotificacionevento())
				.andIdinstitucionEqualTo(notification.getIdinstitucion());

		LOGGER.info(
				"checkGenerationDateNotification() / ageGeneracionnotificacionesMapper.selectByPrimaryKey() -> Entrada a ageGeneracionnotificacionesMapper para obtener la generacion de las notificaciones de un evento");

		List<AgeGeneracionnotificaciones> ageGeneracionnotificacionesList = ageGeneracionnotificacionesMapper
				.selectByExample(ageGeneracionnotificacionesExample);

		LOGGER.info(
				"checkGenerationDateNotification() / ageGeneracionnotificacionesMapper.selectByPrimaryKey() -> Salida a ageGeneracionnotificacionesMapper para obtener la generacion de las notificaciones de un evento");

		if (null != ageGeneracionnotificacionesList && ageGeneracionnotificacionesList.size() > 0) {
			AgeGeneracionnotificaciones ageGeneracionNotificacion = ageGeneracionnotificacionesList.get(0);
			Date fechaGeneracionNotificacion = generateNotificationDate(notification);

			if (ageGeneracionNotificacion.getFechageneracionnotificacion() != fechaGeneracionNotificacion) {

				ageGeneracionNotificacion.setFechageneracionnotificacion(fechaGeneracionNotificacion);
				ageGeneracionNotificacion.setFechamodificacion(new Date());
				ageGeneracionNotificacion.setUsumodificacion(usuario.getIdusuario().longValue());

				response = ageGeneracionnotificacionesMapper.updateByPrimaryKeySelective(ageGeneracionNotificacion);
			}
		}

		return response;
	}

	@Override
	public NotificacionEventoDTO getCalendarNotifications(String idCalendario, HttpServletRequest request) {
		LOGGER.info(
				"getEventNotifications() -> Entrada al servicio para obtener las notificaciones de eventos de un calendario especifico");

		List<NotificacionEventoItem> eventNotifications = new ArrayList<NotificacionEventoItem>();
		NotificacionEventoDTO eventNotificationDTO = new NotificacionEventoDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEventNotifications() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEventNotifications() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Entrada a ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");
				eventNotifications = ageNotificacioneseventoExtendsMapper.getCalendarNotifications(idCalendario,
						idInstitucion.toString(), usuario.getIdlenguaje());
				LOGGER.info(
						"getEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Salida de ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");

				eventNotificationDTO.setEventNotificationItems(eventNotifications);
			}

		}

		LOGGER.info(
				"getEventNotifications() -> Salida del servicio para obtener las notificaciones de eventos de un calendario especifico");

		return eventNotificationDTO;
	}

	@Override
	public NotificacionEventoDTO getHistoricCalendarNotifications(String idCalendario, HttpServletRequest request) {
		LOGGER.info(
				"getHistoricEventNotifications() -> Entrada al servicio para obtener el historico de las notificaciones de eventos de un calendario especifico");

		List<NotificacionEventoItem> eventNotifications = new ArrayList<NotificacionEventoItem>();
		NotificacionEventoDTO eventNotificationDTO = new NotificacionEventoDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEventNotifications() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEventNotifications() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getHistoricEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Entrada a ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");
				eventNotifications = ageNotificacioneseventoExtendsMapper.getHistoricCalendarNotifications(idCalendario,
						idInstitucion.toString(), usuario.getIdlenguaje());
				LOGGER.info(
						"getHistoricEventNotifications() / ageNotificacioneseventoExtendsMapper.getEventNotifications() -> Salida de ageNotificacioneseventoMapper para obtener las notificaciones de eventos de un calendario");

				eventNotificationDTO.setEventNotificationItems(eventNotifications);

			}

		}

		LOGGER.info(
				"getHistoricEventNotifications() -> Salida del servicio para obtener el historico de las notificaciones de eventos de un calendario especifico");

		return eventNotificationDTO;
	}

	@Override
	public UpdateResponseDTO deleteNotification(NotificacionEventoDTO notificacionDTO, HttpServletRequest request) {

		LOGGER.info(
				"deleteNotification() -> Entrada al servicio para dar de baja a las notificaciones de eventos de un calendario especifico");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"deleteNotification() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteNotification() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				for (NotificacionEventoItem noti : notificacionDTO.getEventNotificationItems()) {

					AgeNotificacioneseventoExample exampleNotificacion = new AgeNotificacioneseventoExample();
					exampleNotificacion.createCriteria()
							.andIdnotificacioneventoEqualTo(Long.valueOf(noti.getIdNotificacion()))
							.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

					LOGGER.info(
							"deleteNotification() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Entrada a ageNotificacioneseventoMapper para buscar si existe la notificacion");

					List<AgeNotificacionesevento> notificationList = ageNotificacioneseventoExtendsMapper
							.selectByExample(exampleNotificacion);

					LOGGER.info(
							"deleteNotification() / ageNotificacioneseventoMapper.selectByExample(exampleNotificacion) -> Salida a ageNotificacioneseventoMapper para buscar si existe la notificacion");

					if (null != notificationList && notificationList.size() > 0) {
						AgeNotificacionesevento notification = notificationList.get(0);

						LOGGER.info(
								"deleteNotification() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Entrada a ageCalendarioExtendsMapper para dar de baja a la notificacion");

						notification.setFechamodificacion(new Date());
						notification.setUsumodificacion(usuario.getIdusuario().longValue());
						notification.setFechabaja(new Date());
						response = ageNotificacioneseventoExtendsMapper.updateByPrimaryKey(notification);

						LOGGER.info(
								"deleteNotification() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Salida a ageCalendarioExtendsMapper para dar de baja a notificacion");

						if (notification.getIdevento() != null) {
							// Eliminamos la generacion de la notificacion que eliminamos
							AgeGeneracionnotificacionesExample ageGeneracionnotificacionesExample = new AgeGeneracionnotificacionesExample();
							ageGeneracionnotificacionesExample.createCriteria()
									.andIdnotificacioneventoEqualTo(notification.getIdnotificacionevento())
									.andIdeventoEqualTo(notification.getIdevento());

							List<AgeGeneracionnotificaciones> ageGeneracionnotificacionesList = ageGeneracionnotificacionesMapper
									.selectByExample(ageGeneracionnotificacionesExample);

							if (null != ageGeneracionnotificacionesList && ageGeneracionnotificacionesList.size() > 0) {

								AgeGeneracionnotificaciones ageGeneracionnotificacion = ageGeneracionnotificacionesList
										.get(0);

								ageGeneracionnotificacion.setUsumodificacion(usuario.getIdusuario().longValue());
								ageGeneracionnotificacion.setFechamodificacion(new Date());
								ageGeneracionnotificacion.setFechabaja(new Date());

								LOGGER.info(
										"deleteNotification() / ageGeneracionnotificacionesMapper.updateByPrimaryKeySelective(ageGeneracionnotificaciones) -> Entrada a ageGeneracionnotificacionesMapper para insertar cuando se generará una notificacion");

								response = ageGeneracionnotificacionesMapper
										.updateByPrimaryKeySelective(ageGeneracionnotificacion);

								LOGGER.info(
										"deleteNotification() / ageGeneracionnotificacionesMapper.updateByPrimaryKeySelective(ageGeneracionnotificaciones) -> Salida a ageGeneracionnotificacionesMapper para insertar cuando se generará una notificacion");

							}
						}

						if (response == 0) {
							error.setCode(400);
							error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
						} else {
							error.setCode(200);
						}
					}
				}
			}
		}

		LOGGER.info(
				"deleteNotification() -> Salida del servicio para dar de baja a las notificaciones de eventos de un calendario especifico");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public ComboDTO getPlantillas(HttpServletRequest request) {
		LOGGER.info(
				"getPlantillas() -> Entrada al servicio para obtener las plantillas de las notificaciones de eventos");

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
					"getPlantillas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getPlantillas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getPlantillas() / envPlantillasenviosExtendsMapper.getPlantillas() -> Entrada a envPlantillasenviosExtendsMapper para obtener las plantillas de notificaciones");

				comboItems = envPlantillasenviosExtendsMapper.getPlantillas(idInstitucion.toString());

				LOGGER.info(
						"getPlantillas() / envPlantillasenviosExtendsMapper.getPlantillas() -> Salida de envPlantillasenviosExtendsMapper para obtener las plantillas de notificaciones");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getPlantillas() -> Salida del servicio para obtener las plantillas de las notificaciones de eventos");

		return comboDTO;
	}

	@Override
	public ComboDTO getNotificationTypeCalendarTraining(HttpServletRequest request) {
		LOGGER.info(
				"getNotificationTypeCalendarTraining() -> Entrada al servicio para obtener los tipos de notificaciones de eventos");

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
					"getNotificationTypeCalendarTraining() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getNotificationTypeCalendarTraining() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getNotificationTypeCalendarTraining() / ageTiponotificacioneventoExtendsMapper.getNotificationTypeCalendarTraining() -> Entrada a ageTipocalendarioExtendsMapper para obtener los diferentes tipos de notificaciones");

				comboItems = ageTiponotificacioneventoExtendsMapper
						.getNotificationTypeCalendarTraining(usuario.getIdlenguaje());

				LOGGER.info(
						"getNotificationTypeCalendarTraining() / ageTiponotificacioneventoExtendsMapper.getNotificationTypeCalendarTraining() -> Salida de ageTipocalendarioExtendsMapper para obtener los diferentes tipos de notificaciones");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTypeNotifications() -> Salida del servicio para obtener notificaciones de eventos");

		return comboDTO;
	}

}
