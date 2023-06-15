package org.itcgae.siga.fac.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionItem;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
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
	ResponseEntity<FichaCompraSuscripcionItem> getFichaCompraSuscripcionColegiado(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem peticion) { 
		FichaCompraSuscripcionItem fichaCompleta = gestionFichaCompraSuscripcionService.getFichaCompraSuscripcion(request, peticion);
		if(fichaCompleta != null)return new ResponseEntity<FichaCompraSuscripcionItem>(fichaCompleta, HttpStatus.OK);
		else return new ResponseEntity<FichaCompraSuscripcionItem>(fichaCompleta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/solicitarCompra")
	ResponseEntity<InsertResponseDTO>  SolicitarCompra(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem ficha) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.solicitarCompra(request, ficha);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/pys/solicitarSuscripcion")
	ResponseEntity<InsertResponseDTO>  SolicitarSuscripcion(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem ficha) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.solicitarSuscripcion(request, ficha);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/aprobarCompra")
	ResponseEntity<UpdateResponseDTO> aprobarCompra(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem ficha) throws Exception {
		UpdateResponseDTO response = gestionFichaCompraSuscripcionService.aprobarCompra(request, ficha);
		if(response.getStatus()=="200") return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/aprobarSuscripcion")
	ResponseEntity<UpdateResponseDTO> aprobarSuscricion(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem ficha) throws Exception {
		UpdateResponseDTO response = gestionFichaCompraSuscripcionService.aprobarSuscripcion(request, ficha);
		if(response.getStatus()=="200") return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/denegarPeticion")
	ResponseEntity<InsertResponseDTO>  denegarPeticion(HttpServletRequest request, @RequestBody String nSolicitud) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.denegarPeticion(request, nSolicitud);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/aprobarCompraMultiple")
	ResponseEntity<InsertResponseDTO> aprobarCompraMultiple(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem[] peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.aprobarCompraMultiple(request, peticiones);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/aprobarSuscripcionMultiple")
	ResponseEntity<InsertResponseDTO> aprobarSuscripcionMultiple(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem[] peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.aprobarSuscripcionMultiple(request, peticiones);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/denegarPeticionMultiple")
	ResponseEntity<InsertResponseDTO> denegarPeticionMultiple(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem[] peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.denegarPeticionMultiple(request, peticiones);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pys/getListaProductosCompra")
	ResponseEntity<ListaProductosCompraDTO> getListaProductosCompra(HttpServletRequest request, String idPeticion) throws Exception {
		ListaProductosCompraDTO response = gestionFichaCompraSuscripcionService.getListaProductosCompra(request, idPeticion);
		if(response.getError().getCode()==200) return new ResponseEntity<ListaProductosCompraDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<ListaProductosCompraDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pys/getPermisoModificarImporteProducto")
	ResponseEntity<String> getPermisoModificarImporteProducto(HttpServletRequest request) throws Exception {
		String response = gestionFichaCompraSuscripcionService.getPermisoModificarImporteProducto(request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pys/updateProductosPeticion")
	ResponseEntity<InsertResponseDTO> updateProductosPeticion(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.updateProductosPeticion(request, peticiones);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/updateServiciosPeticion")
	ResponseEntity<InsertResponseDTO> updateServiciosPeticion(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.updateServiciosPeticion(request, peticiones);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pys/getFacturasPeticion")
	ResponseEntity<ListaFacturasPeticionDTO> getFacturasPeticion(HttpServletRequest request, String idPeticion) throws Exception {
		ListaFacturasPeticionDTO response = gestionFichaCompraSuscripcionService.getFacturasPeticion(request, idPeticion);
		if(response.getError() == null) return new ResponseEntity<ListaFacturasPeticionDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<ListaFacturasPeticionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pys/getDescuentosPeticion")
	ResponseEntity<ListaDescuentosPeticionDTO> getDescuentosPeticion(HttpServletRequest request, String idPeticion) throws Exception {
		ListaDescuentosPeticionDTO response = gestionFichaCompraSuscripcionService.getDescuentosPeticion(request, idPeticion);
		if(response.getError() == null) return new ResponseEntity<ListaDescuentosPeticionDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<ListaDescuentosPeticionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/saveAnticipoPeticion")
	ResponseEntity<InsertResponseDTO> saveAnticipoPeticion(HttpServletRequest request, @RequestBody ListaDescuentosPeticionItem anticipoPeticion) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.saveAnticipoPeticion(request, anticipoPeticion);
		if(response.getStatus()=="200") return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/deleteAnticipoPeticion")
	ResponseEntity<DeleteResponseDTO> deleteAnticipoPeticion(HttpServletRequest request, @RequestBody List<ListaDescuentosPeticionItem> anticiposListaPeticion) throws Exception {
		DeleteResponseDTO response = gestionFichaCompraSuscripcionService.deleteAnticipoPeticion(request, anticiposListaPeticion);
		if(response.getStatus()=="200") return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/anularPeticion")
	ResponseEntity<UpdateResponseDTO> anularPeticion(HttpServletRequest request, @RequestBody String nSolicitud) throws Exception {
		UpdateResponseDTO response = gestionFichaCompraSuscripcionService.anularPeticion(request, nSolicitud);
		if(response.getStatus()=="200") {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/pys/anularSuscripcionMultiple")
	ResponseEntity<InsertResponseDTO> anularSuscripcionMultiple(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem[] peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.anularPeticionMultiple(request, peticiones);
		if(response.getStatus()=="200") {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/pys/anularCompraMultiple")
	ResponseEntity<InsertResponseDTO> anularCompraMultiple(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem[] peticiones) throws Exception {
		InsertResponseDTO response = gestionFichaCompraSuscripcionService.anularPeticionMultiple(request, peticiones);
		if(response.getStatus()=="200"){
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/pys/getListaServiciosSuscripcion")
	ResponseEntity<ListaServiciosSuscripcionDTO> getListaServiciosSuscripcion(HttpServletRequest request, String idPeticion, Date aFechaDe) throws Exception {
		ListaServiciosSuscripcionDTO response = gestionFichaCompraSuscripcionService.getListaServiciosSuscripcion(request, idPeticion, aFechaDe);
		if(response.getError().getCode()==200){
			return new ResponseEntity<ListaServiciosSuscripcionDTO>(response, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<ListaServiciosSuscripcionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/pys/anadirAnticipoCompra")
	ResponseEntity<UpdateResponseDTO> anadirAnticipoCompra(HttpServletRequest request, @RequestBody ListaDescuentosPeticionItem anticipo) throws Exception {
		UpdateResponseDTO response = gestionFichaCompraSuscripcionService.anadirAnticipoCompra(request, anticipo);
		if(response.getStatus()=="200"){
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
