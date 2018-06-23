package org.itcgae.siga.cen.controllers;



import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
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
public class TarjetaDatosIntegrantesController {

	@Autowired 
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosIntegrantesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosIntegrantesDTO> searchIntegrantesData(@RequestParam("numPagina") int numPagina, @RequestBody DatosIntegrantesSearchDTO datosIntegrantesSearchDTO, HttpServletRequest request) { 
		DatosIntegrantesDTO response = tarjetaDatosIntegrantesService.searchIntegrantesData(numPagina, datosIntegrantesSearchDTO, request);
		return new ResponseEntity<DatosIntegrantesDTO >(response, HttpStatus.OK);
	}
	
	

}
