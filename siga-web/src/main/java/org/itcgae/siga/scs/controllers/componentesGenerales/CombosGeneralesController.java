package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/combo")
public class CombosGeneralesController {
	
	@Autowired
	private ComboService comboService;
	
	
	@GetMapping("/materias")
	public ResponseEntity<ComboDTO> comboMaterias(String area, String dataFilter, HttpServletRequest request) {
		ComboDTO response = comboService.comboMaterias(request, area, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/areas")
	public ResponseEntity<ComboDTO> comboAreas(HttpServletRequest request) {
		ComboDTO response = comboService.comboAreas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/tipoGuardia")
	public ResponseEntity<ComboDTO> comboTipoGuardia(HttpServletRequest request) {
		ComboDTO response = comboService.comboTiposGuardia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/tipoTurno")
	public ResponseEntity<ComboDTO> comboTipoTurno(HttpServletRequest request) {
		ComboDTO response = comboService.comboTiposTurno(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/turnos")
	public ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTurnos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/grupoFacturacion")
	public ResponseEntity<ComboDTO> comboGrupoFacturacion(HttpServletRequest request) {
		ComboDTO response = comboService.getComboGrupoFacturacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/partidasPresupuestarias")
	public ResponseEntity<ComboDTO> comboPartidasPresupuestarias(HttpServletRequest request) {
		ComboDTO response = comboService.getComboPartidasPresupuestarias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/subzonas")
	public ResponseEntity<ComboDTO> comboSubZona(String zona, HttpServletRequest request) {
		ComboDTO response = comboService.getComboSubZonas(request, zona);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/zonas")
	public ResponseEntity<ComboDTO> comboZonas(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/jurisdicciones")
	public ResponseEntity<ComboDTO> combo(HttpServletRequest request) {
		ComboDTO response = comboService.getJurisdicciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	

}
