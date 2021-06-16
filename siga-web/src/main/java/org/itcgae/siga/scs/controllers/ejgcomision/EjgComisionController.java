package org.itcgae.siga.scs.controllers.ejgcomision;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;

import org.itcgae.siga.DTOs.scs.EjgItem;

import org.itcgae.siga.scs.services.componentesGenerales.ComboServiceComision;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJGComision;

import org.itcgae.siga.scs.services.maestros.IBusquedaFundamentosCalificacionServiceComision;
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
@RequestMapping(value = "/ejgComision")
public class EjgComisionController {

	@Autowired
	private IBusquedaEJGComision busquedaEJGComision;

	@Autowired
	private ComboServiceComision comboServiceComision;

	@Autowired
	private IBusquedaFundamentosCalificacionServiceComision busquedaFundamentosCalificacionServiceComision;

	@RequestMapping(value = "/filtros-ejg/comboFundamentoCalif", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen) {
		ComboDTO response = busquedaEJGComision.comboFundamentoCalificacion(request, list_dictamen);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/colegio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelColegiosCol(String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.getLabelColegiosCol(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPonente", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPonenteComision(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboPonente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoJurid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoJuridComision(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboFundamentoJurid(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJGComision.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaFundamentosCalificacion/comboDictamen", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDictamen(HttpServletRequest request) {
		ComboDTO response = busquedaFundamentosCalificacionServiceComision.comboDictamen(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTipoEJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoEJG(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboTipoEJG(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboEstadoEJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEJG(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboEstadoEJG(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboResolucion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboResolucion(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboResolucion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboJuzgados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgados(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboJuzgados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTurnosTipo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosTipo(HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboTurnosTipo(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardias")
	public ResponseEntity<ComboDTO> comboGuardias(HttpServletRequest request, String idTurno) {
		ComboDTO response = comboServiceComision.comboGuardias(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
