package org.itcgae.siga.age.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.age.service.IAgendaCalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgendaCalendarioController {
	
	@Autowired
	private IAgendaCalendarioService agendaCalendarioService;
	
	@RequestMapping(value = "agendaCalendario/getCalendarios",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CalendarDTO> getCalendarios(HttpServletRequest request) {
		CalendarDTO response = agendaCalendarioService.getCalendariosByIdInstitucion(request);
		return new ResponseEntity<CalendarDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "agendaCalendario/getEventosByIdCalendario",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EventoDTO> getEventsByCalendarId(@RequestParam("idCalendario") String idCalendario, HttpServletRequest request) {
		EventoDTO response = agendaCalendarioService.getEventosByIdCalendario(request, idCalendario);
		return new ResponseEntity<EventoDTO>(response, HttpStatus.OK);
	}
		
}
