package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;
import org.itcgae.siga.cen.services.IFichaDatosCurricularesService;
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
public class FichaDatosCurricularesController {
	
	
	@Autowired
	private IFichaDatosCurricularesService fichaDatosCurriculares;
	
	@RequestMapping(value = "/fichaDatosCurriculares/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FichaDatosCurricularesDTO> searchDatosCurriculares(@RequestParam("numPagina") int numPagina, @RequestBody FichaDatosCurricularesSearchDTO fichaDatosCurricularesSearchDTO, HttpServletRequest request) {
		FichaDatosCurricularesDTO response = fichaDatosCurriculares.searchDatosCurriculares(fichaDatosCurricularesSearchDTO, request);
		return new ResponseEntity<FichaDatosCurricularesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosCurriculares/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteDatosCurriculares(@RequestBody FichaDatosCurricularesDTO fichaDatosCurricularesDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosCurriculares.deleteDatosCurriculares(fichaDatosCurricularesDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosCurriculares/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosCurriculares(@RequestBody FichaDatosCurricularesItem fichaDatosCurricularesItem, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosCurriculares.updateDatosCurriculares(fichaDatosCurricularesItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosCurriculares/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertDatosCurriculares(@RequestBody FichaDatosCurricularesItem fichaDatosCurricularesItem, HttpServletRequest request) { 
		InsertResponseDTO response = fichaDatosCurriculares.insertDatosCurriculares(fichaDatosCurricularesItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "/fichaDatosCurriculares/solicitudUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> solicitudUpdateDatosCurriculares(@RequestBody FichaDatosCurricularesItem fichaDatosCurricularesItem, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosCurriculares.solicitudUpdateDatosCurriculares(fichaDatosCurricularesItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
}
