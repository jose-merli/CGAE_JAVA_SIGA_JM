package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardia")
public class GuardiaController {

	@Autowired
	private ComboService comboService;

	@GetMapping(value = "/busquedaGuardia/comboZonas", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboZonas(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboSubzonas", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboSubzonas(String zona, HttpServletRequest request) {
		ComboDTO response = comboService.getComboSubZonas(request, zona);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboJurisdicciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboJurisdicciones(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboGruposFacturacion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboGruposFacturacion(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboPartidasPresupuestarias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboPartidasPresupuestarias(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}