package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
//import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IFichaDatosColegialesService;
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
public class FichaDatosColegialesController {
	
	
	@Autowired
	private IFichaDatosColegialesService fichaDatosColegiales;
	
	@RequestMapping(value = "/fichaDatosColegiales/tratamiento",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelPerson(HttpServletRequest request) {
		ComboDTO response = fichaDatosColegiales.getTratamiento(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/tipoSeguro",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeInsurances(HttpServletRequest request) {
		ComboDTO response = fichaDatosColegiales.getTypeInsurances(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> searchColegialesData(@RequestParam("numPagina") int numPagina, @RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		ColegiadoDTO response = fichaDatosColegiales.datosColegialesSearch(numPagina, colegiadoItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	
}
