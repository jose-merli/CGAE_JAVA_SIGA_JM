package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ISolicitudModificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolicitudModificacionController {

	@Autowired 
	private ISolicitudModificacionService solicitudModificacionService;
	
	@RequestMapping(value="solicitudModificacion/tipoModificacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoModificacion(HttpServletRequest request) {
		ComboDTO response = solicitudModificacionService.getComboTipoModificacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="solicitudModificacion/estado",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboEstado(HttpServletRequest request) {
		ComboDTO response = solicitudModificacionService.getComboEstado(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchModificationRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModificacionDTO> searchModificationRequest(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) { 
		SolModificacionDTO response = solicitudModificacionService.searchModificationRequest(numPagina, solicitudModificacionSearchDTO, request);
		return new ResponseEntity<SolModificacionDTO>(response, HttpStatus.OK);
	}
}
