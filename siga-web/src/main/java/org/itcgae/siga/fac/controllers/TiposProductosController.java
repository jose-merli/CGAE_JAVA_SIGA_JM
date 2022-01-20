package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.fac.services.ITiposProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiposProductosController {
	
	@Autowired 
	private ITiposProductosService tiposProductosService;
	
	//Datos tabla pantalla Maestros --> Tipos Productos
	@GetMapping(value = "/pys/listadoTipoProducto")
	ResponseEntity<ListadoTipoProductoDTO> listadoTipoProducto(HttpServletRequest request) { 
		ListadoTipoProductoDTO response = tiposProductosService.searchTiposProductos(request);
		return new ResponseEntity<ListadoTipoProductoDTO>(response, HttpStatus.OK);
	}
	
	//Datos con historico (incluidos registros con fechabaja != null) tabla pantalla Maestros --> Tipos Productos
	@GetMapping(value = "/pys/listadoTipoProductoHistorico")
	ResponseEntity<ListadoTipoProductoDTO> listadoTipoProductoHistorico(HttpServletRequest request) { 
		ListadoTipoProductoDTO response = tiposProductosService.searchTiposProductosHistorico(request);
		return new ResponseEntity<ListadoTipoProductoDTO>(response, HttpStatus.OK);
	}
	
	//Obtiene los datos del combo categoria de productos (PYS_TIPOSPRODUCTOS)
	@GetMapping(value = "/combo/tipoProductos")
	ResponseEntity<ComboDTO> comboTiposProductos(HttpServletRequest request) { 
		ComboDTO response = tiposProductosService.comboTiposProductos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	//Crea/edita los tipos de productos (PYS_PRODUCTOS)
	@PostMapping(value="/pys/crearEditarProducto")
	ResponseEntity<DeleteResponseDTO> crearEditarProducto(@RequestBody ListadoTipoProductoDTO listadoProductos, HttpServletRequest request){
		DeleteResponseDTO response = new DeleteResponseDTO();
		try {
			 response = tiposProductosService.crearEditarProducto(listadoProductos, request);
			
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e){
			response.error(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Realiza un borrado logico (establecer fechabaja = new Date()) o lo reactiva en caso de que este inhabilitado.
	@PostMapping(value="/pys/activarDesactivarProducto")
	ResponseEntity<ProductoDTO> activarDesactivarProducto(@RequestBody ListadoTipoProductoDTO listadoProductos, HttpServletRequest request){
		ProductoDTO response = tiposProductosService.activarDesactivarProducto(listadoProductos, request);
		return new ResponseEntity<ProductoDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/listadoTipoProductoByIdCategoria")
	ResponseEntity<ComboDTO> listadoTipoProductoByIdCategoria(HttpServletRequest request, @RequestParam String idCategoria) { 
		ComboDTO response = tiposProductosService.searchTiposProductosByIdCategoria(request, idCategoria);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/listadoTipoProductoByIdCategoriaMultiple")
	ResponseEntity<ComboDTO> listadoTipoProductoByIdCategoriaMultiple(HttpServletRequest request, @RequestParam String idCategoria) { 
		ComboDTO response = tiposProductosService.searchTiposProductosByIdCategoriaMultiple(request, idCategoria);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
}