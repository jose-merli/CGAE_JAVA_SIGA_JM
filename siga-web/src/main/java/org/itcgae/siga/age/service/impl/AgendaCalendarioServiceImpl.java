package org.itcgae.siga.age.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.age.service.IAgendaCalendarioService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaCalendarioServiceImpl implements IAgendaCalendarioService {

	private Logger LOGGER = Logger.getLogger(AgendaCalendarioServiceImpl.class);

	@Autowired
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;
	
	@Autowired 
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Override
	public CalendarDTO getCalendariosByIdInstitucion(HttpServletRequest request) {

		CalendarDTO calendarDTO = new CalendarDTO();
		AgeCalendarioExample example = new AgeCalendarioExample();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		String perfilesFormat = UtilidadesString.prepararPerfiles(perfiles);
		
		if (null != idInstitucion) {

			example.createCriteria().andIdinstitucionEqualTo(idInstitucion);

			List<CalendarItem> listCalendarItem = ageCalendarioExtendsMapper.getCalendariosPermisos(idInstitucion, perfilesFormat);
			calendarDTO.setCalendarItems(listCalendarItem);
		}

		return calendarDTO;
	}

	@Override
	public EventoDTO getEventosByIdCalendario(HttpServletRequest request, String idCalendario) {
		LOGGER.info("getEventosByidCalendario() -> Entrada al servicio para los eventos de un determinado calendario");

		EventoDTO eventoDTO = new EventoDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		String perfilesFormat = UtilidadesString.prepararPerfiles(perfiles);
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		List<EventoItem> listEventos = null;
		List<CenPersona> listCenPersona = null;
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCalendars() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCalendars() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				if (letrado.equalsIgnoreCase("S")) {
					
					AgeCalendario ageCalendario = ageCalendarioExtendsMapper.selectByPrimaryKey(Long.valueOf(idCalendario));

					if(SigaConstants.CALENDARIO_FORMACION == ageCalendario.getIdtipocalendario()) {
						
						CenPersonaExample cenPersonaExample = new CenPersonaExample();
						cenPersonaExample.createCriteria().andNifcifEqualTo(dni);
						listCenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
						if (!listCenPersona.isEmpty()) {
							CenPersona cenPersona = listCenPersona.get(0);
							listEventos = ageCalendarioExtendsMapper.getCalendarioEventosIsColegiado(idInstitucion, perfilesFormat, 
									idCalendario, cenPersona.getIdpersona(), usuario.getIdlenguaje());
						}
						
					}else {
						listEventos = ageCalendarioExtendsMapper.getCalendarioEventos(idInstitucion, perfilesFormat, idCalendario, usuario.getIdlenguaje());
					}

				}else{
					listEventos = ageCalendarioExtendsMapper.getCalendarioEventos(idInstitucion, perfilesFormat, idCalendario, usuario.getIdlenguaje());

				}
			}
		}
		eventoDTO.setEventos(listEventos);

		return eventoDTO;
	}

	@Override
	public ComboDTO getCalendars(HttpServletRequest request) {
		LOGGER.info("getCalendars() -> Entrada al servicio para obtener los calendarios");

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
					"getCalendars() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCalendars() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				LOGGER.info(
						"getCalendars() / ageCalendarioExtendsMapper.getCalendarType() -> Entrada a ageTipocalendarioExtendsMapper para obtener los diferentes tipos de calendarios");
				comboItems = ageCalendarioExtendsMapper.getCalendars(idInstitucion.toString());
				LOGGER.info(
						"getCalendars() / ageCalendarioExtendsMapper.getCalendarType() -> Salida de ageTipocalendarioExtendsMapper para obtener los diferentes tipos de calendarios");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getCalendars() -> Salida del servicio para obtener los tipos de calendarios");

		return comboDTO;
	}

}
