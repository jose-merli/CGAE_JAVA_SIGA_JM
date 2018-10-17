package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITipoCurricularService;
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
public class TipoCurricularController {

	@Autowired
	private ITipoCurricularService tipoCurricularService;
	
	@RequestMapping(value="tipoCurricular/categoriaCurricular",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboCategoriaCurricular(HttpServletRequest request) {
		ComboDTO response = tipoCurricularService.getComboCategoriaCurricular(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tipoCurricular/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TipoCurricularDTO> search(@RequestParam("numPagina") int numPagina, @RequestBody TipoCurricularItem tipoCurricularItem, HttpServletRequest request) { 
		TipoCurricularDTO response = tipoCurricularService.search(numPagina, tipoCurricularItem, request);
		return new ResponseEntity<TipoCurricularDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tipoCurricular/createTipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createTipoCurricular(@RequestBody TipoCurricularItem tipoCurricularItem, HttpServletRequest request) throws Exception { 
		InsertResponseDTO response = tipoCurricularService.createTipoCurricular(tipoCurricularItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
