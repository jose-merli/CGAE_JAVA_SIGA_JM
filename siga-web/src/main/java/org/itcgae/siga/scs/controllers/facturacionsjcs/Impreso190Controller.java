package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.scs.services.facturacionsjcs.IImpreso190Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Impreso190Controller {

	//@Autowired
	//private IImpreso190Service impresoService;
	
	/*@RequestMapping(value="/facturacionsjcs/impreso190generar", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Impreso190DTO> impreso190generar(@RequestBody Impreso190Item impreso190Item, HttpServletRequest request){
		Impreso190DTO response = impresoService.impreso190generar(impreso190Item, request);
		return new ResponseEntity<Impreso190DTO>(response, HttpStatus.OK);
	}*/
	
}
