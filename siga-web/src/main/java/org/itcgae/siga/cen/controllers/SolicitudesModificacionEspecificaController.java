package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolicitModifDatosBasicosDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISolicitudesModificacionEspecificaService;
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
public class SolicitudesModificacionEspecificaController {

	@Autowired
	private ISolicitudesModificacionEspecificaService solicitudesModificacionEspecificaService;
	
	@RequestMapping(value = "solicitudModificacionEspecifica/searchModificacionDatosBasicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolicitModifDatosBasicosDTO> searchModificacionDatosBasicos(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) { 
		SolicitModifDatosBasicosDTO response = solicitudesModificacionEspecificaService.searchModificacionDatosBasicos(numPagina, solicitudModificacionSearchDTO, request);
		return new ResponseEntity<SolicitModifDatosBasicosDTO>(response, HttpStatus.OK);
	}
}
