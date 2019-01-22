package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.cen.services.ISolModifDatosBancariosDetailService;
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
public class SolModifDatosBancariosDetailController {

	@Autowired
	private ISolModifDatosBancariosDetailService solModifDatosBancariosDetailService;
	
	@RequestMapping(value = "solicitudModificacion/searchDatosBancariosDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModifDatosBancariosItem> searchDatosBancariosDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SolModifDatosBancariosItem response = solModifDatosBancariosDetailService.searchDatosBancariosDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SolModifDatosBancariosItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosBancariosDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModifDatosBancariosItem> searchSolModifDatosBancariosDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SolModifDatosBancariosItem response = solModifDatosBancariosDetailService.searchSolModifDatosBancariosDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SolModifDatosBancariosItem>(response, HttpStatus.OK);
	}
}
