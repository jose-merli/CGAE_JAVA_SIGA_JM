package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.cen.services.IFichaColegialSociedadesService;
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
public class FichaColegialSociedadesController {

	@Autowired
	private IFichaColegialSociedadesService iFichaColegialSociedadesService;
	
	@RequestMapping(value = "fichaColegialSociedades/searchSocieties", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaJuridicaDTO> searchSocieties(@RequestParam("numPagina") int numPagina, @RequestBody String idPersona, HttpServletRequest request) { 
		BusquedaJuridicaDTO response = iFichaColegialSociedadesService.searchSocieties(numPagina, idPersona, request);
		return new ResponseEntity<BusquedaJuridicaDTO >(response, HttpStatus.OK);
	}
}
