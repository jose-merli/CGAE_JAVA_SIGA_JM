package org.itcgae.siga.scs.controllers.guardia;

import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasDTO;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesResponse;
import org.itcgae.siga.DTOs.scs.DatosCalendarioDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
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
	ResponseEntity<UpdateResponseDTO> sustituirGuardiaColeg(@RequestBody String[] datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = guardiasColegiadoService.sustituirGuardiaColeg(datos, request);
		if (response != null) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/existeFacturacionGuardiaColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> existeFacturacionGuardiaColegiado(@RequestBody String[] datos, HttpServletRequest request) throws Exception {
		ResponseDataDTO response = guardiasColegiadoService.existeFacturacionGuardiaColegiado(datos, request);
		if (response != null) {
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/existeAsistenciasGuardiaColegiado", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> existeAsistenciasGuardiaColegiado(@RequestBody String[] datos, HttpServletRequest request) throws Exception {
		ResponseDataDTO response = guardiasColegiadoService.existeAsistenciasGuardiaColegiado(datos, request);
		if (response != null) {
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@GetMapping(value = "/getTurnoInscrito")
	ResponseEntity<ComboDTO> getTurnoInscrito(String idPersona, HttpServletRequest request){
		ComboDTO response= guardiasColegiadoService.getTurnoInscrito(idPersona, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getGuardiaDestinoInscrito", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboGuardiasFuturasDTO> getGuardiaDestinoInscrito(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request){
		ComboGuardiasFuturasDTO response= guardiasColegiadoService.getGuardiaDestinoInscrito(guardiaItem, request);
		return new ResponseEntity<ComboGuardiasFuturasDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/permutarGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> permutarGuardia(@RequestBody PermutaItem permutaItem, HttpServletRequest request) throws Exception{
		InsertResponseDTO response= guardiasColegiadoService.permutarGuardia(permutaItem, request);
		if (response != null) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/validarPermuta", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> validarPermuta(@RequestBody List<PermutaItem> permutas, HttpServletRequest request) throws Exception{
		UpdateResponseDTO response= guardiasColegiadoService.validarPermuta(permutas, request);
		if (response != null) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
