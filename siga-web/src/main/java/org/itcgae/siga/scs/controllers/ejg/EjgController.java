package org.itcgae.siga.scs.controllers.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJG;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
		ComboDTO response = busquedaEJG.comboResolucion(request);
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

	// busqueda
	@RequestMapping(value = "/filtros-ejg/busquedaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> busquedaEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = busquedaEJG.busquedaEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}
	
	//Prestaciones Rechazadas
	@RequestMapping(value = "/gestion-ejg/searchPrestacionesRechazadas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ScsEjgPrestacionRechazada>> searchPrestacionesRechazadas(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		List<ScsEjgPrestacionRechazada> response = gestionEJG.searchPrestacionesRechazadas(ejgItem, request);
		return new ResponseEntity<List<ScsEjgPrestacionRechazada>>(response, HttpStatus.OK);
	}

	// datosEJG
	@RequestMapping(value = "/gestion-ejg/datosEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> datosEJG(@RequestBody EjgItem ejgItem, HttpServletRequest request) {
		EjgDTO response = gestionEJG.datosEJG(ejgItem, request);
		return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
	}
	
	//getEjgDesigna
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
	
	//Insertar familiar
	@RequestMapping(value = "/gestion-ejg/insertFamiliarEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertFamiliar(@RequestBody List<String> datos, HttpServletRequest request) {
		InsertResponseDTO response = gestionEJG.insertFamiliarEJG(datos, request);
		if (response.getStatus().equals("OK"))
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
	ResponseEntity<ComboDTO> comboActaAnnio(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboActaAnnio(request);
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

	// Combo Tipo en calidad
	@RequestMapping(value = "/gestion-ejg/comboTipoencalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoencalidad(HttpServletRequest request) {
		ComboDTO response = gestionEJG.comboTipoencalidad(request);
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

	//insertaDatosGenerales
	@RequestMapping(value = "/gestion-ejg/insertaDatosGenerales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDTO> insertaDatosGenerales(@RequestBody EjgItem datos, HttpServletRequest request) {
		EjgDTO response = gestionEJG.insertaDatosGenerales(datos, request);
		if (response != null) {
			return new ResponseEntity<EjgDTO>(response, HttpStatus.OK);
		}
		else
			return new ResponseEntity<EjgDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//actualizaDatosGenerales
	@RequestMapping(value = "/gestion-ejg/actualizaDatosGenerales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizaDatosGenerales(@RequestBody EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.actualizaDatosGenerales(datos, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// añadirRemesa
	@RequestMapping(value = "/filtros-ejg/anadirExpedienteARemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> anadirExpedienteARemesa(@RequestBody List<EjgItem> datos,
			HttpServletRequest request) {
		InsertResponseDTO response = busquedaEJG.anadirExpedienteARemesa(datos, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	// descargarEEJ
	@RequestMapping(value = "/gestion-ejg/descargarExpedientesJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource>  descargarExpedientesJG(@RequestBody List<EjgItem> datos,
			HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = gestionEJG.descargarExpedientesJG(datos, request);
		
			return response;
	}

	//Actualizar turno, guardia y letrado
	@RequestMapping(value = "/gestion-ejg/guardarServiciosTramitacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarServiciosTramitacion(@RequestBody EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.guardarServiciosTramitacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// borrarEstado
	@RequestMapping(value = "/gestion-ejg/borrarEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarEstado(@RequestBody List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarEstado(datos, request);
		
		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	
	}

	// borrarFamiliar
	@RequestMapping(value = "/gestion-ejg/borrarFamiliar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarFamiliar(@RequestBody List<UnidadFamiliarEJGItem> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarFamiliar(datos, request);
		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// nuevoEstado
	@RequestMapping(value = "/gestion-ejg/nuevoEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevoEstado(@RequestBody List<EjgItem> datos, HttpServletRequest request) {
		InsertResponseDTO response = gestionEJG.nuevoEstado(datos, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	// guardarImpugnacion
	@RequestMapping(value = "/gestion-ejg/guardarImpugnacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarImpugnacion(@RequestBody EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.guardarImpugnacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// guardarResolucion
	@RequestMapping(value = "/gestion-ejg/guardarResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarResolucion(@RequestBody EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.guardarResolucion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// guardarInformeCalificacion
	@RequestMapping(value = "/gestion-ejg/guardarInformeCalificacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarInformeCalificacion(@RequestBody EjgItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.guardarInformeCalificacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// borrarInformeCalificacion
	@RequestMapping(value = "/gestion-ejg/borrarInformeCalificacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarInformeCalificacion(@RequestBody EjgItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarInformeCalificacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// descargarInformeCalificacion
	@RequestMapping(value = "/gestion-ejg/descargarInformeCalificacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> descargarInformeCalificacion(@RequestBody EjgItem datos,
			HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.descargarInformeCalificacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// descargarDocumentacion
	@RequestMapping(value = "/gestion-ejg/descargarDocumentacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> descargarDocumentacion(@RequestBody EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.descargarDocumentacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	// borrarRelacion
	@RequestMapping(value = "/gestion-ejg/borrarRelacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> borrarRelacion(@RequestBody List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO response = gestionEJG.borrarRelacion(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	//getComunicaciones
	@RequestMapping(value = "/gestion-ejg/getComunicaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> getComunicaciones(@RequestBody EjgItem item, HttpServletRequest request) {
		EnviosMasivosDTO response = gestionEJG.getComunicaciones(item, request);
		return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
	}
	
	//getRelacionesEJG
	@RequestMapping(value = "/gestion-ejg/getRelaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RelacionesDTO> getRelacionesEJG(@RequestBody EjgItem item, HttpServletRequest request) {
		RelacionesDTO response = gestionEJG.getRelacionesEJG(item, request);
		return new ResponseEntity<RelacionesDTO>(response, HttpStatus.OK);
	}
	// updateDatosJuridicos
		@RequestMapping(value = "/gestion-ejg/updateDatosJuridicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> updateDatosJuridicos(@RequestBody EjgItem datos,
				HttpServletRequest request) {
			UpdateResponseDTO response = gestionEJG.updateDatosJuridicos(datos, request);
			if (response.getStatus().equals("OK"))
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
