package org.itcgae.siga.scs.controllers.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
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
@RequestMapping(value = "/oficio")
public class DesignacionesController {
	
	@Autowired
	private ComboService comboService;
	
	@Autowired
	private IDesignacionesService designacionesService;
	
	@RequestMapping(value = "/designas/comboTipoDesigna",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDesignacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/busquedaJustificacionExpres",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaJustificacionExpres(@RequestBody JustificacionExpressItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaJustificacionExpres(item, request);
		return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaDesignaciones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DesignaItem> busquedaDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		DesignaItem response = designacionesService.busquedaDesignas(item, request);
		return new ResponseEntity<DesignaItem>(response, HttpStatus.OK);
	}
}
 
