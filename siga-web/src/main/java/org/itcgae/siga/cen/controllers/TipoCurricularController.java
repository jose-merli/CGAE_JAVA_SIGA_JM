package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CatalogoMaestroDTO;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDeleteDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
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

	@RequestMapping(value = "tipoCurricular/categoriaCurricular", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboCategoriaCurricular(HttpServletRequest request) {
		ComboDTO response = tipoCurricularService.getComboCategoriaCurricular(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "tipoCurricular/searchTipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TipoCurricularDTO> searchTipoCurricular(@RequestParam("numPagina") int numPagina,
			@RequestBody TipoCurricularItem tipoCurricularItem, HttpServletRequest request) {
		TipoCurricularDTO response = tipoCurricularService.searchTipoCurricular(numPagina, tipoCurricularItem, request);
		return new ResponseEntity<TipoCurricularDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "tipoCurricular/createTipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createTipoCurricular(@RequestBody TipoCurricularItem tipoCurricularItem,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO response = tipoCurricularService.createTipoCurricular(tipoCurricularItem, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "tipoCurricular/updateTipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> updateTipoCurricular(@RequestBody TipoCurricularDTO tipoCurricularDTO,
			HttpServletRequest request) {
		UpdateResponseDTO response = tipoCurricularService.updateTipoCurricular(tipoCurricularDTO, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "tipoCurricular/deleteTipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteTipoCurricular(@RequestBody TipoCurricularDTO tipoCurricularDTO,
			HttpServletRequest request) {
		DeleteResponseDTO response = tipoCurricularService.deleteTipoCurricular(tipoCurricularDTO, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "tipoCurricular/historyTipoCurricular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TipoCurricularDTO> getHistory(@RequestBody TipoCurricularItem tipoCurricularItem,
			HttpServletRequest request) {
		TipoCurricularDTO response = tipoCurricularService.getHistory(tipoCurricularItem, request);
		return new ResponseEntity<TipoCurricularDTO>(response, HttpStatus.OK);
	}
}
