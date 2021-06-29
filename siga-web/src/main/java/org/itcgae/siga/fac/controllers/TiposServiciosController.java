package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ServicioDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.ITiposServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiposServiciosController {
	
	@Autowired 
	private ITiposServiciosService tiposServiciosService;
	
	@GetMapping(value = "/pys/listadoTipoServicio")
	ResponseEntity<ListadoTipoServicioDTO> listadoTipoServicio(HttpServletRequest request) { 
		ListadoTipoServicioDTO response = tiposServiciosService.searchTiposServicios(request);
		return new ResponseEntity<ListadoTipoServicioDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/listadoTipoServicioHistorico")
	ResponseEntity<ListadoTipoServicioDTO> listadoTipoServicioHistorico(HttpServletRequest request) { 
		ListadoTipoServicioDTO response = tiposServiciosService.searchTiposServiciosHistorico(request);
		return new ResponseEntity<ListadoTipoServicioDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoServicios")
	ResponseEntity<ComboDTO> comboTiposServicios(HttpServletRequest request) { 
		ComboDTO response = tiposServiciosService.comboTiposServicios(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/activarDesactivarServicio")
	ResponseEntity<ServicioDTO> activarDesactivarServicio(@RequestBody ListadoTipoServicioDTO listadoServicios, HttpServletRequest request){
		ServicioDTO response = tiposServiciosService.activarDesactivarServicio(listadoServicios, request);
		return new ResponseEntity<ServicioDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/crearServicio")
	ResponseEntity<InsertResponseDTO> crearServicio(@RequestBody ListadoTipoServicioDTO listadoServicios, HttpServletRequest request){
		InsertResponseDTO response = tiposServiciosService.crearServicio(listadoServicios, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/pys/modificarServicio")
	ResponseEntity<DeleteResponseDTO> modificarServicio(@RequestBody ListadoTipoServicioDTO listadoServicios, HttpServletRequest request){
		DeleteResponseDTO response = tiposServiciosService.modificarServicio(listadoServicios, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
}
