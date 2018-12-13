package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.com.services.IConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/consultas")
public class ConsultasController {

	@Autowired
	IConsultasService _consultasService;
	
	/**COMBO DE MODULOS**/
	@RequestMapping(value = "/modulo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request) {
		
		ComboDTO response = _consultasService.modulo(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**COMBO DE OBJETIVOS**/
	@RequestMapping(value = "/objetivo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboObjetivos(HttpServletRequest request) {
		
		ComboDTO response = _consultasService.objetivo(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**COMBO CLASE DE COMUNICACIONES**/
	@RequestMapping(value = "/claseComunicacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClaseComunicacion(HttpServletRequest request) {
		
		ComboDTO response = _consultasService.claseComunicacion(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
