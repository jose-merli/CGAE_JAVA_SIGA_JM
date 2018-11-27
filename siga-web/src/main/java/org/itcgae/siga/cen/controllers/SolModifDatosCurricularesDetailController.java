package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.cen.services.ISolModifDatosCurricularesDetailService;
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
public class SolModifDatosCurricularesDetailController {

	@Autowired
	private ISolModifDatosCurricularesDetailService solModifDatosCurricularesDetailService;
	
	@RequestMapping(value = "solicitudModificacion/searchDatosCurricularesDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModifDatosCurricularesItem> searchDatosCurricularesDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SolModifDatosCurricularesItem response = solModifDatosCurricularesDetailService.searchDatosCurricularesDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SolModifDatosCurricularesItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosCurricularesDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModifDatosCurricularesItem> searchSolModifDatosCurricularesDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SolModifDatosCurricularesItem response = solModifDatosCurricularesDetailService.searchSolModifDatosCurricularesDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SolModifDatosCurricularesItem>(response, HttpStatus.OK);
	}
}
