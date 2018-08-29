package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaPerService;
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
public class BusquedaPerController {
	
	
	@Autowired
	private IBusquedaPerService busquedaPerService;
	
	@RequestMapping(value = "/busquedaPer/colegio",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelColegios(HttpServletRequest request) {
		ComboDTO response = busquedaPerService.getLabelColegios(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/searchJuridica", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaPerJuridicaDTO> searchJuridica(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO, HttpServletRequest request) { 
		BusquedaPerJuridicaDTO response = busquedaPerService.searchPerJuridica(numPagina, busquedaPerJuridicaSearchDTO, request);
		return new ResponseEntity<BusquedaPerJuridicaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/searchFisica", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaPerFisicaDTO> searchPerFisica(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaPerFisicaSearchDTO busquedaPerJuridicaSearchDTO, HttpServletRequest request) { 
		BusquedaPerFisicaDTO response = busquedaPerService.searchPerFisica(numPagina, busquedaPerJuridicaSearchDTO, request);
		return new ResponseEntity<BusquedaPerFisicaDTO>(response, HttpStatus.OK);
	} 
	//
	
}
