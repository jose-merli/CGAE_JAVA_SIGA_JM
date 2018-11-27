package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.cen.services.ISolModifDatosCurricularesDetailService;
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
	ResponseEntity<StringDTO> searchDatosUseFotoDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		StringDTO response = solModifDatosUseFotoDetailService.searchDatosUseFotoDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosUseFotoDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> searchSolModifDatosUseFotoDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		StringDTO response = solModifDatosUseFotoDetailService.searchSolModifDatosUseFotoDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
}
