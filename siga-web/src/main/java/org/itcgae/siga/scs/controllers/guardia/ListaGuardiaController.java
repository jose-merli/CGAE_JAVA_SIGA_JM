package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.scs.services.guardia.ListaGuardiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/listaguardias")
public class ListaGuardiaController {

	@Autowired
	private ListaGuardiaService fichaListaService;

	@PostMapping(value = "/buscarListaGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListaGuardiasDTO> searchListaGuardias(HttpServletRequest request, @RequestBody ListaGuardiasItem filtro) {
		ListaGuardiasDTO response = null;
		try {
			response = fichaListaService.searchListaGuardias(request, filtro);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ListaGuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = {"/guardarListaGuardias"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> saveListaGuardias(HttpServletRequest request, @RequestBody ListaGuardiasItem lista) {
		InsertResponseDTO response = null;
		try {
			response = fichaListaService.saveListaGuardias(request, lista);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping({"/searchGuardiasFromLista"})
	public ResponseEntity<GuardiasDTO> getGuardiasFromLista(HttpServletRequest request, @RequestParam String idLista) {
		GuardiasDTO response = null;
		try {
			response = fichaListaService.getGuardiasFromLista(request, idLista);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = {"/guardarGuardiasEnLista"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> saveGuardiasEnLista(HttpServletRequest request, @RequestBody List<GuardiasItem> guardias) {
		UpdateResponseDTO response = null;
		try {
			response = fichaListaService.saveGuardiasEnLista(request, guardias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = {"/eliminarGuardiasFromLista"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> deleteGuardiasFromLista(HttpServletRequest request, @RequestBody java.util.List<GuardiasItem> guardias) {
		DeleteResponseDTO response = null;
		try {
			response = fichaListaService.deleteGuardiasFromLista(request, guardias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

}
