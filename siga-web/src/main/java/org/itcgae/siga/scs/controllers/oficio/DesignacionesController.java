package org.itcgae.siga.scs.controllers.oficio;

import java.util.ArrayList;
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
	ResponseEntity<List<JustificacionExpressItem>> busquedaJustificacionExpres(@RequestBody JustificacionExpressItem item, HttpServletRequest request) {
		List<JustificacionExpressItem> response = designacionesService.busquedaJustificacionExpres(item, request);
		return new ResponseEntity<List<JustificacionExpressItem>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaDesignaciones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaDesignas(item, request);
		if(response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/comboModulo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request) {

		ComboDTO response = designacionesService.modulo(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/comboProcedimientos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientos(HttpServletRequest request) {

		ComboDTO response = designacionesService.comboProcedimientos(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
 
