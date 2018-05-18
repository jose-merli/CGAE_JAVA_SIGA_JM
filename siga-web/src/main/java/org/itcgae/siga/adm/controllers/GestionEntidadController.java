package org.itcgae.siga.adm.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.EntidadLenguajeInstitucionDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IGestionEntidadService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class GestionEntidadController {	

	@Autowired
	private IGestionEntidadService gestionEntidadService;
	
	@RequestMapping(value = "/entidad/lenguajeInstitucion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EntidadLenguajeInstitucionDTO> getInstitutionLenguage(HttpServletRequest request) {
		EntidadLenguajeInstitucionDTO response = gestionEntidadService.getInstitutionLenguage(request);
		return new ResponseEntity<EntidadLenguajeInstitucionDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/entidad/lenguaje", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLenguages(HttpServletRequest request) {
		ComboDTO response = gestionEntidadService.getLenguages(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "entidad/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = gestionEntidadService.uploadFile(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "/entidad/uploadLenguage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
 	ResponseEntity<UpdateResponseDTO> uploadLenguage(@RequestBody String idLenguaje, HttpServletRequest request) { 
		UpdateResponseDTO response = gestionEntidadService.uploadLenguage(idLenguaje, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
}
