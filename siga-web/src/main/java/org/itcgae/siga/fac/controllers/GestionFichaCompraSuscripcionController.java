package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.IGestionFichaCompraSuscripcionService;
import org.itcgae.siga.fac.services.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestionFichaCompraSuscripcionController {
	@Autowired 
	private IGestionFichaCompraSuscripcionService gestionFichaCompraSuscripcionService;

	@PostMapping(value = "/pys/getFichaCompraSuscripcion")
	ResponseEntity<FichaCompraSuscripcionItem> getFichaCompraSuscripcion(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem peticion) { 
		FichaCompraSuscripcionItem fichaCompleta = gestionFichaCompraSuscripcionService.getFichaCompraSuscripcion(request, peticion);
		if(fichaCompleta != null)return new ResponseEntity<FichaCompraSuscripcionItem>(fichaCompleta, HttpStatus.OK);
		else return new ResponseEntity<FichaCompraSuscripcionItem>(fichaCompleta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/pys/solicitarCompra")
	ResponseEntity<InsertResponseDTO>  SolicitarCompra(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem ficha) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.solicitarCompra(request, ficha);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping(value = "/pys/solicitarSuscripcion")
	ResponseEntity<InsertResponseDTO>  SolicitarSuscripcion(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem ficha) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.solicitarSuscripcion(request, ficha);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}