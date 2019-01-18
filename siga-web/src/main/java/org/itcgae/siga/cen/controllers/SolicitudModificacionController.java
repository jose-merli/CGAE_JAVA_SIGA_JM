package org.itcgae.siga.cen.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ISolicitudModificacionService;
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
public class SolicitudModificacionController {

	@Autowired 
	private ISolicitudModificacionService solicitudModificacionService;
	
	@RequestMapping(value="solicitudModificacion/tipoModificacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoModificacion(HttpServletRequest request) {
		ComboDTO response = solicitudModificacionService.getComboTipoModificacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="solicitudModificacion/estado",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboEstado(HttpServletRequest request) {
		ComboDTO response = solicitudModificacionService.getComboEstado(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/searchModificationRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolModificacionDTO> searchModificationRequest(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) { 
		SolModificacionDTO response = solicitudModificacionService.searchModificationRequest(numPagina, solicitudModificacionSearchDTO, request);
		return new ResponseEntity<SolModificacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "solicitudModificacion/processGeneralModificationRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> processGeneralModificationRequest(@RequestBody ArrayList<SolModificacionItem> solModificacionDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = solicitudModificacionService.processGeneralModificationRequest(solModificacionDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "solicitudModificacion/denyGeneralModificationRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denyGeneralModificationRequest(@RequestBody ArrayList<SolModificacionItem> solModificacionDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = solicitudModificacionService.denyGeneralModificationRequest(solModificacionDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "solicitudModificacion/insertGeneralModificationRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertGeneralModificationRequest(@RequestBody SolModificacionItem solModificacionItem, HttpServletRequest request) {
		InsertResponseDTO response = solicitudModificacionService.insertGeneralModificationRequest(solModificacionItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO >(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO >(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "solicitudModificacion/verifyPerson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> verifyPerson(@RequestBody StringDTO nifCif, HttpServletRequest request) { 
		StringDTO response = solicitudModificacionService.verifyPerson(nifCif, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
}
