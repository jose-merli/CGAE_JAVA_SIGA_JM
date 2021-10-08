package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/facturacionPyS")
public class FacturacionPySController {
	
	@Autowired 
	private IFacturacionPySService facturacionService;
	
	@GetMapping(value = "/getCuentasBancarias")
	ResponseEntity<CuentasBancariasDTO> getCuentasBancarias(HttpServletRequest request) { 
		CuentasBancariasDTO response = facturacionService.getCuentasBancarias(request);
		return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.OK);
	}
	
}
