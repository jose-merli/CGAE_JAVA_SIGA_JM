package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ComboSubtiposCVDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.cen.services.ISubtipoCurricularService;
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
public class SubtipoCurricularController {

	@Autowired
	private ISubtipoCurricularService subtipoCurricularService;
	
	@RequestMapping(value = "subtipoCurricular/searchSubtipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SubtipoCurricularDTO> searchSubtipoCurricular(@RequestParam("numPagina") int numPagina,
			@RequestBody SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request) {
		SubtipoCurricularDTO response = subtipoCurricularService.searchSubtipoCurricular(numPagina, subtipoCurricularItem, request);
		return new ResponseEntity<SubtipoCurricularDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "subtipoCurricular/getCurricularSubtypeCombo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboSubtiposCVDTO> getCurricularSubtypeCombo(@RequestParam("idTipoCV") String idTipoCV, 
			@RequestParam("historico") boolean historico, HttpServletRequest request) {
		ComboSubtiposCVDTO response = subtipoCurricularService.getCurricularSubtypeCombo(idTipoCV, historico, request);
		return new ResponseEntity<ComboSubtiposCVDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "subtipoCurricular/createSubtipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createSubtipoCurricular(@RequestBody SubtipoCurricularItem subtipoCurricularItem,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO response = subtipoCurricularService.createSubtipoCurricular(subtipoCurricularItem, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "subtipoCurricular/updateSubtipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> updateSubtipoCurricular(@RequestBody SubtipoCurricularDTO subtipoCurricularDTO,
			HttpServletRequest request) {
		UpdateResponseDTO response = subtipoCurricularService.updateSubtipoCurricular(subtipoCurricularDTO, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "subtipoCurricular/deleteSubtipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteSubtipoCurricular(@RequestBody SubtipoCurricularDTO subtipoCurricularDTO,
			HttpServletRequest request) {
		DeleteResponseDTO response = subtipoCurricularService.deleteSubtipoCurricular(subtipoCurricularDTO, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "subtipoCurricular/historySubtipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SubtipoCurricularDTO> getHistory(@RequestBody SubtipoCurricularItem subtipoCurricularItem,
			HttpServletRequest request) {
		SubtipoCurricularDTO response = subtipoCurricularService.getHistory(subtipoCurricularItem, request);
		return new ResponseEntity<SubtipoCurricularDTO>(response, HttpStatus.OK);
	}
	
}
