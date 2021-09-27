package org.itcgae.siga.fac.controllers;

import org.itcgae.siga.fac.services.IFacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/facturacion")
public class FacturacionController {
	
	@Autowired 
	private IFacturacionService facturacionService;
	
//	@GetMapping(value = "/pys/listadoTipoServicio")
//	ResponseEntity<ListadoTipoServicioDTO> listadoTipoServicio(HttpServletRequest request) { 
//		ListadoTipoServicioDTO response = tiposServiciosService.searchTiposServicios(request);
//		return new ResponseEntity<ListadoTipoServicioDTO>(response, HttpStatus.OK);
//	}
}
