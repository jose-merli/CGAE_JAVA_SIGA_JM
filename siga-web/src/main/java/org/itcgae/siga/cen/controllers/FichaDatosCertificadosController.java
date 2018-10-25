package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.CertificadoDTO;
import org.itcgae.siga.cen.services.IFichaDatosCertificadosService;
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
public class FichaDatosCertificadosController {
	
	
	@Autowired
	private IFichaDatosCertificadosService fichaDatosCertificados;
	
//	@RequestMapping(value = "/fichaDatosColegiales/tratamiento",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getLabelPerson(HttpServletRequest request) {
//		ComboDTO response = fichaDatosColegiales.getTratamiento(request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "/fichaDatosColegiales/tipoSeguro",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getTypeInsurances(HttpServletRequest request) {
//		ComboDTO response = fichaDatosColegiales.getTypeInsurances(request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}
//	
	@RequestMapping(value = "/fichaDatosCertificados/datosCertificadosSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CertificadoDTO> datosCertificadosSearch(@RequestParam("numPagina") int numPagina, @RequestBody String idPersona, HttpServletRequest request) { 
		CertificadoDTO response = fichaDatosCertificados.datosCertificadosSearch(numPagina, idPersona, request);
		return new ResponseEntity<CertificadoDTO>(response, HttpStatus.OK);
	}
	
	
}
