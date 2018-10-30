package org.itcgae.siga.age.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.age.service.IFichaEventosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaEventosController {
	
	@Autowired
	private IFichaEventosService fichaEventosService;
			
	@RequestMapping(value = "fichaEventos/getTrainers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FormadorCursoDTO> getTrainers(String idCurso, HttpServletRequest request) {
		FormadorCursoDTO response = fichaEventosService.getTrainers(idCurso, request);
		return new ResponseEntity<FormadorCursoDTO>(response, HttpStatus.OK);
	}
	
}
