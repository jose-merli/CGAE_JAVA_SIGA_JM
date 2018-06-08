package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DatosRegistralesDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosRegistralesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TarjetaDatosRegistrales {

	@Autowired
	private ITarjetaDatosRegistralesService tarjetaDatosRegistralesService;
	
	@RequestMapping(value = "perJuridicaDatosRegistrales/actividadProfesionalPer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getActividadProfesionalPer(@RequestBody PersonaJuridicaActividadDTO personaJuridicaActividadDTO, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosRegistralesService.getActividadProfesionalPer(personaJuridicaActividadDTO, request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "perJuridicaDatosRegistrales/actividadProfesional", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getActividadProfesional(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosRegistralesService.getActividadProfesional(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "perJuridicaDatosRegistrales/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosRegistralesDTO> searchRegistryDataLegalPerson(@RequestBody PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request) { 
		DatosRegistralesDTO response = tarjetaDatosRegistralesService.searchRegistryDataLegalPerson(personaJuridicaSearchDTO, request);
		return new ResponseEntity<DatosRegistralesDTO>(response, HttpStatus.OK);
	}
	
	
}
