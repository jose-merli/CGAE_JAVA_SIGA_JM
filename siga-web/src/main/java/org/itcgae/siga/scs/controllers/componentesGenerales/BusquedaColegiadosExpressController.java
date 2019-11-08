package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTO.scs.ColegiadoJGItem;
import org.itcgae.siga.scs.service.componentesGenerales.IBusquedaColegiadosExpressService;
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
public class BusquedaColegiadosExpressController {
	
	@Autowired
	private IBusquedaColegiadosExpressService busquedaColegiadosExpressService;
	@RequestMapping(value = "/componenteGeneralJG/busquedaColegiado",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoJGDTO> busquedaColegiadosExpress(@RequestParam("colegiadoJGItem") String colegiadoJGItem, HttpServletRequest request) {
		ColegiadoJGDTO response = busquedaColegiadosExpressService.busquedaColegiadosExpress(colegiadoJGItem, request);
		return new ResponseEntity<ColegiadoJGDTO>(response, HttpStatus.OK);
	}
}
