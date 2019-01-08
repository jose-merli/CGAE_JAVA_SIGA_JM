package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.cen.services.IBusquedaCensoGeneralService;
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
public class BusquedaCensoGeneralController {
	
	
	@Autowired
	private IBusquedaCensoGeneralService busquedaCensoGeneralService;
	
	
	@RequestMapping(value = "busquedaCensoGeneral/searchNif", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaPerFisicaDTO> searchJuridica(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request) { 
		BusquedaPerFisicaDTO response = busquedaCensoGeneralService.searchByNif(numPagina, busquedaPerFisicaSearchDTO, request);
		return new ResponseEntity<BusquedaPerFisicaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCensoGeneral/searchName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaPerFisicaDTO> searchPerFisica(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request) { 
		BusquedaPerFisicaDTO response = busquedaCensoGeneralService.searchByName(numPagina, busquedaPerFisicaSearchDTO, request);
		return new ResponseEntity<BusquedaPerFisicaDTO>(response, HttpStatus.OK);
	} 
	//
	
}
