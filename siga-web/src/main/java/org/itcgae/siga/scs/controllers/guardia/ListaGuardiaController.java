package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.scs.services.guardia.ListaGuardiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
