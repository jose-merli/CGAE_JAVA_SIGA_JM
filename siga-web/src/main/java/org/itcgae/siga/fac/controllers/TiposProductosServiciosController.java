package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.ITiposProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiposProductosServiciosController {
	
	@Autowired 
	private ITiposProductosService tiposProductosService;
	
	@GetMapping(value = "/pys/listadoTipoProducto")
	ResponseEntity<ListadoTipoProductoDTO> listadoTipoProducto(HttpServletRequest request) { 
		ListadoTipoProductoDTO response = tiposProductosService.searchTiposProductos(request);
		return new ResponseEntity<ListadoTipoProductoDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/listadoTipoProductoHistorico")
	ResponseEntity<ListadoTipoProductoDTO> listadoTipoProductoHistorico(HttpServletRequest request) { 
		ListadoTipoProductoDTO response = tiposProductosService.searchTiposProductosHistorico(request);
		return new ResponseEntity<ListadoTipoProductoDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoProductos")
	ResponseEntity<ComboDTO> comboTiposProductos(HttpServletRequest request) { 
		ComboDTO response = tiposProductosService.comboTiposProductos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/crearProducto")
	ResponseEntity<InsertResponseDTO> crearProducto(@RequestBody ListadoTipoProductoDTO listadoProductos, HttpServletRequest request){
		InsertResponseDTO response = tiposProductosService.crearProducto(listadoProductos, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/activarDesactivarProducto")
	ResponseEntity<ProductoDTO> activarDesactivarProducto(@RequestBody ListadoTipoProductoDTO listadoProductos, HttpServletRequest request){
		ProductoDTO response = tiposProductosService.activarDesactivarProducto(listadoProductos, request);
		return new ResponseEntity<ProductoDTO>(response, HttpStatus.OK);
	}
}
