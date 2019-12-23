package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosDTO;
import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosItem;
import org.itcgae.siga.scs.services.facturacionsjcs.ICartasFacturacionPagoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartasFacturacionPagoController {

	@Autowired
	private ICartasFacturacionPagoServices cartasFacturacionPagoServices;

	@RequestMapping(value="/facturacionsjcs/buscarCartasfacturacion", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CartasFacturacionPagosDTO> buscarCartasfacturacion(@RequestBody CartasFacturacionPagosItem cartasFacturacionPagosItem, HttpServletRequest request){
		CartasFacturacionPagosDTO response = cartasFacturacionPagoServices.buscarCartasfacturacion(cartasFacturacionPagosItem, request);
		return new ResponseEntity<CartasFacturacionPagosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/buscarCartaspago", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CartasFacturacionPagosDTO> buscarCartaspago(@RequestBody CartasFacturacionPagosItem cartasFacturacionPagosItem, HttpServletRequest request){
		CartasFacturacionPagosDTO response = cartasFacturacionPagoServices.buscarCartaspago(cartasFacturacionPagosItem, request);
		return new ResponseEntity<CartasFacturacionPagosDTO>(response, HttpStatus.OK);
	}

}
