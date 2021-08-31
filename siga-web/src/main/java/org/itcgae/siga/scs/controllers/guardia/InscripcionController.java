package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
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
