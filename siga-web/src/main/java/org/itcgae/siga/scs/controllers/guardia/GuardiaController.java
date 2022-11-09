
package org.itcgae.siga.scs.controllers.guardia;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.BusquedaLetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosSalidaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesResponse;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioyProgramacionItem;
import org.itcgae.siga.DTOs.scs.DeleteCalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DeleteIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.GuardiaCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDTO;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesResponseDTO;
import org.itcgae.siga.DTOs.scs.LetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.RangoFechasItem;
import org.itcgae.siga.DTOs.scs.SaveIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.itcgae.siga.scs.services.impl.guardia.GuardiasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/guardia")
public class GuardiaController {

	private Logger LOGGER = Logger.getLogger(GuardiaController.class);

	@Autowired
	GuardiasService guardiasService;

	@PostMapping(value = "/busquedaGuardia/searchGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> searchGuardias(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request) {
		GuardiasDTO response = guardiasService.searchGuardias(guardiasItem, request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/busquedaGuardiasColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> busquedaGuardiasColegiado(@RequestBody GuardiasItem guardiasItem,
			HttpServletRequest request) {
		GuardiasDTO response = guardiasService.busquedaGuardiasColegiado(guardiasItem, request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/deleteGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.deleteGuardias(guardiasDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/activateGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateGuardias(@RequestBody GuardiasDTO guardiasDTO,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.activateGuardias(guardiasDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/updateGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGuardias(@RequestBody GuardiasItem guardiaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.updateGuardia(guardiaItem, request);
		if (response.getStatus() == "OK") {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/busquedaGuardia/detalleGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> detalleGuardia(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request) {
		GuardiasItem response = guardiasService.detalleGuardia(guardiaItem, request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/createGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createGuardia(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request) {
		InsertResponseDTO response = guardiasService.createGuardia(guardiaItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/resumenGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> resumenGuardia(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request) {
		GuardiasItem response = guardiasService.resumenGuardia(guardiaItem, request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/resumenConfiguracionCola", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> resumenConfiguracionCola(@RequestBody GuardiasItem guardiaTurno,
			HttpServletRequest request) {
		GuardiasItem response = guardiasService.resumenConfiguracionCola(guardiaTurno, request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/tarjetaIncompatibilidades")
	ResponseEntity<GuardiasDTO> tarjetaIncompatibilidades(HttpServletRequest request, @RequestParam String idGuardia,
			@RequestParam String idTurno) {
		GuardiasDTO response = guardiasService.tarjetaIncompatibilidades(idGuardia, idTurno, request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/getBaremos", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getBaremos(@RequestBody String idGuardia, HttpServletRequest request) {
		ComboDTO response = guardiasService.getBaremos(idGuardia, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/getCalendario", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosCalendarioItem> getCalendario(@RequestBody String idGuardia, HttpServletRequest request) {
		DatosCalendarioItem response = guardiasService.getCalendario(idGuardia, request);
		return new ResponseEntity<DatosCalendarioItem>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/getColaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionGuardiaDTO> getColaGuardia(@RequestBody GuardiasItem guardia,
			HttpServletRequest request) {
		InscripcionGuardiaDTO response = guardiasService.searchColaGuardia(guardia, request);
		return new ResponseEntity<InscripcionGuardiaDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/updateColaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> setColaGuardia(@RequestBody InscripcionGuardiaDTO guardiaBody,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.updateColaGuardia(guardiaBody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/gestionGuardia/ultimo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateUltimoCola(@RequestBody GuardiasItem guardia, HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.updateUltimoCola(guardia, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/gestionGuardia/updateColaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateColaGuardia(@RequestBody GuardiasItem guardia, HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.updateUltimoCola(guardia, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/gestionGuardia/resumenIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> resumenIncompatibilidades(@RequestBody GuardiasItem guardia,
			HttpServletRequest request) {
		GuardiasDTO response = guardiasService.resumenIncompatibilidades(guardia, request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/gestionGuardia/resumenTurno", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TurnosDTO> resumenTurno(@RequestBody String idTurno, HttpServletRequest request) {
		TurnosDTO response = guardiasService.resumenTurno(idTurno, request);
		return new ResponseEntity<TurnosDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/gestionGuardia/guardarCola", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarCola(@RequestBody List<InscripcionGuardiaItem> inscripciones,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.guardarColaGuardias(inscripciones, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/letradosGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LetradosGuardiaDTO> letradosGuardia(
			@RequestBody BusquedaLetradosGuardiaDTO busquedaLetradosGuardiaDTO, HttpServletRequest request) {
		LetradosGuardiaDTO response = guardiasService.letradosGuardia(busquedaLetradosGuardiaDTO.getIdTurno(),
				busquedaLetradosGuardiaDTO.getIdGuardia(), request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/buscarIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<IncompatibilidadesDTO> getIncompatibilidades(
			@RequestBody IncompatibilidadesDatosEntradaItem incompatibilidadesBody, HttpServletRequest request) {
		IncompatibilidadesDTO response = guardiasService.getIncompatibilidades(incompatibilidadesBody, request);
		return new ResponseEntity<IncompatibilidadesDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteIncompatibilidades(
			@RequestBody List<DeleteIncompatibilidadesDatosEntradaItem> deleteIncompatibilidadesBody,
			HttpServletRequest request) {
		DeleteResponseDTO response = guardiasService.deleteIncompatibilidades(deleteIncompatibilidadesBody, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> saveIncompatibilidades(
			@RequestBody List<SaveIncompatibilidadesDatosEntradaItem> incompatibilidadesBody,
			HttpServletRequest request) {
		DeleteResponseDTO response = guardiasService.saveIncompatibilidades(incompatibilidadesBody, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/getComboGuardiasInc", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboIncompatibilidadesResponse> getCombo(
			@RequestBody ComboIncompatibilidadesDatosEntradaItem comboIncompatibilidadesDatosEntradaItem,
			HttpServletRequest request) {
		ComboIncompatibilidadesResponse response = guardiasService.getCombo(comboIncompatibilidadesDatosEntradaItem,
				request);
		return new ResponseEntity<ComboIncompatibilidadesResponse>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/buscarCalendariosProgramados2", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<CalendariosProgDatosSalidaItem>> getCalendariosProgramados2(
			@RequestBody CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request) {
		List<CalendariosProgDatosSalidaItem> response = guardiasService.getCalendariosProg(calendarioProgBody, request);
		return new ResponseEntity<List<CalendariosProgDatosSalidaItem>>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/buscarLastCalendarioProgramado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosCalendarioProgramadoItem> getLastCalendarioProgramado(
			@RequestBody CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request) {
		DatosCalendarioProgramadoItem response = guardiasService.getLastCalendarioProgramado(calendarioProgBody,
				request);
		return new ResponseEntity<DatosCalendarioProgramadoItem>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/buscarCalendariosProgramados", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DatosCalendarioProgramadoItem>> getCalendarioProgramado(
			@RequestBody CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request) {
		List<DatosCalendarioProgramadoItem> response = guardiasService.getCalendarioProgramado(calendarioProgBody,
				request);
		return new ResponseEntity<List<DatosCalendarioProgramadoItem>>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getGuardiasCalendario", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<GuardiaCalendarioItem>> getGuardiasCalendario(@RequestParam("idCalendar") String idCalendar,
			@RequestParam("fechaDesde") String fechaDesde, @RequestParam("fechaHasta") String fechaHasta,
			HttpServletRequest request) {
		List<GuardiaCalendarioItem> response = guardiasService.getGuardiasFromCalendar(idCalendar, fechaDesde,
				fechaHasta, request);
		return new ResponseEntity<List<GuardiaCalendarioItem>>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getFechasProgramacionGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<RangoFechasItem>> getFechasProgramacionGuardia(String idGuardia, HttpServletRequest request) {
		List<RangoFechasItem> response = guardiasService.getFechasProgramacionGuardia(idGuardia, request);
		return new ResponseEntity<List<RangoFechasItem>>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarCalendariosProgramados", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteCalendariosProgramados(
			@RequestBody List<DeleteCalendariosProgDatosEntradaItem> deleteCalBody, HttpServletRequest request) {
		DeleteResponseDTO response = guardiasService.deleteCalendariosProgramados(deleteCalBody, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/subirDocumentoActDesigna", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<InsertResponseDTO> subirDocumentoActDesigna(MultipartHttpServletRequest request) {
		InsertResponseDTO response = guardiasService.subirDocumentoActDesigna(request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/getDocumentosPorActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentoActDesignaDTO> getDocumentosPorActDesigna(
			@RequestBody DocumentoActDesignaItem documentoActDesignaItem, HttpServletRequest request) {
		DocumentoActDesignaDTO response = guardiasService.getDocumentosPorActDesigna(documentoActDesignaItem, request);
		return new ResponseEntity<DocumentoActDesignaDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/descargarDocumentosActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosActDesigna(
			@RequestBody List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = guardiasService
				.descargarDocumentosActDesigna(listaDocumentoActDesignaItem, request);
		return response;
	}

	@PostMapping(value = "/designas/eliminarDocumentosActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarDocumentosActDesigna(
			@RequestBody List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request) {
		DeleteResponseDTO response = guardiasService.eliminarDocumentosActDesigna(listaDocumentoActDesignaItem,
				request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/guardiasFromIdConjunto")
	public ResponseEntity<List<GuardiaCalendarioItem>> guardiasFromCojunto(HttpServletRequest request,
			String idConjunto) {
		List<GuardiaCalendarioItem> response = guardiasService.guardiasFromCojunto(request, idConjunto);
		return new ResponseEntity<List<GuardiaCalendarioItem>>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/addGuardiaConjunto", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertGuardiaToConjunto(
			@RequestBody List<GuardiaCalendarioItem> guardiaCalendarioItemList, @RequestParam String idConjuntoGuardia, @RequestParam String idTurno,
			@RequestParam String idGuardia, @RequestParam String fechaDesde, @RequestParam String fechaHasta,
			HttpServletRequest request) {
		InsertResponseDTO response = guardiasService.insertGuardiaToConjunto(request, idConjuntoGuardia, idTurno, idGuardia, fechaDesde, fechaHasta,
				guardiaCalendarioItemList);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/addGuardiaCalendario", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertGuardiaToCalendar(
			@RequestBody List<GuardiaCalendarioItem> guardiaCalendarioItemList, Boolean update, String idCalendar,
			HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = guardiasService.insertGuardiaToCalendar(update, request, idCalendar,
					guardiaCalendarioItemList);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			if(e.getMessage() == "messages.factSJCS.error.solapamientoRango") {
				Error error = new Error();
				response.setError(error);
				error.setCode(400);
				error.setDescription("messages.factSJCS.error.solapamientoRango");
				error.setMessage(e.getMessage());
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.CONFLICT);
			}else {
				LOGGER.error(
						"insertGuardiaToCalendar() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
						e);

				Error error = new Error();
				response.setError(error);
				error.setCode(500);
				error.setDescription("general.mensaje.error.bbdd");
				error.setMessage(e.getMessage());
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		
		
	}

	@PostMapping(value = "/deleteGuardiaCalendario", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> deleteGuardiaFromCalendar(
			@RequestBody List<GuardiaCalendarioItem> guardiaCalendarioItemList, String idCalendar,
			HttpServletRequest request) {
		InsertResponseDTO response = guardiasService.deleteGuardiaFromCalendar(request, idCalendar,
				guardiaCalendarioItemList);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/descargarLogCalendarioProgramado", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> descargarLogCalendarioProgramado(@RequestBody DatosCalendarioProgramadoItem item, HttpServletRequest request) throws Exception {
		return guardiasService.descargarLogCalendarioProgramado(item, request);

	}
	
	@PostMapping(value = "/updateCalendarioProgramado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> updateCalendarioProgramado(
			@RequestBody DatosCalendarioProgramadoItem calendarioItem, HttpServletRequest request) {
		InsertResponseDTO response = guardiasService.updateCalendarioProgramado(request, calendarioItem);
		if (response.getStatus() == "OK") {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} else if (response.getStatus() == "ERRORASOCIADAS") {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/newCalendarioProgramado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> newCalendarioProgramado(@RequestBody DatosCalendarioProgramadoItem calendarioItem,
			HttpServletRequest request) {
		InsertResponseDTO response = guardiasService.newCalendarioProgramado(request, calendarioItem);

		if (response.getStatus() == "OK") {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} else if (response.getStatus() == "ERRORASOCIADAS") {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/generarCalendario", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> generarCalendario(@RequestBody DatosCalendarioProgramadoItem programacionItem,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO response = guardiasService.generarCalendario(request, programacionItem);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json"));
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, headers, HttpStatus.CONFLICT);
	}

	@PostMapping(value = "/descargarZipExcelLog", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarZIPExcelLog(
			@RequestBody List<DatosCalendarioyProgramacionItem> programacionItemList, HttpServletRequest request) {
		ByteArrayInputStream response = guardiasService.descargarZIPExcelLog(request, programacionItemList);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		headers.add("Content-Disposition", String.format("inline; filename=%s", "GeneracionCalendariosLog_ZIP"));
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		if (response != null) {
			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(response));
		} else {
			return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping(value = "/descargarExcelLog", produces =
	 * MediaType.APPLICATION_JSON_VALUE) ResponseEntity<DatosDocumentoItem>
	 * descargarExcelLog( HttpServletRequest request) { DatosDocumentoItem response
	 * = guardiasService.descargarExcelLog(request); return new
	 * ResponseEntity<DatosDocumentoItem>(response, HttpStatus.OK); }
	 */

	@RequestMapping(value = "/descargarExcelLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> descargarExcelLog(
			@RequestBody DatosCalendarioyProgramacionItem calyProgItem, HttpServletRequest request)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		DatosDocumentoItem response = guardiasService.descargarExcelLog(request, calyProgItem);
		ByteArrayInputStream bis = new ByteArrayInputStream(response.getDatos());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
		headers.add("Content-Disposition", String.format("inline; filename=%s", response.getFileName()));
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
//		CorsFilter corsFilter = new CorsFilter();
//	    corsFilter.setAllowedHeaders("Content-Type, Access-Control-Allow-Headers, Access-Control-Expose-Headers, Content-Disposition, Authorization, X-Requested-With");
//	    corsFilter.setExposedHeaders("Content-Disposition");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
	}

//	@GetMapping(value = "/descargarExcelLog",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) 
//    ResponseEntity<Response> test2(HttpServletRequest request) {  
//		DatosDocumentoItem response = guardiasService.descargarExcelLog(request);
//        File file = new File(response.getPathDocumento() + response.getFileName());  
//        ResponseBuilder rb = Response.ok(file);  
//        rb.header("content-disposition", "attachment; filename=" + response.getFileName());  
//        return new ResponseEntity<Response>(rb.build(), HttpStatus.OK); 
//    }  

	@PostMapping(value = "/busquedainscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionesResponseDTO> getInscripciones(@RequestBody InscripcionDatosEntradaDTO inscripcionesBody,
			HttpServletRequest request) {
		InscripcionesResponseDTO response = guardiasService.getInscripciones(inscripcionesBody, request);
		return new ResponseEntity<InscripcionesResponseDTO>(response, HttpStatus.OK);
	}

//	@PostMapping(value = "/turnoconbajas", produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getTurnoconBajas(@RequestBody DeleteIncompatibilidadesDatosEntradaItem deleteIncompatibilidadesBody, HttpServletRequest request){
//		DeleteResponseDTO response= guardiasService.deleteIncompatibilidades(deleteIncompatibilidadesBody, request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}

	@PostMapping(value = "/validarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> validarInscripciones(@RequestBody BusquedaInscripcionItem validarbody,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.validarInscripciones(validarbody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/denegarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denegarInscripciones(@RequestBody BusquedaInscripcionItem denegarbody,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.denegarInscripciones(denegarbody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/validarSolicitudGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> validarSolicitudGuardia(@RequestBody GuardiasItem guardiasItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.validarSolicitudGuardia(guardiasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/desvalidarGuardiaColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> desvalidarGuardiaColegiado(@RequestBody GuardiasItem guardiasItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = guardiasService.desvalidarGuardiaColegiado(guardiasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/eliminarGuardiaColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarGuardiaColegiado(@RequestBody GuardiasItem guardiasItem,
			HttpServletRequest request) {
		DeleteResponseDTO response = guardiasService.eliminarGuardiaColegiado(guardiasItem, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping({ "/searchTipoDiaGuardia" })
	ResponseEntity<StringDTO> getTipoDiaGuardia(HttpServletRequest request, @RequestParam String idTurno,
			@RequestParam String idGuardia) {
		StringDTO response = this.guardiasService.getTipoDiaGuardia(request, idTurno, idGuardia);
		return new ResponseEntity(response, HttpStatus.OK);
	}

//	@PostMapping(value = "/solicitarBajaInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<SolicitarBajaInscripcionResponseDTO> solicitarBajaInscripcion(@RequestBody List<SolicitarBajaInscripcionDatosEntradaItem> solicitarbajabody, HttpServletRequest request){
//		SolicitarBajaInscripcionResponseDTO response= guardiasService.solicitarBajaInscripcion(solicitarbajabody, request);
//		return new ResponseEntity<SolicitarBajaInscripcionResponseDTO>(response, HttpStatus.OK);
//	}
//	
//	@PostMapping(value = "/cambiarFechaInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<CambiarFechainscripcionResponseDTO> cambiarFechaInscripcion(@RequestBody List<CambiarFechaInscripcionDatosEntradaItem> cambiarfechabody, HttpServletRequest request){
//		CambiarFechainscripcionResponseDTO response= guardiasService.cambiarFechaInscripcion(cambiarfechabody, request);
//		return new ResponseEntity<CambiarFechainscripcionResponseDTO>(response, HttpStatus.OK);
//	}
}