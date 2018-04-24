package org.itcgae.siga.adm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
import org.itcgae.siga.DTOs.adm.AdmContadorUpdateDTO;
import org.itcgae.siga.DTOs.adm.ContadorDTO;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IContadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContadoresController {

	@Autowired
	private IContadoresService contadoresService;
	
	@RequestMapping(value = "/contadores/module", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getContadorModules() {
		ComboDTO response = contadoresService.getContadorModules();
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/contadores/mode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getContadorMode(HttpServletRequest request) {
		ComboDTO response = contadoresService.getContadorMode(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/contadores/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ContadorDTO> getContadorSearch(@RequestParam("numPagina") int numPagina, @RequestBody ContadorRequestDTO contadorRequestDTO, HttpServletRequest request) { 
		ContadorDTO response = contadoresService.getContadorSearch(numPagina, contadorRequestDTO, request);
		return new ResponseEntity<ContadorDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/contadores/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO>updateParameter(@RequestBody AdmContadorUpdateDTO contadorUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = contadoresService.updateContador(contadorUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	
	
}
