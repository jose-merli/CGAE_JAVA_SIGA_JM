package org.itcgae.siga.cen.controllers;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.cen.services.impl.IFichaColegialOtrasColegiacionesService;
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
public class FichaColegialOtrasColegiacionesController {

	@Autowired
	private IFichaColegialOtrasColegiacionesService iFichaColegialOtrasColegiacionesService;
	
	@RequestMapping(value = "fichaColegialOtrasColegiaciones/searchOtherCollegues", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> searchOtherCollegues(@RequestParam("numPagina") int numPagina, @RequestBody String idPersona, HttpServletRequest request) { 
		ColegiadoDTO response = iFichaColegialOtrasColegiacionesService.searchOtherCollegues(numPagina, idPersona, request);
		return new ResponseEntity<ColegiadoDTO >(response, HttpStatus.OK);
	}
}