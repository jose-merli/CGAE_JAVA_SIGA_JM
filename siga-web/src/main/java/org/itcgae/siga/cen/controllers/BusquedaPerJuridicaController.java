package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaPerJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusquedaPerJuridicaController {
	
	
	@Autowired
	private IBusquedaPerJuridicaService busquedaPerJuridicaService;
	
	@RequestMapping(value = "busquedaPerJuridica/tipoSociedad",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getSocietyTypes( HttpServletRequest request) {
		ComboDTO response = busquedaPerJuridicaService.getSocietyTypes(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/etiqueta",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabel( HttpServletRequest request) {
		ComboDTO response = busquedaPerJuridicaService.getLabel(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	
}
