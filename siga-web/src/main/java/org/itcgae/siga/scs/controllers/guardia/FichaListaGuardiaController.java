package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.GuardiasinputItem;
import org.itcgae.siga.scs.services.guardia.FichaListaGuardiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardias")
public class FichaListaGuardiaController {
	
	@Autowired
	private FichaListaGuardiaService fichaListaService;

	@PostMapping(value = "/detalleListaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> detalleListaGuardia(@RequestBody GuardiasinputItem guardias, HttpServletRequest request) {
		fichaListaService.detalleListaGuardia(guardias, request);
		return null;
	}
	
	@PostMapping(value = "/guardarListaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> guardarListaGuardia(@RequestBody GuardiasinputItem guardias, HttpServletRequest request) {
		DeleteResponseDTO response = fichaListaService.guardarListaGuardia(guardias, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardiasEnLista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> guardiasEnLista(@RequestBody GuardiasinputItem guardias, HttpServletRequest request) {
		fichaListaService.guardiasEnlista(guardias, request);
		return null;
	}
	
	@PostMapping(value = "/comboGuardiasDias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> comboGuardiasDias(@RequestBody GuardiasinputItem guardias, HttpServletRequest request) {
		fichaListaService.comboGuardiasDias(guardias, request);
		return null;
	}
	
	@PostMapping(value = "/guardarGuardiasEnLista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> guardarGuardiasEnLista(@RequestBody GuardiasinputItem guardias, HttpServletRequest request) {
		DeleteResponseDTO response = fichaListaService.guardarGuardiasEnLista(guardias, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/borrarGuardiasEnLista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> borrarGuardiasEnLista(@RequestBody GuardiasinputItem guardias, HttpServletRequest request) {
		DeleteResponseDTO response = fichaListaService.borrarGuardiasEnLista(guardias, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
