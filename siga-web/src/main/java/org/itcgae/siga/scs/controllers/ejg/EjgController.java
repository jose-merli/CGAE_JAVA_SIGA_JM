package org.itcgae.siga.scs.controllers.ejg;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.DocumentacionEjgDTO;
import org.itcgae.siga.DTO.scs.DocumentacionEjgItem;
import org.itcgae.siga.DTO.scs.EjgDTO;
import org.itcgae.siga.DTO.scs.EjgItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ejg")
public class EjgController {
	@Autowired
	private IBusquedaEJG busquedaEJG;
	
	@RequestMapping(value = "/filtros-ejg/comboTipoEJG",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoEJG(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboTipoEJG(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/comboTipoEJGColegio",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoColegioEjg(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboTipoColegioEjg(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/comboFundamentoCalif",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboFundamentoCalificacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboResolucion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboResolucion(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboResolucion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboFundamentoImpug",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoImpug(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboFundamentoImpug(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboPreceptivo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPreceptivo(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboPreceptivo(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboRenuncia",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboRenuncia(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboRenuncia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboCreadoDesde",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboCreadoDesde(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboCreadoDesde(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboFundamentoJurid",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> ComboFundamentoJurid(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJG.ComboFundamentoJurid(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboEstadoEJG",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEJG(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJG.comboEstadoEJG(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboImpugnacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboImpugnacion(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboImpugnacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboJuzgados",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgados(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboJuzgados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	//busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJG.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}
		
}
