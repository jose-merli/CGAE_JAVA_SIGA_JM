package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ISearchSolModifDatosCambiarFotoService;
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
public class SearchSolModifDatosCambiarFotoController {

	@Autowired 
	private ISearchSolModifDatosCambiarFotoService searchSolModifDatosCambiarFotoService;
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosCambiarFoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModificacionDTO> searchSolModifDatosCambiarFoto(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) { 
		SolModificacionDTO response = searchSolModifDatosCambiarFotoService.searchSolModifDatosCambiarFoto(numPagina, solicitudModificacionSearchDTO, request);
		return new ResponseEntity<SolModificacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchSolModifDatosCambiarFotoDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboItem> searchSolModifDatosCambiarFotoDetail(@RequestBody  SolModificacionItem solModificacionItem, HttpServletRequest request,  HttpServletResponse response) { 
		ComboItem comboItem = searchSolModifDatosCambiarFotoService.searchSolModifDatosCambiarFotoDetail(solModificacionItem, request, response);
		return new ResponseEntity<ComboItem>(comboItem, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/processSolModifDatosCambiarFoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> processSolModifDatosCambiarFoto(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = searchSolModifDatosCambiarFotoService.processSolModifDatosCambiarFoto(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
//	
	@RequestMapping(value = "solicitudModificacion/denySolModifDatosCambiarFoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denySolModifDatosCambiarFoto(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = searchSolModifDatosCambiarFotoService.denySolModifDatosCambiarFoto(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
