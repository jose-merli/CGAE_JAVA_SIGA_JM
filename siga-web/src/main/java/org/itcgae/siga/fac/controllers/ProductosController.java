package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.PysTipoiva;
import org.itcgae.siga.fac.services.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	ResponseEntity<ProductoDetalleDTO> detalleProducto(HttpServletRequest request, @RequestParam int idTipoProducto, @RequestParam int idProducto, @RequestParam int idProductoInstitucion) { 
		ProductoDetalleDTO response = productosService.detalleProducto(request, idTipoProducto, idProducto, idProductoInstitucion);
		return new ResponseEntity<ProductoDetalleDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/nuevoProducto")
	ResponseEntity<InsertResponseDTO> nuevoProducto(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request) throws Exception{
		InsertResponseDTO response = productosService.nuevoProducto(producto, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/editarProducto")
	ResponseEntity<DeleteResponseDTO> editarProducto(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request){
		DeleteResponseDTO response = productosService.editarProducto(producto, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/reactivarBorradoFisicoLogicoProductos")
	ResponseEntity<DeleteResponseDTO> reactivarBorradoFisicoLogicoProductos(@RequestBody ListaProductosDTO listadoProductos, HttpServletRequest request){
		DeleteResponseDTO response = productosService.reactivarBorradoFisicoLogicoProductos(listadoProductos, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoIva")
	ResponseEntity<ComboDTO> comboTiposIva(HttpServletRequest request) { 
		ComboDTO response = productosService.comboIva(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoIvaNoDerogados")
	ResponseEntity<ComboDTO> comboTiposIvaNoDerogados(HttpServletRequest request) { 
		ComboDTO response = productosService.comboIvaNoDerogados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/formasPagoProducto")
	ResponseEntity<InsertResponseDTO> crearEditarFormaPago(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request) throws Exception{
		InsertResponseDTO response = productosService.crearEditarFormaPago(producto, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoFormaPago")
	ResponseEntity<ComboDTO> comboTipoFormaPago(HttpServletRequest request) { 
		ComboDTO response = productosService.comboTipoFormaPago(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/pagoInternet")
	ResponseEntity<ComboDTO> comboTipoFormaPagoInternet(HttpServletRequest request) { 
		ComboDTO response = productosService.comboTipoFormaPagoInternet(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/comboPagoSecretaria")
	ResponseEntity<ComboDTO> comboTipoFormaPagoSecretaria(HttpServletRequest request) { 
		ComboDTO response = productosService.comboTipoFormaPagoSecretaria(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/obtenerCodigosPorColegio")
	ResponseEntity<ListaCodigosPorColegioDTO> obtenerCodigosPorColegio(HttpServletRequest request) { 
		ListaCodigosPorColegioDTO response = productosService.obtenerCodigosPorColegio(request);
		return new ResponseEntity<ListaCodigosPorColegioDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/pys/getIvaDetail")
	ResponseEntity<PysTipoiva> getIvaDetail(HttpServletRequest request, String idTipoIva) { 
		PysTipoiva response = productosService.getIvaDetail(request, idTipoIva);
		return new ResponseEntity<PysTipoiva>(response, HttpStatus.OK);
	}
}
