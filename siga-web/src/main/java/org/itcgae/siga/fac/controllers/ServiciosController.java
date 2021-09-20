package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.fac.services.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping(value="/pys/reactivarBorradoFisicoLogicoServicios")
	ResponseEntity<DeleteResponseDTO> reactivarBorradoFisicoLogicoServicios(@RequestBody ListaServiciosDTO listadoServicios, HttpServletRequest request){
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
}