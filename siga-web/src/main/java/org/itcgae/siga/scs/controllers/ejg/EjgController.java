package org.itcgae.siga.scs.controllers.ejg;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ejg")
public class EjgController {
	@Autowired
	private IBusquedaEJG busquedaEJG;
	
	
	@GetMapping(value = "/filtros-ejg/comboTipoEJG" ,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboCosteFijos(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboTipoEJG(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
