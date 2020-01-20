package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

import org.itcgae.siga.scs.services.facturacionsjcs.ICombosServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CombosController {
	
	@Autowired
	private ICombosServices combosServices;
	
    @RequestMapping(value="/combo/comboFactEstados", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFactEstados(HttpServletRequest request) {
		ComboDTO response = combosServices.comboFactEstados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value="/combo/comboFactConceptos", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFactConceptos(HttpServletRequest request) {
		ComboDTO response = combosServices.comboFactConceptos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value="/combo/comboFactColegio", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboFactColegio(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboFactColegio(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value="/combo/comboPagosColegio", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboPagos(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboPagosColegio(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}
}
