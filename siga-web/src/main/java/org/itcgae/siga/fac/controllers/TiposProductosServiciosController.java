package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.fac.services.ITiposProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pys")
public class TiposProductosServiciosController {
	
	@Autowired 
	private ITiposProductosService tiposProductosService;
	
	@GetMapping(value = "/listadoTipoProducto")
	ResponseEntity<ListadoTipoProductoDTO> listadoTipoProducto(HttpServletRequest request) { 
		ListadoTipoProductoDTO response = tiposProductosService.searchTiposProductos(request);
		return new ResponseEntity<ListadoTipoProductoDTO>(response, HttpStatus.OK);
	}
	

}
