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
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesResponseDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.scs.services.guardia.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardias/inscripciones")
public class InscripcionController {
	
	@Autowired
	private InscripcionService inscripcionService;
	
	@PostMapping(value = "/validarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)	
	ResponseEntity<UpdateResponseDTO> validarInscripciones(@RequestBody List<BusquedaInscripcionMod> validarbody, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.validarInscripciones(validarbody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/denegarInscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> denegarInscripciones(@RequestBody List<BusquedaInscripcionMod> denegarbody, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.denegarInscripciones(denegarbody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/solicitarBajaInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> solicitarBajaInscripcion(@RequestBody List<BusquedaInscripcionMod> solicitarbajabody, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.solicitarBajaInscripcion(solicitarbajabody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/cambiarFechaInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cambiarFechaInscripcion(@RequestBody List<BusquedaInscripcionMod> cambiarfechabody, HttpServletRequest request){
		UpdateResponseDTO response= inscripcionService.cambiarFechaInscripcion(cambiarfechabody, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/buscarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Boolean> buscarSaltosCompensaciones(@RequestBody List<BusquedaInscripcionMod> buscarSaltosCompensaciones, HttpServletRequest request){
		Boolean response= inscripcionService.buscarSaltosCompensaciones(buscarSaltosCompensaciones, request);
        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarSaltosCompensaciones(@RequestBody List<BusquedaInscripcionMod> eliminarSaltosCompensaciones, HttpServletRequest request){
		DeleteResponseDTO response= inscripcionService.eliminarSaltosCompensaciones(eliminarSaltosCompensaciones, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
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
	public ResponseEntity<InsertResponseDTO> insertarInscripciones(@RequestBody InscripcionGuardiaItem inscripcion,
			HttpServletRequest request) {
		InsertResponseDTO response = inscripcionService.insertarInscripciones(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/historicoInscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HistoricoInscripcionDTO> historicoInscripcion(@RequestBody InscripcionGuardiaItem inscripcion,
			HttpServletRequest request) {
		HistoricoInscripcionDTO response = inscripcionService.historicoInscripcion(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/inscripcionesDisponibles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InscripcionesDisponiblesDTO> inscripcionesDisponibles(@RequestBody InscripcionGuardiaItem inscripcion,
			HttpServletRequest request) {
		InscripcionesDisponiblesDTO response = inscripcionService.inscripcionesDisponibles(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/inscripcionPorguardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InscripcionesDisponiblesDTO> inscripcionPorguardia(@RequestBody InscripcionGuardiaItem inscripcion,
			HttpServletRequest request) {
		InscripcionesDisponiblesDTO response = inscripcionService.inscripcionPorguardia(inscripcion, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@GetMapping(value = "/inscripcionesCombo", produces = MediaType.APPLICATION_JSON_VALUE, params = {"idGuardia"})
	public ResponseEntity<ComboDTO> comboInscripciones(HttpServletRequest request, @RequestParam String idGuardia) {
		ComboDTO comboLetrados = inscripcionService.comboLetrados(request, idGuardia);
		return new ResponseEntity<>(comboLetrados, HttpStatus.OK);

		
	}
}
