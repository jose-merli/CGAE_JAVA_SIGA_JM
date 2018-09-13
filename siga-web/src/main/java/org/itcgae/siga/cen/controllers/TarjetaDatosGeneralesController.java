package org.itcgae.siga.cen.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.SociedadCreateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosGeneralesService;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class TarjetaDatosGeneralesController {

	@Autowired 
	private ITarjetaDatosGeneralesService tarjetaDatosGeneralesService;
	
//	@RequestMapping(value = "/personaJuridica/cargarFotografia", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UpdateResponseDTO> loadPhotography(@RequestBody PersonaJuridicaFotoDTO personaJuridicaFotoDTO, HttpServletRequest request, HttpServletResponse response) {
//		UpdateResponseDTO response1 = tarjetaDatosGeneralesService.loadPhotography(personaJuridicaFotoDTO, request, response);
//		
//		if(response1.getStatus().equals(SigaConstants.OK))
//			return new ResponseEntity<UpdateResponseDTO>(HttpStatus.OK);
//			else return new ResponseEntity<UpdateResponseDTO>(HttpStatus.FORBIDDEN);
//    }
	

	 @RequestMapping(value = "personaJuridica/cargarFotografia", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<ComboItem> loadPhotography(@RequestBody EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request, HttpServletResponse response) {
		 	ComboItem comboItem = tarjetaDatosGeneralesService.loadPhotography(etiquetaUpdateDTO,request,response);
			return new ResponseEntity<ComboItem>(comboItem, HttpStatus.OK);
	    }
	

	@RequestMapping(value = "personaJuridica/uploadFotografia", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadPhotography(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = tarjetaDatosGeneralesService.uploadPhotography(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosGeneralesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PersonaJuridicaDTO> searchGeneralData(@RequestParam("numPagina") int numPagina, @RequestBody PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request) { 
		PersonaJuridicaDTO response = tarjetaDatosGeneralesService.searchGeneralData(numPagina, personaJuridicaSearchDTO, request);
		return new ResponseEntity<PersonaJuridicaDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createLegalPerson(@RequestBody SociedadCreateDTO sociedadCreateDTO, HttpServletRequest request) { 
		InsertResponseDTO response = tarjetaDatosGeneralesService.createLegalPerson(sociedadCreateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO >(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO >(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateLegalPerson(@RequestBody EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosGeneralesService.updateLegalPerson(etiquetaUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO >(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO >(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/createLabel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createLabel(@RequestBody ComboItem items, HttpServletRequest request){
		InsertResponseDTO response = tarjetaDatosGeneralesService.createLabel(items, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO >(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO >(response, HttpStatus.FORBIDDEN);
	}
}