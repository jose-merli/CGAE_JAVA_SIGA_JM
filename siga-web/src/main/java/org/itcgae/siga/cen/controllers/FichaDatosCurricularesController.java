package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;
import org.itcgae.siga.cen.services.IFichaDatosCurricularesService;
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
	
	
}
