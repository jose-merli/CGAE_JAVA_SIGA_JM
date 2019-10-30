
package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.guardia.GuardiasDTO;
import org.itcgae.siga.DTO.scs.guardia.GuardiasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.service.guardia.GuardiasService;
import org.itcgae.siga.services.ComboService;
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
	ComboService comboService;

	@Autowired
	GuardiasService guardiasService;
	
	@PostMapping(value = "/busquedaGuardia/searchGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> searchGuardias(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request){
		GuardiasDTO response= guardiasService.searchGuardias(guardiasItem,request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaGuardia/deleteGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.deleteGuardias(guardiasDTO,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/activateGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.activateGuardias(guardiasDTO,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
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
		ComboDTO response = comboService.getJurisdicciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboGruposFacturacion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboGruposFacturacion(HttpServletRequest request) {
		ComboDTO response = comboService.getComboGrupoFacturacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboPartidasPresupuestarias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboPartidasPresupuestarias(HttpServletRequest request) {
		ComboDTO response = comboService.getComboPartidasPresupuestarias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/busquedaGuardia/comboAreas", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboAreas(HttpServletRequest request) {
		ComboDTO response = comboService.comboAreas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/busquedaGuardia/comboMaterias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboMaterias(String area,String filtro,HttpServletRequest request) {
		ComboDTO response = comboService.comboMaterias(request, area, filtro);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/busquedaGuardia/comboTiposGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTiposGuardia(HttpServletRequest request) {
		ComboDTO response = comboService.comboTiposGuardia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/busquedaGuardia/comboTiposTurno", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTiposTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTiposTurno(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/busquedaGuardia/comboTurnos", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTurnos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}