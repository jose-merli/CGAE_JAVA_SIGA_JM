package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.scs.services.maestros.ICalendarioLaboralAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarioLaboralAgendaController {
	
	@Autowired
	private ICalendarioLaboralAgendaService calendarioLaboralAgendaService;
	
	@RequestMapping(value = "calendarioLaboralAgenda/searchFestivos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EventoDTO> searchFestivos(@RequestBody EventoItem eventoItem, HttpServletRequest request) {
		EventoDTO response = calendarioLaboralAgendaService.searchFestivos(eventoItem, request);
		return new ResponseEntity<EventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "calendarioLaboralAgenda/deleteFestivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteFestivos(@RequestBody EventoDTO eventoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = calendarioLaboralAgendaService.deleteFestivos(eventoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	@RequestMapping(value = "calendarioLaboralAgenda/activateFestivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateFestivos(@RequestBody EventoDTO eventoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = calendarioLaboralAgendaService.activateFestivos(eventoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
		
}
