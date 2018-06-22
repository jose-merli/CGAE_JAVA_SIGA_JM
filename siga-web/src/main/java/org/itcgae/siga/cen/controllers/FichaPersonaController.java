package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DesasociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.FichaPerSearchDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IFichaPersonaService;
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
public class FichaPersonaController {

	@Autowired
	private IFichaPersonaService fichaPersonaService;
	
	@RequestMapping(value = "/fichaPersona/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FichaPersonaDTO> searchPersonFile(@RequestParam("numPagina") int numPagina, @RequestBody FichaPerSearchDTO fichaPerSearch, HttpServletRequest request) { 
		FichaPersonaDTO response = fichaPersonaService.searchPersonFile(numPagina, fichaPerSearch, request);
		return new ResponseEntity<FichaPersonaDTO>(response, HttpStatus.OK);
	}	
	
	
	@RequestMapping(value = "/fichaPersona/desasociarPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> disassociatePerson(@RequestBody DesasociarPersonaDTO desasociarPersona, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaPersonaService.disassociatePerson(desasociarPersona, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/fichaPersona/guardar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> savePerson(@RequestBody AsociarPersonaDTO asociarPersona, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaPersonaService.savePerson(asociarPersona, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/fichaPersona/crearNotario",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> createPersonFile(@RequestBody CrearPersonaDTO crearPersonaDTO, HttpServletRequest request) { 
		ComboDTO response = fichaPersonaService.createPersonFile(crearPersonaDTO, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/busquedaPerJuridica/tipoSociedadCombo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getIdentificationTypes( HttpServletRequest request) {
		ComboDTO response = fichaPersonaService.getIdentificationTypes(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	
	
}
