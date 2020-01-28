package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value = "/guardia")
public class SaltosCompGuardiasController {
	
	@Autowired
	SaltosCompGuardiasService saltosCompGuardiasService;

	@PostMapping(value = "/busquedaSaltosCompG/searchSaltosYCompensaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<SaltoCompGuardiaDTO> searchSaltosYCompensaciones(@RequestBody SaltoCompGuardiaItem saltoItem, HttpServletRequest request){
		SaltoCompGuardiaDTO response= saltosCompGuardiasService.searchSaltosYCompensaciones(saltoItem,request);
		return new ResponseEntity<SaltoCompGuardiaDTO>(response, HttpStatus.OK);
	}
	
}
