package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
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
	
	
	//Obtiene la informacion de los productos al darle a buscar en Facturacion --> Productos para rellenar la tabla. 
	@PostMapping(value = "/pys/buscarProductos")
	ResponseEntity<ListaProductosDTO> listadoProductos(HttpServletRequest request, @RequestBody FiltroProductoItem filtroProductoItem) { 
		ListaProductosDTO response = productosService.searchListadoProductos(request, filtroProductoItem);
		return new ResponseEntity<ListaProductosDTO>(response, HttpStatus.OK);
	}
	
	//Obtiene la informacion detallada del producto para su uso al entrar por editar desde la pantalla busqueda de productos por ejemplo
	@GetMapping(value = "/pys/detalleProducto")
	ResponseEntity<ProductoDetalleDTO> detalleProducto(HttpServletRequest request, @RequestParam int idTipoProducto, @RequestParam int idProducto, @RequestParam int idProductoInstitucion) { 
		ProductoDetalleDTO response = productosService.detalleProducto(request, idTipoProducto, idProducto, idProductoInstitucion);
		return new ResponseEntity<ProductoDetalleDTO>(response, HttpStatus.OK);
	}
	
	//Inserta en base de datos un nuevo producto
	@PostMapping(value="/pys/nuevoProducto")
	ResponseEntity<InsertResponseDTO> nuevoProducto(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request){
		InsertResponseDTO response = new InsertResponseDTO();
		try {
			 response = productosService.nuevoProducto(producto, request);
			
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e){
			response.error(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Edita un producto existente en base de datos
	@PostMapping(value="/pys/editarProducto")
	ResponseEntity<DeleteResponseDTO> editarProducto(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request){
		DeleteResponseDTO response = productosService.editarProducto(producto, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	//Reactiva o elimina fisicamente (si no tiene ninguna solicitud de compra o compra existente) o logicamente (en caso de tener solicitud de compra o compra existente, le establece la fechabaja a hoy) un producto
	@PostMapping(value="/pys/reactivarBorradoFisicoLogicoProductos")
	ResponseEntity<DeleteResponseDTO> reactivarBorradoFisicoLogicoProductos(@RequestBody ListaProductosDTO listadoProductos, HttpServletRequest request){	
		DeleteResponseDTO response = new DeleteResponseDTO();
		try {
			 response = productosService.reactivarBorradoFisicoLogicoProductos(listadoProductos, request);
			
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e){
			response.error(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Obtiene el combo de tipos de iva dados de alta (fechabaja is null)
	@GetMapping(value = "/combo/tipoIva")
	ResponseEntity<ComboDTO> comboTiposIva(HttpServletRequest request) { 
		ComboDTO response = productosService.comboIva(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	//Obtiene todos los tipos de iva para el combo esten o no dados de alta (indiferentemente de la fechabaja)
	@GetMapping(value = "/combo/tipoIvaNoDerogados")
	ResponseEntity<ComboDTO> comboTiposIvaNoDerogados(HttpServletRequest request) { 
		ComboDTO response = productosService.comboIvaNoDerogados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	//Inserta o edita en base de datos las formas de pago asociadas a X producto
	@PostMapping(value="/pys/formasPagoProducto")
	ResponseEntity<InsertResponseDTO> crearEditarFormaPago(@RequestBody ProductoDetalleDTO producto, HttpServletRequest request){
		InsertResponseDTO response = new InsertResponseDTO();
		try {
			 response = productosService.crearEditarFormaPago(producto, request);
			
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e){
			response.error(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
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
	
	//Obtiene los codigos de productos existentes en un colegio para su uso por ejemplo en validar que en ficha producto a la hora de crear/editar no se introduzca un codigo ya existente (para saber como esta formado el codigo revisar la documentacion)
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
