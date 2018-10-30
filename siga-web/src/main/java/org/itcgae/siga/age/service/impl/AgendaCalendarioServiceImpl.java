package org.itcgae.siga.age.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.age.service.IAgendaCalendarioService;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaCalendarioServiceImpl implements IAgendaCalendarioService {

	private Logger LOGGER = Logger.getLogger(AgendaCalendarioServiceImpl.class);

	@Autowired
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;

	@Override
	public CalendarDTO getCalendariosByIdInstitucion(HttpServletRequest request) {
		
		CalendarDTO calendarDTO = new CalendarDTO();
		AgeCalendarioExample example = new AgeCalendarioExample();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {

			example.createCriteria().andIdinstitucionEqualTo(idInstitucion);

			List<AgeCalendario> listAgeCalendario = ageCalendarioExtendsMapper.selectByExample(example);
			
			if(!listAgeCalendario.isEmpty() && listAgeCalendario != null) {
				calendarDTO.fillListCalendarItems(listAgeCalendario);
			}
			
		}

			return calendarDTO;
		}

		@Override
		public EventoDTO getEventosByIdCalendario(String idCalendario) {
			LOGGER.info("getEventosByidCalendario() -> Entrada al servicio para los eventos de un determinado calendario");

			EventoDTO eventoDTO = new EventoDTO();
			List<EventoItem> listEventos = new ArrayList<EventoItem>();

			EventoItem eventoItem = null;

			switch (idCalendario) {

			case "1":

				eventoItem = new EventoItem("1", "Reuniones iniciales", "2018-10-07", "2018-10-10", true, "#ff9f89", "1");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("2", "Evento inaguración", "2018-10-15", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("3", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("4", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("5", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("6", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("7", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("8", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("9", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("10", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);
				
				eventoItem = new EventoItem("11", "Evento especial", "2018-10-20", null, true, "#ff9f89", "1");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("12", "Reuniones iniciales", "2018-10-22", "2018-10-26", true, "#ff9f89", "1");
				listEventos.add(eventoItem);

				break;

			case "2":

				eventoItem = new EventoItem("1", "Conferencia inicial", "2018-10-12", "2018-10-15", true, "#009414", "2");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("2", "Evento importante", "2018-10-16T12:00:00", "2018-10-16T14:00:00", true,
						"#009414", "2");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("3", "Examen", "2018-10-20", null, true, "#009414", "2");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("4", "Reuniones finales", "2018-10-25", "2018-10-26", true, "#009414", "2");
				listEventos.add(eventoItem);

				break;

			case "3":

				eventoItem = new EventoItem("1", "Exámenes extrordinarios", "2018-10-02", "2018-10-03", true, "#0de7e9",
						"3");
				listEventos.add(eventoItem);

				eventoItem = new EventoItem("2", "Prácticas extraordinarias", "2018-10-05", null, true, "#0de7e9", "3");
				listEventos.add(eventoItem);

				break;

			}

			eventoDTO.setEventos(listEventos);

			return eventoDTO;
		}

	}
