package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.BorrarSuscripcionBajaItem;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosDTO;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosItem;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.fac.services.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping(value="/pys/reactivarBorradoFisicoLogicoServicios")
	ResponseEntity<DeleteResponseDTO> reactivarBorradoFisicoLogicoServicios(@RequestBody ListaServiciosDTO listadoServicios, HttpServletRequest request) throws Exception{
		DeleteResponseDTO response = serviciosService.reactivarBorradoFisicoLogicoServicios(listadoServicios, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/obtenerCodigosPorColegioServicios")
	ResponseEntity<ListaCodigosPorColegioDTO> obtenerCodigosPorColegioServicios(HttpServletRequest request) { 
		ListaCodigosPorColegioDTO response = serviciosService.obtenerCodigosPorColegioServicios(request);
		return new ResponseEntity<ListaCodigosPorColegioDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/nuevoServicio")
	ResponseEntity<InsertResponseDTO> nuevoServicio(@RequestBody ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception{
		InsertResponseDTO response = serviciosService.nuevoServicio(servicio, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/editarServicio")
	ResponseEntity<DeleteResponseDTO> editarServicio(@RequestBody ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception{
		DeleteResponseDTO response = serviciosService.editarServicio(servicio, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/detalleServicio")
	ResponseEntity<ServicioDetalleDTO> detalleServicio(HttpServletRequest request, @RequestParam int idTipoServicio, @RequestParam int idServicio, @RequestParam int idServiciosInstitucion) { 
		ServicioDetalleDTO response = serviciosService.detalleServicio(request, idTipoServicio, idServicio, idServiciosInstitucion);
		return new ResponseEntity<ServicioDetalleDTO>(response, HttpStatus.OK);
	}
	
//	@GetMapping(value = "/combo/CondicionSuscripcion")
//	ResponseEntity<ComboDTO> comboCondicionSuscripcion(HttpServletRequest request, @RequestParam int idConsulta) { 
//		ComboDTO response = serviciosService.comboCondicionSuscripcion(request, idConsulta);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}
	
	@GetMapping(value = "/combo/CondicionSuscripcion")
	ResponseEntity<ComboDTO> comboCondicionSuscripcion(HttpServletRequest request) { 
		ComboDTO response = serviciosService.comboCondicionSuscripcion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/formasPagoServicio")
	ResponseEntity<InsertResponseDTO> crearEditarFormaPago(@RequestBody ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception{
		InsertResponseDTO response = serviciosService.crearEditarFormaPago(servicio, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value="/pys/borrarSuscripcionesBajas")
	ResponseEntity<DeleteResponseDTO> borrarSuscripcionesBajas(@RequestBody BorrarSuscripcionBajaItem borrarSuscripcionBajaItem, HttpServletRequest request) throws Exception{
		DeleteResponseDTO response = serviciosService.borrarSuscripcionesBajas(borrarSuscripcionBajaItem, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pys/detalleTarjetaPrecios")
	ResponseEntity<FichaTarjetaPreciosDTO> detalleTarjetaPrecios(HttpServletRequest request, @RequestBody ServicioDetalleDTO servicio) { 
		FichaTarjetaPreciosDTO response = serviciosService.detalleTarjetaPrecios(request, servicio);
		return new ResponseEntity<FichaTarjetaPreciosDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/comboPeriodicidad")
	ResponseEntity<ComboDTO> comboPeriodicidad(HttpServletRequest request) { 
		ComboDTO response = serviciosService.comboPeriodicidad(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/crearEditarPrecios")
	ResponseEntity<InsertResponseDTO> crearEditarPrecios(@RequestBody FichaTarjetaPreciosDTO listaPrecios, HttpServletRequest request){
		InsertResponseDTO response = serviciosService.crearEditarPrecios(listaPrecios, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
}