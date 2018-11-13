package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaSancionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusquedaSancionesController {

	@Autowired
	private IBusquedaSancionesService busquedaSancionesService;

	@RequestMapping(value = "busquedaSanciones/comboTipoSancion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoSancion( HttpServletRequest request) {
		ComboDTO response = busquedaSancionesService.getComboTipoSancion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
