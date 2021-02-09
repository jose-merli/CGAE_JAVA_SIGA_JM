package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosRegistralesDTO;
import org.itcgae.siga.DTOs.cen.PerJuridicaDatosRegistralesUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosRegistralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TarjetaDatosRegistralesController {

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
	
	
	@RequestMapping(value = "perJuridicaDatosRegistrales/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateRegistryDataLegalPerson(@RequestBody PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosRegistralesService.updateRegistryDataLegalPerson(perJuridicaDatosRegistralesUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "perJuridicaDatosRegistrales/datosContador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContadorDTO> getDatosContador(HttpServletRequest request) { 
		AdmContadorDTO response = tarjetaDatosRegistralesService.getDatosContador(request);
		return new ResponseEntity<AdmContadorDTO >(response, HttpStatus.OK);
	}
	
	
	
}
