package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductosController {
	@Autowired 
	private IProductosService productosService;
	
	@GetMapping(value = "/combo/tipoIva")
	ResponseEntity<ComboDTO> comboTiposIva(HttpServletRequest request) { 
		ComboDTO response = productosService.comboIva(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/tipoFormaPago")
	ResponseEntity<ComboDTO> comboTipoFormaPago(HttpServletRequest request) { 
		ComboDTO response = productosService.comboTipoFormaPago(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
