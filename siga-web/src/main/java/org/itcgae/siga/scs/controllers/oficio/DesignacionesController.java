package org.itcgae.siga.scs.controllers.oficio;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/designas")
public class DesignacionesController {
	
	@Autowired
	private ComboService comboService;
	
	@RequestMapping(value = "/comboTipoDesgina",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDesignacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	private IDesignacionesService designacionesService;
	
	@RequestMapping(value = "/busquedaJustificacionExpres",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JustificacionExpressItem> busquedaTurnos(@RequestBody JustificacionExpressItem item, HttpServletRequest request) {
		JustificacionExpressItem response = designacionesService.busquedaJustificacionExpres(item, request);
		return new ResponseEntity<JustificacionExpressItem>(response, HttpStatus.OK);
	}
}
 
