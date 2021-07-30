package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.fac.services.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiciosController {
	
	@Autowired 
	private IServiciosService serviciosService;

	@PostMapping(value = "/pys/buscarServicios")
	ResponseEntity<ListaServiciosDTO> listadoServicios(HttpServletRequest request, @RequestBody FiltroServicioItem filtroServicioItem) { 
		ListaServiciosDTO response = serviciosService.searchListadoServicios(request, filtroServicioItem);
		return new ResponseEntity<ListaServiciosDTO>(response, HttpStatus.OK);
	}
}
