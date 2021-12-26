package org.itcgae.siga.scs.controllers.ejgcomision;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.ActualizarAnioActaItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;

import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJGComision;
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

	private Logger LOGGER = Logger.getLogger(EjgComisionController.class);

	@Autowired
	private IBusquedaEJGComision busquedaEJGComision;

	@RequestMapping(value = "/filtros-ejg/comboFundamentoCalifComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen) {
		ComboDTO response = busquedaEJGComision.comboFundamentoCalificacion(request, list_dictamen);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

//	@RequestMapping(value = "/filtros-ejg/colegioComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getLabelColegiosCol(HttpServletRequest request) {
//		ComboDTO response = busquedaEJGComision.getLabelColegiosCol(request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}

	@RequestMapping(value = "/filtros-ejg/comboPonenteComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPonenteComision(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboPonente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPresidente", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPresidente(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboPresidente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboSecretario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboSecretario(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboSecretario(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoJuridComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoJuridComision(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboFundamentoJurid(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJGComision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJGComision.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}
	
	// busqueda
		@RequestMapping(value = "/filtros-ejg/busquedaActas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<ActasDTO> busquedaActas(@RequestBody ActasItem actasItem, HttpServletRequest request) {
			ActasDTO response = busquedaEJGComision.busquedaActas(actasItem, request);
			return new ResponseEntity<ActasDTO>(response, HttpStatus.OK);
		}
	@RequestMapping(value = "/filtros-ejg/busquedaEJGActaComision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJGActaComision(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJGComision.busquedaEJGActaComision(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/editarActaAnio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> editarActaAnio(@RequestBody List<EjgItem> ejgItem,
			HttpServletRequest request) throws SigaExceptions {
		UpdateResponseDTO response = busquedaEJGComision.editarActaAnio(ejgItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/borrarActaAnio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarActaAnio(@RequestBody List<EjgItem> ejgItem,
			HttpServletRequest request) throws SigaExceptions {
		UpdateResponseDTO response = busquedaEJGComision.borrarActaAnio(ejgItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/editarResolucionFundamento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> editarResolucionFundamento(@RequestBody List<EjgItem> ejgItem,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = busquedaEJGComision.editarResolucionFundamento(ejgItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/borrarResolucionFundamento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarResolucionFundamento(@RequestBody List<EjgItem> ejgItem,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = busquedaEJGComision.borrarResolucionFundamento(ejgItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/editarPonente", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> editarPonente(@RequestBody List<EjgItem> ejgItem, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = busquedaEJGComision.editarPonente(ejgItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-ejg/borrarPonente", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarPonente(@RequestBody List<EjgItem> ejgItem, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = busquedaEJGComision.borrarPonente(ejgItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/filtros-ejg/comboDictamenComision", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDictamen(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboDictamen(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/filtros-ejg/obligatoriedadResolucion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obligatoriedadResolucion(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.obligatoriedadResolucion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTipoEJGColegioComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoColegioEjg(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboTipoColegioEjg(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboEstadoEJGComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEJG(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJGComision.comboEstadoEJG(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboResolucionComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboResolucion(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboResolucion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboAnioActaComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboAnioActa(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboAnioActa(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboJuzgadosComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgados(HttpServletRequest request) {
		ComboDTO response = busquedaEJGComision.comboJuzgados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTurnosTipoComision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosTipo(HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboTurnosTipo(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/filtros-ejg/guardias")
	public ResponseEntity<ComboDTO> comboGuardias(HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJGComision.comboGuardias(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/obligatorioFundamento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> obligatorioFundamento(HttpServletRequest request) {
		String response = busquedaEJGComision.obligatorioFundamento(request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busqueda-ejg-comision/asociarEJGActa", method = RequestMethod.POST)
	ResponseEntity<UpdateResponseDTO> asociarEJGActa(HttpServletRequest request, @RequestBody EjgItem ejgItem) throws SigaExceptions {
		UpdateResponseDTO response = busquedaEJGComision.asociarEJGActa(ejgItem, request);
		if(response.getStatus().equals("200")) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
