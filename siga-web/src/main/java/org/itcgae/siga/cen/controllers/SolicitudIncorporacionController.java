package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ISolicitudIncorporacionService;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
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
@RequestMapping(value = "/solicitudIncorporacion")
public class SolicitudIncorporacionController {
	
	@Autowired 
	private ISolicitudIncorporacionService _solicitudIncorporacionService;
	
	@Autowired
	private ITarjetaDatosDireccionesService _tarjetaDatosDireccionesService;
	
	
	
	@RequestMapping(value = "/searchSolicitud", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SolIncorporacionDTO> searchSolicitudesData(@RequestParam("numPagina") int numPagina, @RequestBody SolicitudIncorporacionSearchDTO DatosSolicitudSearchDTO, HttpServletRequest request) {
		
		SolIncorporacionDTO response = _solicitudIncorporacionService.datosSolicitudSearch(numPagina, DatosSolicitudSearchDTO, request);
		return new ResponseEntity<SolIncorporacionDTO>(response, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/tipoSolicitud",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelTipoSolicitud(HttpServletRequest request) {
		ComboDTO response = _solicitudIncorporacionService.getTipoSolicitud(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/estadoSolicitud",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelEstadoSolicitud(HttpServletRequest request) {
		ComboDTO response = _solicitudIncorporacionService.getEstadoSolicitud(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/pais",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPaises(HttpServletRequest request) {
		ComboDTO response = _tarjetaDatosDireccionesService.getPais(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/tratamiento",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTratamiento(HttpServletRequest request) {
		ComboDTO response = _solicitudIncorporacionService.getTratamiento(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/estadoCivil",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getEstadoCivil(HttpServletRequest request) {
		ComboDTO response = _solicitudIncorporacionService.getEstadoCivil(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
