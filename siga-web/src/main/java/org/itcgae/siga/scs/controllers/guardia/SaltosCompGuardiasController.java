package org.itcgae.siga.scs.controllers.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.BusquedaLetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.scs.services.guardia.SaltosCompGuardiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardia/saltosCompensaciones")
public class SaltosCompGuardiasController {

	@Autowired
	SaltosCompGuardiasService saltosCompGuardiasService;

	@PostMapping(value = "/buscarSaltosOCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaltoCompGuardiaDTO> searchSaltosYCompensaciones(@RequestBody SaltoCompGuardiaItem saltoItem,
			HttpServletRequest request) {
		SaltoCompGuardiaDTO response = saltosCompGuardiasService.searchSaltosYCompensaciones(saltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> guardarSaltosCompensaciones(
			@RequestBody List<SaltoCompGuardiaItem> listaSaltoItem, HttpServletRequest request) {
		DeleteResponseDTO response = saltosCompGuardiasService.guardarSaltosCompensaciones(listaSaltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/borrarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> borrarSaltosCompensaciones(
			@RequestBody List<SaltoCompGuardiaItem> listaSaltoItem, HttpServletRequest request) {
		DeleteResponseDTO response = saltosCompGuardiasService.borrarSaltosCompensaciones(listaSaltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/anularSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> anularSaltosCompensaciones(
			@RequestBody List<SaltoCompGuardiaItem> listaSaltoItem, HttpServletRequest request) {
		DeleteResponseDTO response = saltosCompGuardiasService.anularSaltosCompensaciones(listaSaltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/isGrupo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> isGrupo(
			@RequestBody BusquedaLetradosGuardiaDTO letradoGuardiaItem, HttpServletRequest request) {
		String grupo = saltosCompGuardiasService.isGrupo(letradoGuardiaItem);
		return new ResponseEntity<>(grupo, HttpStatus.OK);
	}
}
