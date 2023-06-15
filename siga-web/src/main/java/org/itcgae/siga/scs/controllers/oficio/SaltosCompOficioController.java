package org.itcgae.siga.scs.controllers.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.scs.services.oficio.ISaltosCompOficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oficio/saltosCompensaciones")
public class SaltosCompOficioController {

	@Autowired
	private ISaltosCompOficioService saltosCompOficioService;

	@PostMapping(value = "/buscarSaltosOCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaltoCompGuardiaDTO> searchSaltosYCompensacionesOficio(
			@RequestBody SaltoCompGuardiaItem saltoItem, HttpServletRequest request) {
		SaltoCompGuardiaDTO response = saltosCompOficioService.searchSaltosYCompensaciones(saltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> guardarSaltosCompensaciones(
			@RequestBody List<SaltoCompGuardiaItem> listaSaltoItem, HttpServletRequest request) {
		DeleteResponseDTO response = saltosCompOficioService.guardarSaltosCompensaciones(listaSaltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/anularSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> anularSaltosCompensaciones(
			@RequestBody List<SaltoCompGuardiaItem> listaSaltoItem, HttpServletRequest request) {
		DeleteResponseDTO response = saltosCompOficioService.anularSaltosCompensaciones(listaSaltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/borrarSaltosCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> borrarSaltosCompensaciones(
			@RequestBody List<SaltoCompGuardiaItem> listaSaltoItem, HttpServletRequest request) {
		DeleteResponseDTO response = saltosCompOficioService.borrarSaltosCompensaciones(listaSaltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/searchLetradosTurno", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComboDTO> searchLetradosTurno(@RequestBody SaltoCompGuardiaItem saltoItem,
			HttpServletRequest request) {
		ComboDTO response = saltosCompOficioService.searchLetradosTurno(saltoItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
