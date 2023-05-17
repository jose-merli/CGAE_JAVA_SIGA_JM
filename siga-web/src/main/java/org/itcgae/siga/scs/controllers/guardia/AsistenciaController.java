package org.itcgae.siga.scs.controllers.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.scs.services.guardia.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/asistencia")
public class AsistenciaController {

	@Autowired
	private AsistenciaService asistenciaService;
	
	@GetMapping(value = "/turnosByColegiadoFecha")
	public ResponseEntity<ComboDTO> getTurnosByColegiadoFecha(HttpServletRequest request, 
			@RequestParam String guardiaDia,
			@RequestParam(required = false) String idPersona) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getTurnosByColegiadoFecha(request, guardiaDia, idPersona);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/guardiasByTurnoColegiadoFecha")
	public ResponseEntity<ComboDTO> getGuardiasByTurnoColegiadoFecha(HttpServletRequest request,
															  @RequestParam String guardiaDia,
															  @RequestParam String idTurno,
															  @RequestParam(required = false) String idPersona) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getGuardiasByTurnoColegiadoFecha(request, guardiaDia, idTurno, idPersona);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getTiposAsistencia")
	public ResponseEntity<ComboDTO> getTiposAsistencia(HttpServletRequest request, 
			@RequestParam String idTurno,
			@RequestParam String idGuardia) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getTiposAsistenciaColegio(request, idTurno, idGuardia);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/buscarTarjetaAsistencia")
	public ResponseEntity<TarjetaAsistenciaResponseDTO> buscarTarjetaAsistencias(HttpServletRequest request, @RequestParam String anioNumero) {
		TarjetaAsistenciaResponseDTO response = null;
		try {
			response = asistenciaService.buscarTarjetaAsistencias(request, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaAsistenciaResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getColegiadosGuardiaDia")
	public ResponseEntity<ComboDTO> getColegiadosGuardiaDia(HttpServletRequest request, 
			@RequestParam String idTurno,
			@RequestParam String idGuardia,
			@RequestParam String guardiaDia) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getColegiadosGuardiaDia(request, idTurno, idGuardia, guardiaDia);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/buscarAsistenciasExpress", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TarjetaAsistenciaResponseDTO2> searchAsistenciasExpress(HttpServletRequest request, @RequestBody FiltroAsistenciaItem filtro) {
		TarjetaAsistenciaResponseDTO2 response = null;
		try {
			response = asistenciaService.searchAsistenciasExpress(request, filtro);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaAsistenciaResponseDTO2>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/buscarAsistencias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TarjetaAsistenciaResponseDTO> searchAsistencias(HttpServletRequest request, @RequestBody FiltroAsistenciaItem filtro) {
		TarjetaAsistenciaResponseDTO response = null;
		try {
			response = asistenciaService.searchAsistencias(request, filtro);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaAsistenciaResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/buscarAsistenciasByIdSolicitud")
	public ResponseEntity<TarjetaAsistenciaResponseDTO> buscarAsistenciasByIdSolicitud(HttpServletRequest request, 
			@RequestParam String idSolicitud) {
		TarjetaAsistenciaResponseDTO response = null;
		try {
			response = asistenciaService.searchAsistenciasByIdSolicitud(request, idSolicitud);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaAsistenciaResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getJuzgados")
	public ResponseEntity<ComboDTO> getJuzgados(HttpServletRequest request, 
			@RequestParam String idTurno) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getJuzgados(request, idTurno);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getComisarias")
	public ResponseEntity<ComboDTO> getComisarias(HttpServletRequest request, 
			@RequestParam String idTurno) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getComisarias(request, idTurno);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardarAsistenciasExpres", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> guardarAsistenciasExpres(HttpServletRequest request, @RequestBody List<TarjetaAsistenciaResponse2Item> asistencias) {
		DeleteResponseDTO response = null;
		try {
			response = asistenciaService.guardarAsistenciasExpres(request, asistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardarAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> guardarAsistencia(HttpServletRequest request, @RequestParam(required = false) String idAsistenciaCopy, @RequestParam(required = false) String isLetrado, @RequestParam(required = false) String isTodaySelected, @RequestBody List<TarjetaAsistenciaResponseItem> asistencias) {
		InsertResponseDTO response = null;
			response = asistenciaService.guardarAsistencia(request, asistencias, idAsistenciaCopy, isLetrado, isTodaySelected);
		if (response.getStatus() == "ERRORASOCIADAS") {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.CONFLICT);
		} 

		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> eliminarAsistencia(HttpServletRequest request, @RequestBody List<TarjetaAsistenciaResponseItem> asistencias) {
		DeleteResponseDTO response = null;
		try {
			response = asistenciaService.eliminarAsistencias(request, asistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/isUnicaAsistenciaPorGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringDTO> isUnicaAsistenciaPorGuardia(HttpServletRequest request, @RequestBody List<TarjetaAsistenciaResponseItem> asistencias) {
		StringDTO response = null;
		try {
			response = asistenciaService.isUnicaAsistenciaPorGuardia(request, asistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/desvalidarGuardiasAsistencias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> desvalidarGuardiaAsistencia(HttpServletRequest request, @RequestBody List<TarjetaAsistenciaResponseItem> asistencias) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.desvalidarGuardiaAsistencia(request, asistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getDefaultTipoAsistenciaColegio")
	public ResponseEntity<StringDTO> getDefaultTipoAsistenciaColegio(HttpServletRequest request) {
		StringDTO response = null;
		try {
			response = asistenciaService.getDefaultTipoAsistenciaColegio(request);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/updateEstadoAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> updateEstadoAsistencia(HttpServletRequest request, @RequestBody List<TarjetaAsistenciaResponseItem> asistencias) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.updateEstadoAsistencia(request,asistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/asociarAsistido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> asociarAsistido(HttpServletRequest request, @RequestBody JusticiableItem justiciable, @RequestParam String anioNumero, @RequestParam String actualizaDatos) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.asociarJusticiable(request, justiciable, anioNumero, actualizaDatos);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/desasociarAsistido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> desasociarAsistido(HttpServletRequest request, @RequestBody JusticiableItem justiciable, @RequestParam String anioNumero) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.desasociarJusticiable(request, justiciable, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchListaContrarios")
	public ResponseEntity<List<ListaContrarioJusticiableItem>> searchListaContrarios(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam boolean mostrarHistorico) {
		List<ListaContrarioJusticiableItem> response = null;
		try {
			response = asistenciaService.searchListaContrarios(request,anioNumero,mostrarHistorico);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<List<ListaContrarioJusticiableItem>>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/asociarContrario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> asociarContrario(HttpServletRequest request, @RequestBody List<JusticiableItem> justiciables, @RequestParam String anioNumero) {
		InsertResponseDTO response = null;
		try {
			response = asistenciaService.asociarContrario(request,justiciables,anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarContrario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> actualizar(@RequestBody String[] item, HttpServletRequest request) {
		InsertResponseDTO response = null;
		try {
			response = asistenciaService.actualizarContrario(item, request);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/desasociarContrario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> desasociarContrario(HttpServletRequest request, @RequestBody List<ListaContrarioJusticiableItem> contrarios, @RequestParam String anioNumero) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.desasociarContrario(request,contrarios,anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchTarjetaDefensaJuridica")
	public ResponseEntity<TarjetaDefensaJuridicaDTO> searchTarjetaDefensaJuridica(HttpServletRequest request, @RequestParam String anioNumero) {
		TarjetaDefensaJuridicaDTO response = null;
		try {
			response = asistenciaService.searchTarjetaDefensaJuridica(request, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaDefensaJuridicaDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarTarjetaDefensaJuridica", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> saveTarjetaDefensaJuridica(HttpServletRequest request, @RequestBody TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem, @RequestParam String anioNumero) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.guardarTarjetaDefensaJuridica(request, tarjetaDefensaJuridicaItem, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchTarjetaObservaciones")
	public ResponseEntity<TarjetaObservacionesDTO> searchTarjetaObservaciones(HttpServletRequest request, @RequestParam String anioNumero) {
		TarjetaObservacionesDTO response = null;
		try {
			response = asistenciaService.searchTarjetaObservaciones(request, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaObservacionesDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarTarjetaObservaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> saveTarjetaObservaciones(HttpServletRequest request, @RequestBody TarjetaObservacionesItem tarjetaObservacionesItem, @RequestParam String anioNumero) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.guardarTarjetaObservaciones(request, tarjetaObservacionesItem, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchRelaciones")
	public ResponseEntity<RelacionesDTO> searchRelaciones(HttpServletRequest request, @RequestParam String anioNumero) {
		RelacionesDTO response = null;
		try {
			response = asistenciaService.searchRelaciones(request, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<RelacionesDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/asociarDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> asociarDesigna(HttpServletRequest request, @RequestParam String anioNumero, @RequestBody DesignaItem designa, @RequestParam String copiarDatos) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.asociarDesigna(request, anioNumero, designa, copiarDatos);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/asociarEjg", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> asociarEjg(HttpServletRequest request, @RequestParam String anioNumero, @RequestBody EjgItem ejg, @RequestParam String copiarDatos) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.asociarEjg(request, anioNumero, ejg, copiarDatos);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarRelacion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> eliminarRelacion(HttpServletRequest request, @RequestParam String anioNumero, @RequestBody List<RelacionesItem> asuntos) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.eliminarRelacion(request, anioNumero, asuntos);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchDocumentacion")
	public ResponseEntity<DocumentacionAsistenciaDTO> searchDocumentacion(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam(required = false) String idActuacion) {
		DocumentacionAsistenciaDTO response = null;
		try {
			response = asistenciaService.searchDocumentacion(request, anioNumero, idActuacion);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DocumentacionAsistenciaDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/subirDocumentoAsistencia", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<InsertResponseDTO> subirDocumentoAsistencia(MultipartHttpServletRequest request) {
		InsertResponseDTO response = null;
		try {
			response = asistenciaService.subirDocumentoAsistencia(request);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarDocumentoAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> eliminarDocumentoAsistencia(HttpServletRequest request, @RequestParam String anioNumero, @RequestBody List<DocumentacionAsistenciaItem> documentos) {
		DeleteResponseDTO response = null;
		try {
			response = asistenciaService.eliminarDocumentoAsistencia(request,documentos,anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/descargarDocumentoAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosAsistencia(
			@RequestBody List<DocumentacionAsistenciaItem> documentos, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = asistenciaService.descargarDocumento(request, documentos);
		return response;
	}


	@PostMapping(value = "/saveCaracteristicas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> saveCaracteristicas(HttpServletRequest request, @RequestBody CaracteristicasAsistenciaItem caracteristicasAsistenciaItem, @RequestParam String anioNumero) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.saveCaracteristicas(request, caracteristicasAsistenciaItem, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchCaracteristicas")
	public ResponseEntity<CaracteristicasAsistenciaDTO> searchCaracteristicas(HttpServletRequest request, @RequestParam String anioNumero) {
		CaracteristicasAsistenciaDTO response = null;
		try {
			response = asistenciaService.searchCaracteristicas(request, anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<CaracteristicasAsistenciaDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/searchActuaciones")
	public ResponseEntity<ActuacionAsistenciaDTO> searchActuaciones(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam String mostrarHistorico) {
		ActuacionAsistenciaDTO response = null;
		try {
			response = asistenciaService.searchActuaciones(request, anioNumero, mostrarHistorico);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ActuacionAsistenciaDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/updateEstadoActuacion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> updateEstadoActuacion(HttpServletRequest request, @RequestBody List<ActuacionAsistenciaItem> actuaciones, @RequestParam String anioNumero) {
		UpdateResponseDTO response = null;
		try {
			response = asistenciaService.updateEstadoActuacion(request,actuaciones,anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarActuaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> eliminarActuaciones(HttpServletRequest request, @RequestBody List<ActuacionAsistenciaItem> actuaciones, @RequestParam String anioNumero) {
		DeleteResponseDTO response = null;
		try {
			response = asistenciaService.eliminarActuaciones(request,actuaciones,anioNumero);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
}
