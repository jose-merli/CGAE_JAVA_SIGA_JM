package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
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
	
	
	@RequestMapping(value = "busquedaCensoGeneral/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaPerFisicaDTO> searchJuridica(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request) { 
		BusquedaPerFisicaDTO response = busquedaCensoGeneralService.search(numPagina, busquedaPerFisicaSearchDTO, request);
		return new ResponseEntity<BusquedaPerFisicaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCensoGeneral/searchExact", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaPerFisicaDTO> searchExact(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request) { 
		BusquedaPerFisicaDTO response = busquedaCensoGeneralService.searchExact(numPagina, busquedaPerFisicaSearchDTO, request);
		return new ResponseEntity<BusquedaPerFisicaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCensoGeneral/searchCliente",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NoColegiadoDTO> searchCliente(@RequestBody NoColegiadoItem noColegiadoItem, HttpServletRequest request) {
		NoColegiadoDTO response = busquedaCensoGeneralService.searchCliente(noColegiadoItem, request);
		return new ResponseEntity<NoColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCensoGeneral/searchColegiado",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> searchColegiado(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) {
		ColegiadoDTO response = busquedaCensoGeneralService.searchColegiado(colegiadoItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}

	
}
