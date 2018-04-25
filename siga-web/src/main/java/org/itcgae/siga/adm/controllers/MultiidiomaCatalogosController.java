package org.itcgae.siga.adm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IMultiidiomaCatalogosService;
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
public class MultiidiomaCatalogosController {

	@Autowired
	private IMultiidiomaCatalogosService multiidiomaCatalogosService;
	
	@RequestMapping(value = "/catalogos/lenguaje", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCatalogLenguage() {
		ComboDTO response = multiidiomaCatalogosService.getCatalogLenguage();
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/catalogos/entidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCatalogEntity(HttpServletRequest request) {
		ComboDTO response = multiidiomaCatalogosService.getCatalogEntity(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/catalogos/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MultiidiomaCatalogoDTO> getCatalogSearch(@RequestParam("numPagina") int numPagina, @RequestBody MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO, HttpServletRequest request) { 
		MultiidiomaCatalogoDTO response = multiidiomaCatalogosService.catalogSearch(numPagina, multiidiomaCatalogoSearchDTO, request);
		return new ResponseEntity<MultiidiomaCatalogoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/catalogos/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> getCatalogUpdate(@RequestBody MultiidiomaCatalogoUpdateDTO multiidiomaCatalogoUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = multiidiomaCatalogosService.catalogUpdate(multiidiomaCatalogoUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
}
