package org.itcgae.siga.fac.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaCompraProductosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.ICompraProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompraProductosController {
	@Autowired
	private ICompraProductosService compraProductosService;
	
	@GetMapping(value = "/pys/comboEstadosFactura")
	ResponseEntity<ComboDTO> comboEstadoFactura(HttpServletRequest request) { 
		ComboDTO response = compraProductosService.comboEstadoFactura(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
//	@GetMapping(value = "/pys/getListaCompraProductos")
//	ResponseEntity<ListaCompraProductosDTO> getListaCompraProductos(HttpServletRequest request, String idPersona, Date fechaSolicitudDesde, Date fechaSolicitudHasta,
//	String nSolicitud, String idCategoria, String idTipoProducto, String descProd, String idEstadoSolicitud, String idEstadoFactura) { 
	@PostMapping(value = "/pys/getListaCompraProductos")
	ResponseEntity<ListaCompraProductosDTO> getListaCompraProductos(HttpServletRequest request,@RequestBody FiltrosCompraProductosItem peticion){
//		FiltrosCompraProductosItem peticion = new FiltrosCompraProductosItem();
//		peticion.setIdpersona(idPersona);
//		peticion.setFechaSolicitudDesde(fechaSolicitudDesde);
//		peticion.setFechaSolicitudHasta(fechaSolicitudHasta);
//		peticion.setnSolicitud(nSolicitud);
//		peticion.setIdCategoria(idCategoria);
//		peticion.setIdTipoProducto(idTipoProducto);
//		peticion.setDescProd(descProd);
//		peticion.setIdEstadoSolicitud(idEstadoSolicitud);
//		peticion.setIdEstadoFactura(idEstadoFactura);
		ListaCompraProductosDTO listaCompras = compraProductosService.getListaCompraProductos(request, peticion);
		if(listaCompras.getError().getCode()==200)return new ResponseEntity<ListaCompraProductosDTO>(listaCompras, HttpStatus.OK);
		else return new ResponseEntity<ListaCompraProductosDTO>(listaCompras, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
