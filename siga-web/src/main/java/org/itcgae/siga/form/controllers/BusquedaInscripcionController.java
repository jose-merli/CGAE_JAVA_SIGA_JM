package org.itcgae.siga.form.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.form.InscripcionDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.form.services.IBusquedaInscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusquedaInscripcionController {

	@Autowired
	private IBusquedaInscripcionService busquedaInscripcionService;
	
	@RequestMapping(value = "busquedaInscripciones/estadosInscripciones",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getEstadosInscripciones(HttpServletRequest request) {
		ComboDTO response = busquedaInscripcionService.getEstadosInscripcion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaInscripciones/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionDTO> searchInscripcion(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) {
		InscripcionDTO response = busquedaInscripcionService.searchInscripcion(inscripcionItem, request);
		return new ResponseEntity<InscripcionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaInscripciones/searchPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FichaPersonaItem> searchPersona(HttpServletRequest request) { 
		FichaPersonaItem response = busquedaInscripcionService.searchPersona(request);
		return new ResponseEntity<FichaPersonaItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaInscripciones/isAdministrador",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> isAdministrador(HttpServletRequest request) { 
		Boolean response = busquedaInscripcionService.isAdministrador(request);
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "busquedaInscripciones/calificacionesEmitidas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCalificacionesEmitidas(HttpServletRequest request) {
		ComboDTO response = busquedaInscripcionService.getCalificacionesEmitidas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaInscripciones/updateEstado",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Object> updateEstado(@RequestBody List<InscripcionItem> listInscripcionItem, HttpServletRequest request) {
		Object response = busquedaInscripcionService.updateEstado(listInscripcionItem, request);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaInscripciones/updateCalificacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Integer> updateCalificacion(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) {
		Integer response = busquedaInscripcionService.updateCalificacion(inscripcionItem, request);
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}
}
