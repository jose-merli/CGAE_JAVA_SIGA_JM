package org.itcgae.siga.form.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.form.services.IFichaInscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaInscripcionController {

	@Autowired
	private IFichaInscripcionService fichaInscripcionService;
	
	@RequestMapping(value = "fichaInscripcion/searchCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CursoItem> searchCourse(@RequestBody String idCurso, HttpServletRequest request) {
		CursoItem response = fichaInscripcionService.searchCourse(idCurso, request);
		return new ResponseEntity<CursoItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/saveInscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> saveInscripcion(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) {
		ComboDTO response = fichaInscripcionService.saveInscripcion(inscripcionItem, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/updateInscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateInscripcion(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) {
		UpdateResponseDTO response = fichaInscripcionService.updateInscripcion(inscripcionItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/guardarPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> guardarPersona(@RequestBody AsociarPersonaDTO asociarPersona, HttpServletRequest request) { 
		InsertResponseDTO response = fichaInscripcionService.guardarPersona(asociarPersona, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
}
