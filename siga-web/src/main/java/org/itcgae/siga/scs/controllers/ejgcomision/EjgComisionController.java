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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ejgComision")
public class EjgComisionController {

	private Logger LOGGER = Logger.getLogger(EjgComisionController.class);

	
	@Autowired
	private IBusquedaEJGComision busquedaEJGComision;

	@RequestMapping(value = "/filtros-ejg/comboFundamentoCalif", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(@RequestParam String idInstitucion,HttpServletRequest request, String[] list_dictamen) {
		ComboDTO response = busquedaEJGComision.comboFundamentoCalificacion(idInstitucion,request, list_dictamen);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/colegioComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelColegiosCol(@RequestParam String idInstitucion, HttpServletRequest request) {
		LOGGER.info(
				"getLabelColegiosCol() / parametro idInstitucion" + idInstitucion);
		ComboDTO response = busquedaEJGComision.getLabelColegiosCol(request, idInstitucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPonente", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPonenteComision(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboPonente(idInstitucion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoJurid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoJuridComision(@RequestParam String idInstitucion, HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboFundamentoJurid(idInstitucion,request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJGComision.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaFundamentosCalificacion/comboDictamenComision", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDictamen(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboDictamen(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/comboTipoEJGColegioComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoColegioEjg(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboTipoColegioEjg(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboEstadoEJGComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEJG(@RequestParam String idInstitucion, HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboEstadoEJG(idInstitucion,request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboResolucionComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboResolucion(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboResolucion(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboJuzgados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgados(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboJuzgados(idInstitucion,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTurnosTipo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosTipo(@RequestParam String idInstitucion, HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboTurnosTipo(idInstitucion,request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardias")
	public ResponseEntity<ComboDTO> comboGuardias(@RequestParam String idInstitucion, HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboGuardias(idInstitucion,request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
