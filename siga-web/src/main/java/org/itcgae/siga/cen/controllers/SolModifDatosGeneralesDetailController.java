package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.cen.services.ISolModifDatosGeneralesDetailService;
import org.itcgae.siga.cen.services.ISolModifDatosUseFotoDetailService;
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
public class SolModifDatosGeneralesDetailController {

	@Autowired
	private ISolModifDatosGeneralesDetailService solModifDatosGeneralesDetailService;
	
	@RequestMapping(value = "solicitudModificacion/searchDatosGeneralesDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> searchDatosGeneralesDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		StringDTO response = solModifDatosGeneralesDetailService.searchDatosGeneralesDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosGeneralesDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> searchSolModifDatosGeneralesDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		StringDTO response = solModifDatosGeneralesDetailService.searchSolModifDatosGeneralesDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
}
