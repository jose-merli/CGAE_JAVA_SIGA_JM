
package org.itcgae.siga.scs.controllers.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesResponse;
import org.itcgae.siga.DTOs.scs.BusquedaLetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosSalidaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.DeleteCalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DeleteIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDTO;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.LetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaveIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardia")
public class GuardiaController {

	@Autowired
	GuardiasService guardiasService;

	@PostMapping(value = "/busquedaGuardia/searchGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> searchGuardias(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request) {
		GuardiasDTO response = guardiasService.searchGuardias(guardiasItem, request);
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
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
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

	@PostMapping(value = "/busquedaGuardia/tarjetaIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> tarjetaIncompatibilidades(@RequestBody String idGuardia, HttpServletRequest request) {
		GuardiasDTO response = guardiasService.tarjetaIncompatibilidades(idGuardia, request);
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
	ResponseEntity<UpdateResponseDTO> setColaGuardia(@RequestBody InscripcionGuardiaDTO guardiaBody, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.updateColaGuardia(guardiaBody, request);
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
	ResponseEntity<IncompatibilidadesDTO> getIncompatibilidades(@RequestBody IncompatibilidadesDatosEntradaItem incompatibilidadesBody, HttpServletRequest request){
		IncompatibilidadesDTO response= guardiasService.getIncompatibilidades(incompatibilidadesBody, request);
		return new ResponseEntity<IncompatibilidadesDTO>(response, HttpStatus.OK);
	}
	@PostMapping(value = "/eliminarIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteIncompatibilidades(@RequestBody DeleteIncompatibilidadesDatosEntradaItem deleteIncompatibilidadesBody, HttpServletRequest request){
		DeleteResponseDTO response= guardiasService.deleteIncompatibilidades(deleteIncompatibilidadesBody, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	@PostMapping(value = "/guardarIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> saveIncompatibilidades(@RequestBody SaveIncompatibilidadesDatosEntradaItem incompatibilidadesBody, HttpServletRequest request){
		DeleteResponseDTO response= guardiasService.saveIncompatibilidades(incompatibilidadesBody, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getComboGuardiasInc", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboIncompatibilidadesResponse> getCombo(@RequestBody ComboIncompatibilidadesDatosEntradaItem comboIncompatibilidadesDatosEntradaItem, HttpServletRequest request){
		ComboIncompatibilidadesResponse response= guardiasService.getCombo(comboIncompatibilidadesDatosEntradaItem, request);
		return new ResponseEntity<ComboIncompatibilidadesResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/buscarCalendariosProgramados2", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<CalendariosProgDatosSalidaItem>> getCalendariosProgramados2(@RequestBody CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request){
		List<CalendariosProgDatosSalidaItem> response= guardiasService.getCalendariosProg(calendarioProgBody, request);
		return new ResponseEntity<List<CalendariosProgDatosSalidaItem>>(response, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/buscarCalendariosProgramados", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DatosCalendarioProgramadoItem>> getCalendarioProgramado(@RequestBody CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request) {
		List<DatosCalendarioProgramadoItem> response = guardiasService.getCalendarioProgramado(calendarioProgBody, request);
		return new ResponseEntity<List<DatosCalendarioProgramadoItem>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarCalendariosProgramados", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteCalendariosProgramados(@RequestBody DeleteCalendariosProgDatosEntradaItem deleteCalBody, HttpServletRequest request){
		DeleteResponseDTO response= guardiasService.deleteCalendariosProgramados(deleteCalBody, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
}