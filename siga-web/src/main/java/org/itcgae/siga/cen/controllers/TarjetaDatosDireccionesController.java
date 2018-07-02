package org.itcgae.siga.cen.controllers;



import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
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
public class TarjetaDatosDireccionesController {


	@Autowired 
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	

	
	
	@RequestMapping(value = "busquedaPerJuridica/datosDireccionesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosDireccionesDTO> searchIntegrantesData(@RequestParam("numPagina") int numPagina, @RequestBody DatosDireccionesSearchDTO datosDireccionesSearchDTO, HttpServletRequest request) { 
		DatosDireccionesDTO response = tarjetaDatosDireccionesService.datosDireccionesSearch(numPagina, datosDireccionesSearchDTO, request);
		return new ResponseEntity<DatosDireccionesDTO >(response, HttpStatus.OK);
	}
	
	 
	
}
