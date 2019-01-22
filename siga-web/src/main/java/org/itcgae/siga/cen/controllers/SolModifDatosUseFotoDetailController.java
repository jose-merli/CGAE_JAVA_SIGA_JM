package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModifFotoItem;
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
public class SolModifDatosUseFotoDetailController {

	@Autowired
	private ISolModifDatosUseFotoDetailService solModifDatosUseFotoDetailService;
	
	@RequestMapping(value = "solicitudModificacion/searchDatosUseFotoDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SoliModifFotoItem> searchDatosUseFotoDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SoliModifFotoItem response = solModifDatosUseFotoDetailService.searchDatosUseFotoDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SoliModifFotoItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosUseFotoDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SoliModifFotoItem> searchSolModifDatosUseFotoDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SoliModifFotoItem response = solModifDatosUseFotoDetailService.searchSolModifDatosUseFotoDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SoliModifFotoItem>(response, HttpStatus.OK);
	}
}
