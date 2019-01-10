package org.itcgae.siga.age.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.AsistenciaEventoDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.age.service.IAgendaCalendarioService;
import org.itcgae.siga.age.service.IFichaEventosService;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaEventosController {
	
	@Autowired
	private IFichaEventosService fichaEventosService;
	
	@Autowired 
	private IAgendaCalendarioService agendaCalendarioService;
				
	@RequestMapping(value = "fichaEventos/getTrainersLabels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FormadorCursoDTO> getTrainersLabels(String idCurso, HttpServletRequest request) {
		FormadorCursoDTO response = fichaEventosService.getTrainersLabels(idCurso, request);
		return new ResponseEntity<FormadorCursoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/downloadTemplateFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadTemplateFile(@RequestBody AsistenciaEventoDTO asistenciaEventoDTO, HttpServletRequest request,
			HttpServletResponse response) throws SigaExceptions {
		ResponseEntity<InputStreamResource> res = fichaEventosService.generateExcelAssistance(asistenciaEventoDTO.getAsistenciaEventoItem());
		return res;
	}
	
	@RequestMapping(value = "fichaEventos/getCalendars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCalendars(HttpServletRequest request) {
		ComboDTO response = agendaCalendarioService.getCalendars(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/saveEventCalendar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveEvent(@RequestBody EventoItem eventoItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaEventosService.saveEventCalendar(eventoItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getTypeEvent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeEvent(HttpServletRequest request) {
		ComboDTO response = fichaEventosService.getTypeEvent(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getEventStates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getEventStates(HttpServletRequest request) {
		ComboDTO response = fichaEventosService.getEventStates(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getRepeatEvery", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getRepeatEvery(HttpServletRequest request) {
		ComboDTO response = fichaEventosService.getRepeatEvery(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getDaysWeek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getDaysWeek(HttpServletRequest request) {
		ComboDTO response = fichaEventosService.getDaysWeek(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getJudicialDistrict", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getJudicialDistrict(HttpServletRequest request) {
		ComboDTO response = fichaEventosService.getJudicialDistrict(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/updateEventCalendar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateEventCalendar(@RequestBody EventoItem eventoItem, HttpServletRequest request) {
		UpdateResponseDTO response = fichaEventosService.updateEventCalendar(eventoItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/deleteEventCalendar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteEventCalendar(@RequestBody EventoDTO eventoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaEventosService.deleteEventCalendar(eventoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getEventNotifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NotificacionEventoDTO> getEventNotifications(String idEvento, HttpServletRequest request) {
		NotificacionEventoDTO response = fichaEventosService.getEventNotifications(idEvento, request);
		return new ResponseEntity<NotificacionEventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getHistoricEventNotifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NotificacionEventoDTO> getHistoricEventNotifications(String idEvento, HttpServletRequest request) {
		NotificacionEventoDTO response = fichaEventosService.getHistoricEventNotifications(idEvento, request);
		return new ResponseEntity<NotificacionEventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaEventos/searchEvent",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EventoItem> searchEvent(@RequestBody CursoItem cursoItem, HttpServletRequest request) {
		EventoItem response = fichaEventosService.searchEvent(cursoItem, request);
		return new ResponseEntity<EventoItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/getEntryListCourse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AsistenciaEventoDTO> getEntryListCourse(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		AsistenciaEventoDTO response = fichaEventosService.getEntryListCourse(idCurso, request);
		return new ResponseEntity<AsistenciaEventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaEventos/saveAssistancesCourse",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveAssistancesCourse(@RequestBody AsistenciaEventoDTO asistenciaEventoDTO, HttpServletRequest request) {
		InsertResponseDTO response = fichaEventosService.saveAssistancesCourse(asistenciaEventoDTO, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
}
