package org.itcgae.siga.scs.controllers.guardia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.scs.services.guardia.GuardiasColegiadoService;

@RestController
@RequestMapping(value = "/guardiaColegiado")
public class GuardiasColegiadoController {
	
	@Autowired
	GuardiasColegiadoService guardiasColegiadoService;
	
	
	@PostMapping(value = "/getGuardiaColeg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> getGuardiaColeg(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request) {
		GuardiasDTO response = guardiasColegiadoService.getGuardiaColeg(guardiasItem, request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getTurnoGuardiaColeg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TurnosDTO> getTurnoGuardiaColeg(@RequestBody TurnosItem turnosItem, HttpServletRequest request) {
		TurnosDTO response = guardiasColegiadoService.getTurnoGuardiaColeg(turnosItem, request);
		return new ResponseEntity<TurnosDTO>(response, HttpStatus.OK);
	}
	

	
	@PostMapping(value = "/getColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> getColegiado(@RequestBody ColegiadoItem guardiasItem, HttpServletRequest request) {
		ColegiadoDTO response = guardiasColegiadoService.getColegiado(guardiasItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getIdConjuntoGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> getIdConjuntoGuardia(@RequestBody String idGuardia, HttpServletRequest request) {
		String response = guardiasColegiadoService.getIdConjuntoGuardia(idGuardia, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/updateGuardiaColeg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGuardiaColeg(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request) {
		UpdateResponseDTO response = guardiasColegiadoService.updateGuardiaColeg(guardiasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/insertGuardiaColeg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertGuardiaColeg(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request) {
		InsertResponseDTO response = guardiasColegiadoService.insertGuardiaColeg(guardiasItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sustituirGuardiaColeg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> sustituirGuardiaColeg(@RequestBody String[] datos, HttpServletRequest request) {
		UpdateResponseDTO response = guardiasColegiadoService.sustituirGuardiaColeg(datos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getCalendarioColeg", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DatosCalendarioItem>> getCalendarioColeg(@RequestBody String[] datosCalendarioItem, HttpServletRequest request) {
		List<DatosCalendarioItem> response = guardiasColegiadoService.getCalendarioColeg(datosCalendarioItem, request);
		return new ResponseEntity<List<DatosCalendarioItem>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getPermutasColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermutaDTO> getPermutasColegiado(@RequestBody PermutaItem permutaItem, HttpServletRequest request) {
		PermutaDTO response = guardiasColegiadoService.getPemutasColeg(permutaItem, request);
		return new ResponseEntity<PermutaDTO>(response, HttpStatus.OK);
	}
}
