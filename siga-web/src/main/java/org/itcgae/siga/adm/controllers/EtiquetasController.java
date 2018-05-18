package org.itcgae.siga.adm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.EtiquetaDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaSearchDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IEtiquetasService;
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
public class EtiquetasController {

	@Autowired
	private IEtiquetasService etiquetasService;
	
	@RequestMapping(value = "/etiquetas/lenguaje", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getParameterModules() {
		ComboDTO response = etiquetasService.getLabelLenguage();
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/etiquetas/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EtiquetaDTO> getParametersSearch(@RequestParam("numPagina") int numPagina, @RequestBody EtiquetaSearchDTO etiquetaSearchDTO, HttpServletRequest request) { 
		EtiquetaDTO response = etiquetasService.searchLabels(numPagina, etiquetaSearchDTO, request);
		return new ResponseEntity<EtiquetaDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/etiquetas/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> getParametersSearch(@RequestBody EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = etiquetasService.updateLabel(etiquetaUpdateDTO, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
}
