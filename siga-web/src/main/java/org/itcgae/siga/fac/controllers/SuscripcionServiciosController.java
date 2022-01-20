package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltrosSuscripcionesItem;
import org.itcgae.siga.DTO.fac.ListaSuscripcionesDTO;
import org.itcgae.siga.DTO.fac.RevisionAutLetradoItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.fac.services.ISuscripcionServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuscripcionServiciosController {
	
	@Autowired 
	private ISuscripcionServiciosService suscripcionServiciosService;

//	@GetMapping(value = "/pys/getListaSuscripciones")
//	ResponseEntity<ListaSuscripcionesDTO> getListaSuscripciones(HttpServletRequest request, String idPersona, Date fechaSolicitudDesde, Date fechaSolicitudHasta,
//	String nSolicitud, String idCategoria, String idTipoProducto, String descProd, String idEstadoSolicitud, String idEstadoFactura) { 
	@PostMapping(value = "/pys/getListaSuscripciones")
	ResponseEntity<ListaSuscripcionesDTO> getListaSuscripciones(HttpServletRequest request, @RequestBody FiltrosSuscripcionesItem peticion){
//		FiltrosSuscripcionesItem peticion = new FiltrosSuscripcionesItem();
//		peticion.setIdpersona(idPersona);
//		peticion.setFechaSolicitudDesde(fechaSolicitudDesde);
//		peticion.setFechaSolicitudHasta(fechaSolicitudHasta);
//		peticion.setnSolicitud(nSolicitud);
//		peticion.setIdCategoria(idCategoria);
//		peticion.setIdTipoProducto(idTipoProducto);
//		peticion.setDescProd(descProd);
//		peticion.setIdEstadoSolicitud(idEstadoSolicitud);
//		peticion.setIdEstadoFactura(idEstadoFactura);
		ListaSuscripcionesDTO listaCompras = suscripcionServiciosService.getListaSuscripciones(request, peticion);
		if(listaCompras.getError().getCode()==200)return new ResponseEntity<ListaSuscripcionesDTO>(listaCompras, HttpStatus.OK);
		else return new ResponseEntity<ListaSuscripcionesDTO>(listaCompras, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/actualizacionColaSuscripcionesPersona")
	ResponseEntity<InsertResponseDTO> actualizacionSuscripcionesPersona(HttpServletRequest request, @RequestBody RevisionAutLetradoItem peticion) throws Exception{
		InsertResponseDTO response = suscripcionServiciosService.actualizacionColaSuscripcionesPersona(request, peticion);
		if(response.getStatus().equals("200")) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
