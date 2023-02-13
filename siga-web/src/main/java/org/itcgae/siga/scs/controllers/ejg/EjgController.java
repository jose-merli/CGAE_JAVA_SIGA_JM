package org.itcgae.siga.scs.controllers.ejg;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DelitosEjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosDTO;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.ExpInsosDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioEJGJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJG;
import org.itcgae.siga.scs.services.ejg.IEJGIntercambiosService;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/ejg")
public class EjgController {
	
	private Logger LOGGER = Logger.getLogger(EjgController.class);
	
	@Autowired
	private IBusquedaEJG busquedaEJG;

	@Autowired
	private IGestionEJG gestionEJG;
	
	@Autowired
	private ComboService comboService;

	@Autowired
	private IEJGIntercambiosService ejgIntercambiosService;

	@RequestMapping(value = "/filtros-ejg/comboTipoEJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoEJG(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboTipoEJG(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTipoEJGColegio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoColegioEjg(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboTipoColegioEjg(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoCalif", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen) {
		ComboDTO response = busquedaEJG.comboFundamentoCalificacion(request, list_dictamen);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboResolucion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboResolucion(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboResolucion(request,"filtro");
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoImpug", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboFundamentoImpug(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboFundamentoImpug(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPreceptivo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPreceptivo(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboPreceptivo(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboRenuncia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboRenuncia(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboRenuncia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboRemesa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboRemesa(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboRemesa(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboCreadoDesde", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboCreadoDesde(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboCreadoDesde(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboFundamentoJurid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> ComboFundamentoJurid(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJG.ComboFundamentoJurid(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboEstadoEJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEJG(HttpServletRequest request, String resolucion) {
		ComboDTO response = busquedaEJG.comboEstadoEJG(request, resolucion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboImpugnacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboImpugnacion(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboImpugnacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboJuzgados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgados(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboJuzgados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPonente", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPonente(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboPonente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboPrestaciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPrestaciones(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboPrestaciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-ejg/comboTurnosTipo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosTipo(HttpServletRequest request, String idTurno) {
		ComboDTO response = busquedaEJG.comboTurnosTipo(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/comboSituaciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboSituaciones(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboSituaciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/comboDelitos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDelitos(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboDelitos(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/comboTipoencalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoencalidad(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboTipoencalidad(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/gestion-ejg/comboResolucion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEjgResolucion(HttpServletRequest request) {
		ComboDTO response = busquedaEJG.comboResolucion(request,"ficha");
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJG.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}

	// Prestaciones Rechazadas
	@RequestMapping(value = "/gestion-ejg/searchPrestacionesRechazadas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ScsEjgPrestacionRechazada>> searchPrestacionesRechazadas(@RequestBody EjgItem ejgItem,
			HttpServletRequest request) {
		List<ScsEjgPrestacionRechazada> response = gestionEJG.searchPrestacionesRechazadas(ejgItem, request);
		return new ResponseEntity<List<ScsEjgPrestacionRechazada>>(response, HttpStatus.OK);
	}

	// datosEJG
	@RequestMapping(value = "/gestion-ejg/datosEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> datosEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = gestionEJG.datosEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}
	
	// datosEJGJustificacionExpres
	@RequestMapping(value = "/gestion-ejg/datosEJGJustificacionExpres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> datosEJGJustificacionExpres(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = gestionEJG.datosEJGJustificacionExpres(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}

	// getEjgDesigna
	@RequestMapping(value = "/gestion-ejg/getEjgDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDesignaDTO> getEjgDesigna(@RequestBody EjgItem datos, HttpServletRequest request) {
		EjgDesignaDTO response = gestionEJG.getEjgDesigna(datos, request);
		return new ResponseEntity<EjgDesignaDTO>(response, HttpStatus.OK);
	}

	// unidadFamiliar
	@RequestMapping(value = "/gestion-ejg/unidadFamiliarEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UnidadFamiliarEJGDTO> unidadFamiliarEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		UnidadFamiliarEJGDTO response = gestionEJG.unidadFamiliarEJG(ejgItem, request);
		return new ResponseEntity<UnidadFamiliarEJGDTO>(response, HttpStatus.OK);
	}

	// Insertar familiar
	@RequestMapping(value = "/gestion-ejg/insertFamiliarEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertFamiliar(@RequestBody List<String> datos, HttpServletRequest request) {
		InsertResponseDTO response = gestionEJG.insertFamiliarEJG(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Expedientes Económicos
	@RequestMapping(value = "/gestion-ejg/getExpedientesEconomicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ExpedienteEconomicoDTO> getExpedientesEconomicos(@RequestBody EjgItem ejgItem,
			HttpServletRequest request) {
		ExpedienteEconomicoDTO response = gestionEJG.getExpedientesEconomicos(ejgItem, request);
		return new ResponseEntity<ExpedienteEconomicoDTO>(response, HttpStatus.OK);
	}

	// Estados
	@RequestMapping(value = "/gestion-ejg/getEstados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EstadoEjgDTO> getEstados(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EstadoEjgDTO response = gestionEJG.getEstados(ejgItem, request);
		return new ResponseEntity<EstadoEjgDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestion-ejg/getUltEstadoEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EstadoEjgDTO> getUltEstadoEjg(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EstadoEjgDTO response = gestionEJG.getUltEstadoEjg(ejgItem, request);
		return new ResponseEntity<EstadoEjgDTO>(response, HttpStatus.OK);
	}

	// Documentos
	@RequestMapping(value = "/gestion-ejg/getDocumentos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDocumentacionDTO> getDocumentos(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDocumentacionDTO response = gestionEJG.getDocumentos(ejgItem, request);
		return new ResponseEntity<EjgDocumentacionDTO>(response, HttpStatus.OK);
	}

	// Informe Calficacion
	@RequestMapping(value = "/gestion-ejg/getDictamen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgItem> getDictamen(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgItem response = gestionEJG.getDictamen(ejgItem, request);
		return new ResponseEntity<EjgItem>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/comboOrigen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboOrigen(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboOrigen(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Resolucion
	@RequestMapping(value = "/gestion-ejg/getResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResolucionEJGItem> getResolucion(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		ResolucionEJGItem response = gestionEJG.getResolucion(ejgItem, request);
		return new ResponseEntity<ResolucionEJGItem>(response, HttpStatus.OK);
	}

	// comboActaAnnio
	@RequestMapping(value = "/gestion-ejg/comboActaAnnio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboActaAnnio(@RequestParam("anioacta") String anioacta, @RequestParam("idacta") String idacta, HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboActaAnnio(anioacta, idacta, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo TipoExpediente
	@RequestMapping(value = "/gestion-ejg/comboTipoExpediente", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoExpediente(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboTipoExpediente(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo Centros de detencion
	@RequestMapping(value = "/gestion-ejg/comboCDetencion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboCDetencion(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboCDetenciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo presentador
	@RequestMapping(value = "/gestion-ejg/comboPresentadores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPresentador(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboPresentadores(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo presentador
	@RequestMapping(value = "/gestion-ejg/comboTipoDocumentacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoDocumentacion(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboTipoDocumentacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo presentador
	@RequestMapping(value = "/gestion-ejg/comboDocumentos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDocumento(String idTipoDocumentacion, HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboDocumentos(idTipoDocumentacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// cambiarEstadoEJGs
	@RequestMapping(value = "/gestion-ejg/cambioEstadoMasivo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cambioEstadoMasivo(@RequestBody List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.cambioEstadoMasivo(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

//	if (response.getError().getCode() == 200)
//		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//	else
//		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	// insertaDatosGenerales
	@RequestMapping(value = "/gestion-ejg/insertaDatosGenerales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> insertaDatosGenerales(@RequestBody EjgItem datos, HttpServletRequest request)
			throws Exception {
		EjgDTO response = gestionEJG.insertaDatosGenerales(datos, request);
		if (response != null) {
			return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
		} else
			return new ResponseEntity<EjgDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// actualizaDatosGenerales
	@RequestMapping(value = "/gestion-ejg/actualizaDatosGenerales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizaDatosGenerales(@RequestBody EjgItem datos, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = gestionEJG.actualizaDatosGenerales(datos, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// añadirRemesa
	@RequestMapping(value = "/filtros-ejg/anadirExpedienteARemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> anadirExpedienteARemesa(@RequestBody List<EjgItem> datos,
			HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();
		try {
			response = busquedaEJG.anadirExpedienteARemesa(datos, request);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	// descargarEEJ
	@RequestMapping(value = "/gestion-ejg/descargarExpedientesJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarExpedientesJG(@RequestBody List<EjgItem> datos,
			HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = gestionEJG.descargarExpedientesJG(datos, request);

		return response;
	}

	@RequestMapping(value = "/gestion-ejg/solicitarEEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> solicitarEEJG(@RequestBody UnidadFamiliarEJGItem datos,
			HttpServletRequest request) {
		InsertResponseDTO response = gestionEJG.solicitarEEJG(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Actualizar turno, guardia y letrado
	@RequestMapping(value = "/gestion-ejg/guardarServiciosTramitacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarServiciosTramitacion(@RequestBody EjgItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.guardarServiciosTramitacion(datos, request);
		if(response.getStatus().equals(SigaConstants.OK)) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// borrarEstado
	@RequestMapping(value = "/gestion-ejg/borrarEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarEstado(@RequestBody List<EstadoEjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarEstado(datos, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// editarEstado
	@RequestMapping(value = "/gestion-ejg/editarEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> editarEstado(@RequestBody EstadoEjgItem datos, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = gestionEJG.editarEstado(datos, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// borrarFamiliar
	@RequestMapping(value = "/gestion-ejg/borrarFamiliar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarFamiliar(@RequestBody List<UnidadFamiliarEJGItem> datos,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = gestionEJG.borrarFamiliar(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// nuevoEstado
	@RequestMapping(value = "/gestion-ejg/nuevoEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevoEstado(@RequestBody EstadoEjgItem datos, HttpServletRequest request) {
		InsertResponseDTO response = gestionEJG.nuevoEstado(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// guardarResolucion
	@RequestMapping(value = "/gestion-ejg/guardarResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarResolucion(@RequestBody ResolucionEJGItem datos,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = gestionEJG.guardarResolucion(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// GetHabilitarActa (Permiso en la tarjeta resolucion)
	@RequestMapping(value = "/gestion-ejg/getHabilitarActa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> getHabilitarActa(HttpServletRequest request) {
		Boolean response = gestionEJG.getHabilitarActa(request);
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
	
	// GetHabilitarActa (Permiso en la tarjeta resolucion)
	@RequestMapping(value = "/gestion-ejg/getEditResolEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> getEditResolEjg(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		Boolean response = gestionEJG.getEditResolEjg(ejgItem, request);
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}

	// borrarRelacion
	@RequestMapping(value = "/gestion-ejg/borrarRelacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarRelacion(@RequestBody List<String> datos, HttpServletRequest request) {
		DeleteResponseDTO response = gestionEJG.borrarRelacion(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// borrarRelacion
	@RequestMapping(value = "/gestion-ejg/borrarRelAsisEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarRelacionAsistenciaEJG(@RequestBody RelacionesItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarRelacionAsistenciaEJG(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// borrarRelacion
	@RequestMapping(value = "/gestion-ejg/borrarRelSojEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarRelacionSojEJG(@RequestBody RelacionesItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarRelacionSojEJG(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// getComunicaciones
	@RequestMapping(value = "/gestion-ejg/getComunicaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> getComunicaciones(@RequestBody EjgItem item, HttpServletRequest request) {
		EnviosMasivosDTO response = gestionEJG.getComunicaciones(item, request);
		return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
	}

	// getRelacionesEJG
	@RequestMapping(value = "/gestion-ejg/getRelaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RelacionesDTO> getRelacionesEJG(@RequestBody EjgItem item, HttpServletRequest request) {
		RelacionesDTO response = gestionEJG.getRelacionesEJG(item, request);
		return new ResponseEntity<RelacionesDTO>(response, HttpStatus.OK);
	}

	// descargarDocumentoResolucion
	@RequestMapping(value = "/gestion-ejg/descargarDocumentoResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentoResolucion(@RequestBody String docResolucion,
			HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = gestionEJG.descargarDocumentoResolucion(docResolucion, request);

		return response;
	}

	// updateDatosJuridicos
	@RequestMapping(value = "/gestion-ejg/updateDatosJuridicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosJuridicos(@RequestBody EjgItem datos, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = gestionEJG.updateDatosJuridicos(datos, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// delete ContrarioEJG
	// this.selectedDatos.idPersona, this.selectedDatos.anio,
	// this.selectedDatos.numero, this.selectedDatos.tipoEJG
	@RequestMapping(value = "/gestion-ejg/deleteContrarioEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteContrarioEJG(@RequestBody String[] item, HttpServletRequest request) {
		ScsContrariosejg contrario = new ScsContrariosejg();
		contrario.setIdpersona(Long.parseLong(item[0]));
		contrario.setAnio(Short.parseShort(item[1]));
		contrario.setIdtipoejg(Short.parseShort(item[3]));
		contrario.setNumero(Long.parseLong(item[2]));
		UpdateResponseDTO response = gestionEJG.deleteContrarioEJG(contrario, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// [this.ejg.numero.toString(), this.ejg.annio, this.ejg.tipoEJG,
	// this.historicoContrario]
	@RequestMapping(value = "/gestion-ejg/busquedaListaContrariosEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ListaContrarioEJGJusticiableItem>> busquedaListaContrariosEJG(@RequestBody String[] item,
			HttpServletRequest request) {
		EjgItem ejg = new EjgItem();
		ejg.setAnnio(item[1]);
		ejg.setNumero(item[0]);
		ejg.setTipoEJG(item[2]);
		List<ListaContrarioEJGJusticiableItem> response = gestionEJG.busquedaListaContrariosEJG(ejg, request,
				Boolean.parseBoolean(item[3]));
		if (response != null) {
			return new ResponseEntity<List<ListaContrarioEJGJusticiableItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ListaContrarioEJGJusticiableItem>>(
					new ArrayList<ListaContrarioEJGJusticiableItem>(), HttpStatus.OK);
		}
	}

	// Insertar contrario EJG
	// [ justiciable.idpersona, ejg.annio, ejg.tipoEJG, ejg.numero]
	@RequestMapping(value = "/gestion-ejg/insertContrarioEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertContrarioEJG(@RequestBody String[] item, HttpServletRequest request) {
		ScsContrariosejg contrario = new ScsContrariosejg();
		contrario.setIdpersona(Long.parseLong(item[0]));
		contrario.setAnio(Short.parseShort(item[1]));
		contrario.setNumero(Long.parseLong(item[3]));
		contrario.setIdtipoejg(Short.parseShort(item[2]));
		InsertResponseDTO response = gestionEJG.insertContrarioEJG(contrario, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// [ sessionStorage.getItem("personaDesigna"), ejg.annio, ejg.numero,
	// ejg.tipoEJG, this.generalBody.apellidos.concat(",", this.generalBody.nombre)]
	@RequestMapping(value = "/gestion-ejg/updateRepresentanteContrarioEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateRepresentanteContrarioEJG(@RequestBody String[] item,
			HttpServletRequest request) {
		ScsContrariosejg contrario = new ScsContrariosejg();
		contrario.setIdpersona(Long.parseLong(item[0]));
		contrario.setAnio(Short.parseShort(item[1]));
		contrario.setIdtipoejg(Short.parseShort(item[3]));
		contrario.setNumero(Long.parseLong(item[2]));
		contrario.setNombrerepresentanteejg(item[4]);
		
		if (item[5] != null) {
			contrario.setIdrepresentanteejg(Long.parseLong(item[5]));
		}
		
		UpdateResponseDTO response = gestionEJG.updateRepresentanteContrarioEJG(contrario, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// [ejg.idInstitucion, sessionStorage.getItem("personaDesigna"), ejg.annio,
	// ejg.numero, ejg.tipoEJG, this.generalBody.idProcurador,
	// this.generalBody.idInstitucion];
	@RequestMapping(value = "/gestion-ejg/updateProcuradorContrarioEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateProcuradorContrarioEJG(@RequestBody String[] item,
			HttpServletRequest request) {
//	ResponseEntity<UpdateResponseDTO> updateProcuradorContrarioEJG(@RequestBody ScsContrariosejg contrario, HttpServletRequest request) {
		ScsContrariosejg contrario = new ScsContrariosejg();
		contrario.setIdinstitucion(Short.parseShort(item[0]));
		contrario.setIdpersona(Long.parseLong(item[1]));
		contrario.setAnio(Short.parseShort(item[2]));
		contrario.setNumero(Long.parseLong(item[3]));
		contrario.setIdtipoejg(Short.valueOf(item[4]));
		if (item[5] != null)
			contrario.setIdprocurador(Long.valueOf(item[5]));
		else
			contrario.setIdprocurador(null);
		if (item[6] != null)
			contrario.setIdinstitucionProcu(Short.parseShort(item[6]));
		else
			contrario.setIdinstitucionProcu(null);
		UpdateResponseDTO response = gestionEJG.updateProcuradorContrarioEJG(contrario, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// [ sessionStorage.getItem("personaDesigna"), ejg.annio, ejg.numero,
	// ejg.tipoEJG, this.generalBody.idPersona, this.generalBody.nombre]
	@RequestMapping(value = "/gestion-ejg/updateAbogadoContrarioEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateAbogadoContrario(@RequestBody String[] item, HttpServletRequest request) {

		ScsContrariosejg contrario = new ScsContrariosejg();
		contrario.setIdpersona(Long.parseLong(item[0]));
		contrario.setAnio(Short.parseShort(item[1]));
		contrario.setIdtipoejg(Short.parseShort(item[3]));
		contrario.setNumero(Long.parseLong(item[2]));
		if(!item[4].equals(""))contrario.setIdabogadocontrarioejg(Long.parseLong(item[4]));
		else contrario.setIdabogadocontrarioejg(null);
		if(!item[5].equals(""))contrario.setNombreabogadocontrarioejg(item[5]);
		else contrario.setNombreabogadocontrarioejg(null);
		UpdateResponseDTO response = gestionEJG.updateAbogadoContrarioEJG(contrario, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/busquedaProcuradorEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> busquedaProcurador(@RequestBody EjgItem ejg, HttpServletRequest request) {
		ProcuradorDTO response = gestionEJG.busquedaProcuradorEJG(ejg, request);
		if (response != null) {
			return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProcuradorDTO>(new ProcuradorDTO(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/gestion-ejg/guardarProcuradorEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarProcuradorEJG(@RequestBody EjgItem item, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = gestionEJG.guardarProcuradorEJG(item, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/gestion-ejg/comboProcedimientosConJuzgado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientosConJuzgado(HttpServletRequest request, @RequestBody EjgItem item) {

		ComboDTO response = comboService.comboProcedimientosConJuzgadoEjg(request, item);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/getDelitosEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DelitosEjgDTO> getDelitosEjg(@RequestBody EjgItem item, HttpServletRequest request) {
		DelitosEjgDTO response = gestionEJG.getDelitosEjg(item, request);
		return new ResponseEntity<DelitosEjgDTO>(response, HttpStatus.OK);
	}

//	@RequestMapping(value = "/gestion-ejg/actualizarDelitosEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<InsertResponseDTO> actualizarDelitosEJG(@RequestBody EjgItem item, HttpServletRequest request) {
//		InsertResponseDTO response = gestionEJG.actualizarDelitosEJG(item, request);
//		if (response.getStatus().equals(SigaConstants.OK))
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@RequestMapping(value = "/gestion-ejg/busquedaProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> busquedaProcuradores(@RequestBody ProcuradorItem procuradorItem,
			HttpServletRequest request) {
		ProcuradorDTO response = gestionEJG.busquedaProcuradores(procuradorItem, request);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/subirDocumentoEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<InsertResponseDTO> subirDocumentoEjg(MultipartHttpServletRequest request) throws Exception {
		InsertResponseDTO response = gestionEJG.subirDocumentoEjg(request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/crearDocumentacionEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> crearDocumentacionEjg(@RequestBody EjgDocumentacionItem documentoEjgItem,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO response = gestionEJG.crearDocumentacionEjg(documentoEjgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/actualizarDocumentacionEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizarDocumentacionEjg(@RequestBody EjgDocumentacionItem documentoEjgItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.actualizarDocumentacionEjg(documentoEjgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/eliminarDocumentosEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarDocumentosEjg(@RequestBody EjgDocumentacionItem documentacionEjgItem,
			HttpServletRequest request) {
		DeleteResponseDTO response = gestionEJG.eliminarDocumentosEjg(documentacionEjgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/eliminarDocumentacionEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarDocumentacionEjg(
			@RequestBody List<EjgDocumentacionItem> documentacionEjgItem, HttpServletRequest request) {
		DeleteResponseDTO response = gestionEJG.eliminarDocumentacionEjg(documentacionEjgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/gestion-ejg/descargarDocumentosEjg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosEjg(
			@RequestBody List<EjgDocumentacionItem> listaDocumentoEjgItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = gestionEJG.descargarDocumentosEjg(listaDocumentoEjgItem,
				request);
		return response;
	}

	@RequestMapping(value = "/gestion-ejg/actualizarInformeCalificacionEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizarInformeCalificacion(@RequestBody EjgItem item,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = gestionEJG.actualizarInformeCalificacionEjg(item, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Asociar designacion
	@RequestMapping(value = "/gestion-ejg/asociarDesignacion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarDesignacion(@RequestBody List<String> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.asociarDesignacion(datos, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// descargarInformeCalificacion
	@RequestMapping(value = "/gestion-ejg/descargarInformeCalificacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> descargarInformeCalificacion(@RequestBody EjgItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.descargarInformeCalificacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// Asociar Asistencia
	@RequestMapping(value = "/gestion-ejg/asociarAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarAsistencia(@RequestBody List<String> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.asociarAsistencia(datos, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Asociar SOJ
	@RequestMapping(value = "/gestion-ejg/asociarSOJ", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarSOJ(@RequestBody List<String> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.asociarSOJ(datos, request);

		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// guardarImpugnacion
	@RequestMapping(value = "/gestion-ejg/guardarImpugnacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarImpugnacion(@RequestBody EjgItem datos, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = gestionEJG.guardarImpugnacion(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestion-ejg/searchListDocEjg", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocushareDTO> searchListEjg(HttpServletRequest request, String anio, String numero, String idTipoEJG,
			String identificadords) throws Exception {
		EjgItem ejgItem = new EjgItem();

		ejgItem.setAnnio(anio);
		ejgItem.setNumero(numero);
		ejgItem.setTipoEJG(idTipoEJG);
		
		// AQUI LLEGA DE VEZ EN CUANDO A "undefined" (al menos el elemento anio)
		DocushareDTO response = new DocushareDTO();
		if ( !StringUtils.isEmpty(anio) && !StringUtils.isEmpty(numero) && !StringUtils.isEmpty(idTipoEJG)
				&& (("undefined").equalsIgnoreCase(anio) || ("undefined").equalsIgnoreCase(numero) || ("undefined").equalsIgnoreCase(idTipoEJG)) ) {
			LOGGER.info("+++++ searchListEjg('/gestion-ejg/searchListDocEjg') --> UNDEFINED VALUES => anio = " + anio + " | numero = " + numero + " | idTipoEJG = " + idTipoEJG);
			return new ResponseEntity<DocushareDTO>(response, HttpStatus.NO_CONTENT);
		} else {
			response = gestionEJG.searchListDocEjg(ejgItem, request);
			return new ResponseEntity<DocushareDTO>(response, HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/gestion-ejg/insertCollectionEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> insertCollectionEjg(@RequestBody EjgItem ejgItem, HttpServletRequest request)
			throws Exception {
		String response = gestionEJG.insertCollectionEjg(ejgItem, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestion-ejg/searchListDirEjg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocushareDTO> searchDirNoCol(@RequestParam("numPagina") int numPagina, @RequestBody DocuShareObjectVO docu, HttpServletRequest request) throws Exception { 
		DocushareDTO response = gestionEJG.searchListDirEjg(numPagina, docu, request);
		return new ResponseEntity<DocushareDTO>(response, HttpStatus.OK);
	}

	// Prestaciones Rechazadas
	@RequestMapping(value = "/gestion-ejg/getDatosExpInsos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ExpInsosDTO> getDatosExpInsos(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		ExpInsosDTO response = gestionEJG.getDatosExpInsos(ejgItem, request);
		return new ResponseEntity<ExpInsosDTO>(response, HttpStatus.OK);
	}

	// Integración con Pericles para la Zona Común

	@RequestMapping(value = "/gestion-ejg/esColegioZonaComun", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> esColegioZonaComun(HttpServletRequest request) {
		ResponseDataDTO response = new ResponseDataDTO();
		try {
			response = ejgIntercambiosService.esColegioZonaComun(request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/getListadoIntercambiosAltaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgListaIntercambiosDTO> getListadoIntercambiosAltaEJG(@RequestBody EjgItem item, HttpServletRequest request) {
		EjgListaIntercambiosDTO response = new EjgListaIntercambiosDTO();
		try {
			response = ejgIntercambiosService.getListadoIntercambiosAltaEJG(item, request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<EjgListaIntercambiosDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/getListadoIntercambiosDocumentacionEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgListaIntercambiosDTO> getListadoIntercambiosDocumentacionEJG(@RequestBody EjgItem item, HttpServletRequest request) {
		EjgListaIntercambiosDTO response = new EjgListaIntercambiosDTO();
		try {
			response = ejgIntercambiosService.getListadoIntercambiosDocumentacionEJG(item, request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<EjgListaIntercambiosDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/consultarEstadoPericles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> consultarEstadoPericles(@RequestBody EjgItem item, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();
		try {
			response = ejgIntercambiosService.consultarEstadoPericles(item, request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/enviaDocumentacionAdicional", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> enviaDocumentacionAdicional(@RequestBody EjgDocumentacionItem documentacionItem, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();
		try {
			response = ejgIntercambiosService.enviaDocumentacionAdicional(documentacionItem, request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/enviaDocumentacionAdicionalExpEconomico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> enviaDocumentacionAdicionalUnidadFamiliar(@RequestBody ExpedienteEconomicoItem expedienteEconomicoItem, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();
		try {
			response = ejgIntercambiosService.enviaDocumentacionAdicionalUnidadFamiliar(expedienteEconomicoItem, request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/enviaDocumentacionAdicionalRegtel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> enviaDocumentacionAdicionalRegtel(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();
		try {
			response = ejgIntercambiosService.enviaDocumentacionAdicionalRegtel(ejgItem, request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestion-ejg/esColegioConfiguradoEnvioCAJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> esColegioConfiguradoEnvioCAJG(HttpServletRequest request) {
		ResponseDataDTO response = new ResponseDataDTO();
		try {
			response = ejgIntercambiosService.esColegioConfiguradoEnvioCAJG(request);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
		}
		return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
	}

}
