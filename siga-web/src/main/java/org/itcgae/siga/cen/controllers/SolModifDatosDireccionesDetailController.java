package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.cen.services.ISolModifDatosDireccionesDetailService;
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
public class SolModifDatosDireccionesDetailController {

	@Autowired
	private ISolModifDatosDireccionesDetailService solModifDatosDireccionesDetailService;
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosDireccionesDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SoliModiDireccionesItem> searchSolModifDatosDireccionesDetail(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SoliModiDireccionesItem response = solModifDatosDireccionesDetailService.searchSolModifDatosDireccionesDetail(numPagina, solModificacionItem, request);
		return new ResponseEntity<SoliModiDireccionesItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchDirecciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SoliModiDireccionesItem> searchDatosDirecciones(@RequestParam("numPagina") int numPagina, @RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		SoliModiDireccionesItem response = solModifDatosDireccionesDetailService.searchDatosDirecciones(numPagina, solModificacionItem, request);
		return new ResponseEntity<SoliModiDireccionesItem>(response, HttpStatus.OK);
	}
}
