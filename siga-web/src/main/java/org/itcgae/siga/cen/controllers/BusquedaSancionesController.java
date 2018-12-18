package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaSancionesService;
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
public class BusquedaSancionesController {

	@Autowired
	private IBusquedaSancionesService busquedaSancionesService;

	@RequestMapping(value = "busquedaSanciones/comboTipoSancion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoSancion( HttpServletRequest request) {
		ComboDTO response = busquedaSancionesService.getComboTipoSancion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaSanciones/searchBusquedaSanciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaSancionesDTO> searchBusquedaSanciones(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request) { 
		BusquedaSancionesDTO response = busquedaSancionesService.searchBusquedaSanciones(numPagina, busquedaSancionesSearchDTO, request);
		return new ResponseEntity<BusquedaSancionesDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaSanciones/insertSanction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertSanction(@RequestBody BusquedaSancionesItem busquedaSancionesItem, HttpServletRequest request) throws Exception { 
		InsertResponseDTO response = busquedaSancionesService.insertSanction(busquedaSancionesItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "busquedaSanciones/updateSanction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateSanction(@RequestBody BusquedaSancionesItem busquedaSancionesItem, HttpServletRequest request) throws Exception { 
		UpdateResponseDTO response = busquedaSancionesService.updateSanction(busquedaSancionesItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
