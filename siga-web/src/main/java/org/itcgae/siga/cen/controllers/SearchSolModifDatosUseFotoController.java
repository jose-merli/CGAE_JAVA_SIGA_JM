package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosUseFotoService;
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
public class SearchSolModifDatosUseFotoController {

	@Autowired 
	private ISearchSolModifDatosUseFotoService searchSolModifDatosUseFotoService;
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosUseFoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModificacionDTO> searchSolModifDatosUseFoto(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) { 
		SolModificacionDTO response = searchSolModifDatosUseFotoService.searchSolModifDatosUseFoto(numPagina, solicitudModificacionSearchDTO, request);
		return new ResponseEntity<SolModificacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/processSolModifDatosUseFoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> processSolModifDatosUseFoto(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = searchSolModifDatosUseFotoService.processSolModifDatosUseFoto(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "solicitudModificacion/denySolModifDatosUseFoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denySolModifDatosUseFoto(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = searchSolModifDatosUseFotoService.denySolModifDatosUseFoto(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
