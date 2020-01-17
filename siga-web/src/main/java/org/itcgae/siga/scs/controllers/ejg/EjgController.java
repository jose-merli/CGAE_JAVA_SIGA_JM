package org.itcgae.siga.scs.controllers.ejg;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJG;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ejg")
public class EjgController {
	@Autowired
	private IBusquedaEJG busquedaEJG;
	@Autowired
	private IGestionEJG gestionEJG;
	
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
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen) {
		ComboDTO response = busquedaEJG.comboFundamentoCalificacion(request, list_dictamen);
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
	@RequestMapping(value = "/filtros-ejg/comboPonente",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPonente(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboPonente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboPrestaciones",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPrestaciones(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboPrestaciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/filtros-ejg/comboTurnosTipo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosTipo(HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJG.comboTurnosTipo(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	//busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJG.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}
	//datosEJG
		@RequestMapping(value = "/gestion-ejg/datosEJG",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<EjgDTO> datosEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
			EjgDTO response = gestionEJG.datosEJG(ejgItem, request);
			return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
		}
	//unidadFamiliar
	@RequestMapping(value = "/gestion-ejg/unidadFamiliarEJG",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UnidadFamiliarEJGDTO> unidadFamiliarEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		UnidadFamiliarEJGDTO response = gestionEJG.unidadFamiliarEJG(ejgItem, request);
		return new ResponseEntity<UnidadFamiliarEJGDTO>(response, HttpStatus.OK);
	}
	//Expedientes Económicos
	@RequestMapping(value = "/gestion-ejg/getExpedientesEconomicos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ExpedienteEconomicoDTO> getExpedientesEconomicos(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		ExpedienteEconomicoDTO response = gestionEJG.getExpedientesEconomicos(ejgItem, request);
		return new ResponseEntity<ExpedienteEconomicoDTO>(response, HttpStatus.OK);
	}
	//Estados
	@RequestMapping(value = "/gestion-ejg/getEstados",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EstadoEjgDTO> getEstados(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EstadoEjgDTO response = gestionEJG.getEstados(ejgItem, request);
		return new ResponseEntity<EstadoEjgDTO>(response, HttpStatus.OK);
	}
	//Documentos
		@RequestMapping(value = "/gestion-ejg/getDocumentos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<EjgDocumentacionDTO> getDocumentos(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
			EjgDocumentacionDTO response = gestionEJG.getDocumentos(ejgItem, request);
			return new ResponseEntity<EjgDocumentacionDTO>(response, HttpStatus.OK);
		}
	//Informe Calficacion
	@RequestMapping(value = "/gestion-ejg/getDictamen",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgItem> getDictamen(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgItem response = gestionEJG.getDictamen(ejgItem, request);
		return new ResponseEntity<EjgItem>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/gestion-ejg/comboOrigen",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboOrigen(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboOrigen(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
