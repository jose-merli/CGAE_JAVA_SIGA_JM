package org.itcgae.siga.scs.controllers.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionMod;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesResponseDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.services.guardia.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardias/inscripciones")
public class InscripcionController {
	
	@Autowired
	private InscripcionService inscripcionService;
	
	@PostMapping(value = "/validarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)	
	ResponseEntity<UpdateResponseDTO> validarInscripciones(@RequestBody List<BusquedaInscripcionMod> validarbody, HttpServletRequest request) throws Exception{
		UpdateResponseDTO response= inscripcionService.validarInscripciones(validarbody, request);
		if (response != null) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	
	}

	@PostMapping(value = "/denegarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denegarInscripciones(@RequestBody List<BusquedaInscripcionMod> denegarbody, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.denegarInscripcion(denegarbody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/solicitarBajaInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> solicitarBajaInscripcion(@RequestBody List<BusquedaInscripcionMod> solicitarbajabody, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.solicitarBajaInscripcion(solicitarbajabody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/cambiarFechaInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cambiarFechaInscripcion(@RequestBody List<BusquedaInscripcionMod> cambiarfechabody, HttpServletRequest request){
		UpdateResponseDTO response= null;
		try {
			response= inscripcionService.cambiarFechaInscripcion(cambiarfechabody, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/buscarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> buscarSaltosCompensaciones(@RequestBody List<BusquedaInscripcionMod> buscarSaltosCompensaciones, HttpServletRequest request){
		Boolean response= inscripcionService.buscarSaltosCompensaciones(buscarSaltosCompensaciones, request);
        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarSaltosCompensaciones(@RequestBody List<BusquedaInscripcionMod> eliminarSaltosCompensaciones, HttpServletRequest request) throws Exception{
		DeleteResponseDTO response= inscripcionService.eliminarSaltosCompensaciones(eliminarSaltosCompensaciones, request);
		if (response.getStatus() == "OK") {
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.CONFLICT);
		}
			}
	
	
	@PostMapping(value = "/buscarTrabajosSJCS", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> buscarTrabajosSJCS(@RequestBody List<BusquedaInscripcionMod> buscarTrabajosSJCS, HttpServletRequest request){
		Boolean response= inscripcionService.buscarTrabajosSJCS(buscarTrabajosSJCS, request);
        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}

	
	@PostMapping(value = "/buscarGuardiasAsocTurnos", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> buscarGuardiasAsocTurnos(@RequestBody List<BusquedaInscripcionMod> buscarGuardiasAsocTurnos, HttpServletRequest request){
		Boolean response= inscripcionService.buscarGuardiasAsocTurnos(buscarGuardiasAsocTurnos, request);
        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/insertarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> insertarInscripciones(@RequestBody BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		InsertResponseDTO response = inscripcionService.insertarInscripciones(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/historicoInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HistoricoInscripcionDTO> historicoInscripcion(@RequestBody BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		HistoricoInscripcionDTO response = inscripcionService.historicoInscripcion(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/inscripcionesDisponibles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InscripcionesDisponiblesDTO> inscripcionesDisponibles(@RequestBody BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		InscripcionesDisponiblesDTO response = inscripcionService.inscripcionesDisponibles(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PostMapping(value = "/inscripcionesDisponiblesGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InscripcionesDisponiblesDTO> inscripcionesDisponiblesGuardia(@RequestBody BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		InscripcionesDisponiblesDTO response = inscripcionService.inscripcionesDisponiblesGuardia(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/inscripcionPorguardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InscripcionesDisponiblesDTO> inscripcionPorguardia(@RequestBody BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		InscripcionesDisponiblesDTO response = inscripcionService.inscripcionPorguardia(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PostMapping(value = "/inscripcionesCombo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComboDTO> comboInscripciones(HttpServletRequest request, @RequestBody String idGuardia) {
		ComboDTO comboLetrados = inscripcionService.comboLetrados(request, idGuardia);
		return new ResponseEntity<>(comboLetrados, HttpStatus.OK);

		
	}
	
	@PostMapping(value = "/updateInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateInscripcion(@RequestBody BusquedaInscripcionMod updateInscripcion, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.updateInscripcion(updateInscripcion, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/insertSolicitarAlta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertSolicitarAlta(@RequestBody InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		InsertResponseDTO response = inscripcionService.insertSolicitarAlta(inscripcionesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
