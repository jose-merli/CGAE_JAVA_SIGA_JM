package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.scs.services.maestros.ICalendarioLaboralAgendaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarioLaboralAgendaServiceImpl implements ICalendarioLaboralAgendaService {

	private Logger LOGGER = Logger.getLogger(CalendarioLaboralAgendaServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private AgeEventoExtendsMapper ageEventoExtendsMapper;

	@Override
	public EventoDTO searchFestivos(EventoItem eventoItem, HttpServletRequest request) {
		LOGGER.info("searchFestivos() -> Entrada al servicio para los eventos festivos");

		EventoDTO eventoDTO = new EventoDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		List<EventoItem> listEventos = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchFestivos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchFestivos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				LOGGER.info(
						"searchFestivos() / scsPartidaPresupuestariaExtendsMapper.selectByExample() -> Entrada a scsPartidaPresupuestariaExtendsMapper para obtener los modulos");

				listEventos = ageEventoExtendsMapper.searchFestivos(eventoItem, idInstitucion);

				LOGGER.info(
						"searchFestivos() / scsPartidaPresupuestaria.selectByExample() -> Salida a scsPartidaPresupuestariaExtends para obtener los modulos");
			}
		}

		eventoDTO.setEventos(listEventos);

		LOGGER.info("searchFestivos() -> Salida al servicio para los eventos festivos");

		return eventoDTO;
	}

	@Override
	public UpdateResponseDTO deleteFestivos(EventoDTO eventoDTO, HttpServletRequest request) {

		LOGGER.info("deleteFestivos() ->  Entrada al servicio para eliminar los eventos festivos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteFestivos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteFestivos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (EventoItem eventoItem : eventoDTO.getEventos()) {

						if (idInstitucion.equals(SigaConstants.IDINSTITUCION_2000)
								&& eventoItem.getIdTipoEvento().equals(SigaConstants.EVENTO_TIPO_FESTIVO)
								&& eventoItem.getTitulo().equals(SigaConstants.EVENTO_TIPO_FIESTA_NACIONAL)) {

							response = deleteEventosTipoFestivoNacionalAutonomico(eventoItem, usuario);

						} else {
							AgeEventoExample ageEventoExample = new AgeEventoExample();

							ageEventoExample.createCriteria()
									.andIdeventoEqualTo(Long.valueOf(eventoItem.getIdEvento()));

							LOGGER.info(
									"deleteFestivos() / ageEventoExtendsMapper.selectByExample() -> Entrada a ageEventoExtendsMapper para buscar el evento");

							List<AgeEvento> eventosFestivosList = ageEventoExtendsMapper
									.selectByExample(ageEventoExample);

							LOGGER.info(
									"deleteFestivos() / ageEventoExtendsMapper.selectByExample() -> Salida de ageEventoExtendsMapper para buscar el evento");

							if (null != eventosFestivosList && eventosFestivosList.size() > 0) {

								AgeEvento evento = eventosFestivosList.get(0);

								evento.setFechabaja(new Date());
								evento.setFechamodificacion(new Date());
								evento.setUsumodificacion(usuario.getIdusuario().longValue());

								LOGGER.info(
										"deleteFestivos() / ageEventoExtendsMapper.updateByPrimaryKey() -> Entrada a ageEventoExtendsMapper para dar de baja a un evento");

								response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

								LOGGER.info(
										"deleteFestivos() / ageEventoExtendsMapper.updateByPrimaryKey() -> Salida de ageEventoExtendsMapper para dar de baja a un evento");
							}
						}

					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteFestivos() -> Salida del servicio para eliminar los eventos festivos");

		return updateResponseDTO;

	}

	private int deleteEventosTipoFestivoNacionalAutonomico(EventoItem eventoItem, AdmUsuarios usuario) {
		int response = 0;

		AgeEventoExample exampleEvent = new AgeEventoExample();
		exampleEvent.createCriteria().andFechainicioEqualTo(eventoItem.getFechaInicio())
				.andIdtipoeventoEqualTo(Long.valueOf(eventoItem.getIdTipoEvento()))
				.andTituloEqualTo(eventoItem.getTitulo()).andFechabajaIsNull();

		LOGGER.info(
				"deleteFestivos() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Entrada a ageNotificacioneseventoMapper para buscar si existe el evento");

		List<AgeEvento> eventList = ageEventoExtendsMapper.selectByExample(exampleEvent);

		LOGGER.info(
				"deleteFestivos() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Salida a ageNotificacioneseventoMapper para buscar si existe el evento");

		if (null != eventList && eventList.size() > 0) {

			for (AgeEvento event : eventList) {

				event.setFechamodificacion(new Date());
				event.setUsumodificacion(usuario.getIdusuario().longValue());
				event.setFechabaja(new Date());

				LOGGER.info(
						"deleteFestivos() / ageEventoExtendsMapper.updateByPrimaryKey(event) -> Entrada a ageEventoExtendsMapper para eliminar un evento");

				response = ageEventoExtendsMapper.updateByPrimaryKey(event);

				LOGGER.info(
						"deleteFestivos() / ageEventoExtendsMapper.updateByPrimaryKey(event) -> Salida a ageEventoExtendsMapper para eliminar un evento");
			}
		}

		return response;
	}

	@Override
	public UpdateResponseDTO activateFestivos(EventoDTO eventoDTO, HttpServletRequest request) {

		LOGGER.info("activateFestivos() ->  Entrada al servicio para dar de alta a eventos festivos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activateFestivos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateFestivos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (EventoItem eventoItem : eventoDTO.getEventos()) {

						if (idInstitucion.equals(SigaConstants.IDINSTITUCION_2000)
								&& eventoItem.getIdTipoEvento().equals(SigaConstants.EVENTO_TIPO_FESTIVO)
								&& eventoItem.getTitulo().equals(SigaConstants.EVENTO_TIPO_FIESTA_NACIONAL)) {

							response = activateEventosTipoFestivoNacionalAutonomico(eventoItem, usuario);

						} else {

							AgeEventoExample ageEventoExample = new AgeEventoExample();
							ageEventoExample.createCriteria()
									.andIdeventoEqualTo(Long.valueOf(eventoItem.getIdEvento()));

							LOGGER.info(
									"activateFestivos() / ageEventoExtendsMapper.selectByExample() -> Entrada a ageEventoExtendsMapper para buscar el evento");

							List<AgeEvento> eventosFestivosList = ageEventoExtendsMapper
									.selectByExample(ageEventoExample);

							LOGGER.info(
									"activateFestivos() / ageEventoExtendsMapper.selectByExample() -> Salida de ageEventoExtendsMapper para buscar el evento");

							if (null != eventosFestivosList && eventosFestivosList.size() > 0) {

								AgeEvento evento = eventosFestivosList.get(0);

								evento.setFechabaja(null);
								evento.setFechamodificacion(new Date());
								evento.setUsumodificacion(usuario.getIdusuario().longValue());

								LOGGER.info(
										"activateFestivos() / ageEventoExtendsMapper.updateByPrimaryKey() -> Entrada a ageEventoExtendsMapper para dar de alta a un evento");

								response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

								LOGGER.info(
										"activateFestivos() / ageEventoExtendsMapper.updateByPrimaryKey() -> Salida de ageEventoExtendsMapper para dar de alta a un evento");

							}
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateFestivos() -> Salida del servicio para dar de alta a eventos festivos");

		return updateResponseDTO;

	}

	private int activateEventosTipoFestivoNacionalAutonomico(EventoItem eventoItem, AdmUsuarios usuario) {
		int response = 0;

		AgeEventoExample exampleEvent = new AgeEventoExample();
		exampleEvent.createCriteria().andFechainicioEqualTo(eventoItem.getFechaInicio())
				.andIdtipoeventoEqualTo(Long.valueOf(eventoItem.getIdTipoEvento()))
				.andTituloEqualTo(eventoItem.getTitulo()).andFechabajaIsNotNull();

		LOGGER.info(
				"activateFestivos() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Entrada a ageNotificacioneseventoMapper para buscar si existe el evento");

		List<AgeEvento> eventList = ageEventoExtendsMapper.selectByExample(exampleEvent);

		LOGGER.info(
				"activateFestivos() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Salida a ageNotificacioneseventoMapper para buscar si existe el evento");

		if (null != eventList && eventList.size() > 0) {

			for (AgeEvento event : eventList) {

				event.setFechamodificacion(new Date());
				event.setUsumodificacion(usuario.getIdusuario().longValue());
				event.setFechabaja(null);

				LOGGER.info(
						"activateFestivos() / ageEventoExtendsMapper.updateByPrimaryKey(event) -> Entrada a ageEventoExtendsMapper para eliminar un evento");

				response = ageEventoExtendsMapper.updateByPrimaryKey(event);

				LOGGER.info(
						"activateFestivos() / ageEventoExtendsMapper.updateByPrimaryKey(event) -> Salida a ageEventoExtendsMapper para eliminar un evento");
			}
		}

		return response;
	}

}