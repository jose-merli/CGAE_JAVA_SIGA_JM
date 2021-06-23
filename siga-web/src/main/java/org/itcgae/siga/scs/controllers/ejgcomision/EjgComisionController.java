package org.itcgae.siga.scs.controllers.ejgcomision;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;

import org.itcgae.siga.DTOs.scs.EjgItem;

import org.itcgae.siga.scs.services.ejg.IBusquedaEJGComision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ejgComision")
public class EjgComisionController {

	private Logger LOGGER = Logger.getLogger(EjgComisionController.class);

	
	@Autowired
	private IBusquedaEJGComision busquedaEJGComision;

	@RequestMapping(value = "/filtros-ejg/comboFundamentoCalifComision/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(@PathVariable String idInstitucion,HttpServletRequest request, String[] list_dictamen) {
		ComboDTO response = busquedaEJGComision.comboFundamentoCalificacion(idInstitucion,request, list_dictamen);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/colegioComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelColegiosCol(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.getLabelColegiosCol(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPonenteComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPonenteComision(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboPonente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoJuridComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoJuridComision(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboFundamentoJurid(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@PathVariable EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJGComision.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaFundamentosCalificacion/comboDictamenComision/{idInstitucion}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDictamen(@PathVariable String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboDictamen(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/comboTipoEJGColegioComision/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoColegioEjg(@PathVariable String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboTipoColegioEjg(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboEstadoEJGComision/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEJG(@PathVariable String idInstitucion, HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboEstadoEJG(idInstitucion,request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/comboResolucion/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboResolucion(@PathVariable String idInstitucion ,HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboResolucion(idInstitucion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/comboAnioActaComision/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboAnioActa(@PathVariable String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboAnioActa(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboJuzgadosComision/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgados(@PathVariable String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboJuzgados(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTurnosTipo/{idInstitucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosTipo(@PathVariable String idInstitucion, HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboTurnosTipo(idInstitucion,request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/filtros-ejg/guardias/{idInstitucion}")
	public ResponseEntity<ComboDTO> comboGuardias(@PathVariable String idInstitucion, HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboGuardias(idInstitucion,request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
