package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosExpedientesService;
import org.itcgae.siga.commons.constants.SigaConstants;
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
public class SearchSolModifDatosExpedientesController {

	@Autowired 
	private ISearchSolModifDatosExpedientesService searchSolModifDatosExpedientesService;
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosExpedientes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModificacionDTO> searchSolModifDatosExpedientes(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) { 
		SolModificacionDTO response = searchSolModifDatosExpedientesService.searchSolModifDatosExpedientes(numPagina, solicitudModificacionSearchDTO, request);
		return new ResponseEntity<SolModificacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/processSolModifDatosExpedientes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> processSolModifDatosExpedientes(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = searchSolModifDatosExpedientesService.processSolModifDatosExpedientes(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "solicitudModificacion/denySolModifDatosExpedientes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denySolModifDatosExpedientes(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = searchSolModifDatosExpedientesService.denySolModifDatosExpedientes(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
