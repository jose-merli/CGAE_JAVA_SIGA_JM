package org.itcgae.siga.scs.controllers.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacturacionController {

	@Autowired
	private IFacturacionSJCSServices facturacionServices;

	@RequestMapping(value="/facturacionsjcs/buscarfacturaciones", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDTO> buscarFacturaciones(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request){
		FacturacionDTO response = facturacionServices.buscarFacturaciones(facturacionItem, request);
		return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/eliminarFacturacion", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDeleteDTO> eliminarFacturaciones(@RequestBody int idFactura, HttpServletRequest request){
		FacturacionDeleteDTO response = facturacionServices.eliminarFacturaciones(idFactura, request);
		if (response.getStatus() == SigaConstants.OK) {
			return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/facturacionsjcs/datosfacturacion", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDTO> datosfacturacion(@RequestParam("idFacturacion") String idFacturacion, HttpServletRequest request){
		FacturacionDTO response = facturacionServices.datosFacturacion(idFacturacion, request);
		return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/historicofacturacion", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDTO> historicoFacturacion(@RequestParam("idFacturacion") String idFacturacion, HttpServletRequest request){
		FacturacionDTO response = facturacionServices.historicoFacturacion(idFacturacion, request);
		return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/numApuntes", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> numApuntes(@RequestParam("idFacturacion") String idFacturacion, HttpServletRequest request){
		StringDTO response = facturacionServices.numApuntes(idFacturacion, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/facturacionsjcs/saveFacturacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveFacturacion(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request) { 
		InsertResponseDTO response = facturacionServices.saveFacturacion(facturacionItem, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/facturacionsjcs/updateFacturacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateFacturacion(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = facturacionServices.updateFacturacion(facturacionItem, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/facturacionsjcs/ejecutarfacturacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> ejecutarFacturacion(@RequestBody String idFacturacion, HttpServletRequest request) { 
		InsertResponseDTO response = facturacionServices.ejecutarFacturacion(idFacturacion, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/facturacionsjcs/reabrirfacturacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> reabrirFacturacion(@RequestBody String idFacturacion, HttpServletRequest request) { 
		InsertResponseDTO response = facturacionServices.reabrirFacturacion(idFacturacion, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/facturacionsjcs/simularfacturacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> simularFacturacion(@RequestBody String idFacturacion, HttpServletRequest request) { 
		InsertResponseDTO response = facturacionServices.simularFacturacion(idFacturacion, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value="/facturacionsjcs/tarjetaConceptosfac", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDTO> conceptosFacturacion(@RequestParam("idFacturacion") String idFacturacion, HttpServletRequest request){
		FacturacionDTO response = facturacionServices.conceptosFacturacion(idFacturacion, request);
		return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/facturacionsjcs/saveConceptosFac",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveConceptosFac(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request) { 
		InsertResponseDTO response = facturacionServices.saveConceptosFac(facturacionItem, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/facturacionsjcs/updateConceptosFac",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateConceptosFac(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request) { 
		UpdateResponseDTO response = facturacionServices.updateConceptosFac(facturacionItem, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/facturacionsjcs/deleteConceptosFac",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteConceptosFac(@RequestBody List<FacturacionItem> facturacionDto, HttpServletRequest request) { 
		DeleteResponseDTO response = facturacionServices.deleteConceptosFac(facturacionDto, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/facturacionsjcs/datospagos", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PagosjgDTO> datosPagos(@RequestParam("idFacturacion") String idFacturacion, HttpServletRequest request){
		PagosjgDTO response = facturacionServices.datosPagos(idFacturacion, request);
		return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/buscarPagos", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PagosjgDTO> buscarPagos(@RequestBody PagosjgItem pagosItem, HttpServletRequest request){
		PagosjgDTO response = facturacionServices.buscarPagos(pagosItem, request);
		return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
	}
}
