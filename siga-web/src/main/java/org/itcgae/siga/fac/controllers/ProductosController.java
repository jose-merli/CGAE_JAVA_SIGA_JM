package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductosController {
	@Autowired 
	private IProductosService productosService;
	
	@PostMapping(value = "/pys/buscarProductos")
	ResponseEntity<ListaProductosDTO> listadoProductos(HttpServletRequest request, @RequestBody FiltroProductoItem filtroProductoItem) { 
		ListaProductosDTO response = productosService.searchListadoProductos(request, filtroProductoItem);
		return new ResponseEntity<ListaProductosDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/detalleProducto")
	ResponseEntity<ProductoDetalleDTO> detalleProducto(HttpServletRequest request, @RequestBody ListaProductosItem producto) { 
		ProductoDetalleDTO response = productosService.detalleProducto(request, producto);
		return new ResponseEntity<ProductoDetalleDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/nuevoProducto")
	ResponseEntity<InsertResponseDTO> nuevoProducto(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request){
		InsertResponseDTO response = productosService.nuevoProducto(producto, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
//	@PostMapping(value="/pys/editarProducto")
//	ResponseEntity<DeleteResponseDTO> editarProducto(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request){
//		DeleteResponseDTO response = tiposProductosService.modificarProducto(listadoProductos, request);
//		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
//	}
	
	@GetMapping(value = "/combo/tipoIva")
	ResponseEntity<ComboDTO> comboTiposIva(HttpServletRequest request) { 
		ComboDTO response = productosService.comboIva(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoFormaPago")
	ResponseEntity<ComboDTO> comboTipoFormaPago(HttpServletRequest request) { 
		ComboDTO response = productosService.comboTipoFormaPago(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
