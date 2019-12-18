package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionServices;
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
	private IFacturacionServices facturacionServices;

	@RequestMapping(value="/facturacionsjcs/buscarfacturaciones", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDTO> buscarFacturaciones(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request){
		FacturacionDTO response = facturacionServices.buscarFacturaciones(facturacionItem, request);
		return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/eliminarFacturacion", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDeleteDTO> eliminarFacturaciones(@RequestBody int idFactura, HttpServletRequest request){
		FacturacionDeleteDTO response = facturacionServices.eliminarFacturaciones(idFactura, request);
		if (response.getError().getCode() == 200) {
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
}
