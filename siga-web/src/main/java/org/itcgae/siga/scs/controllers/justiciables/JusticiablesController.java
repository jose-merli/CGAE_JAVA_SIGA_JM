package org.itcgae.siga.scs.controllers.justiciables;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTO.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.service.justiciables.IBusquedaJusticiablesService;
import org.itcgae.siga.scs.service.justiciables.IGestionJusticiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/justiciables")
public class JusticiablesController {
	
	@Autowired
	IBusquedaJusticiablesService busquedaJusticiablesService;
	
	@Autowired
	IGestionJusticiableService gestionJusticiableService;
	
	@RequestMapping(value = "/busquedaJusticiables/comboRoles", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboRoles(HttpServletRequest request) {
		ComboDTO response = busquedaJusticiablesService.getComboRoles(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/getTelefonos", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableTelefonoDTO> getTelefonos(HttpServletRequest request) {
		JusticiableTelefonoDTO response = busquedaJusticiablesService.getTelefonos(request);
		return new ResponseEntity<JusticiableTelefonoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaJusticiables/searchJusticiables", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableBusquedaDTO> searchJusticiables(@RequestBody JusticiableBusquedaItem justiciableBusquedaItem,HttpServletRequest request) {
		JusticiableBusquedaDTO response = busquedaJusticiablesService.searchJusticiables(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableBusquedaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboMinusvalias", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getMinusvalias(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getMinusvalias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboProfesiones", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProfesiones(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getProfesiones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "calendarioLaboralAgenda/deleteFestivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> deleteFestivos(@RequestBody EventoDTO eventoDTO, HttpServletRequest request) {
//
//		UpdateResponseDTO response = calendarioLaboralAgendaService.deleteFestivos(eventoDTO, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
//
//	}
//	
//	@RequestMapping(value = "calendarioLaboralAgenda/activateFestivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> activateFestivos(@RequestBody EventoDTO eventoDTO, HttpServletRequest request) {
//
//		UpdateResponseDTO response = calendarioLaboralAgendaService.activateFestivos(eventoDTO, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
//
//	}
	
		
}
