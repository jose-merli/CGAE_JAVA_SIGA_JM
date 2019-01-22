package org.itcgae.siga.form.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.form.services.IBusquedaCursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusquedaCursosController {

	@Autowired
	private IBusquedaCursosService busquedaCursosService;
	
	@RequestMapping(value = "busquedaCursos/visibilidadCursos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getVisibilidadCursos(HttpServletRequest request) {
		ComboDTO response = busquedaCursosService.getVisibilidadCursos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCursos/estadosCursos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getEstadosCursos(HttpServletRequest request) {
		ComboDTO response = busquedaCursosService.getEstadosCursos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCursos/temasCursos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTemasCursos(HttpServletRequest request) {
		ComboDTO response = busquedaCursosService.getTemasCursos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCursos/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CursoDTO> searchColegiado(@RequestBody CursoItem cursoItem, HttpServletRequest request) {
		CursoDTO response = busquedaCursosService.searchCurso(cursoItem, request);
		return new ResponseEntity<CursoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCursos/archivar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Integer> archivarCursos(@RequestBody List<CursoItem> listCursoItem, HttpServletRequest request) {
		int response = busquedaCursosService.archivarCursos(listCursoItem, request);
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCursos/desarchivar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Integer> desarchivarCursos(@RequestBody List<CursoItem> listCursoItem, HttpServletRequest request) {
		int response = busquedaCursosService.desarchivarCursos(listCursoItem, request);
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaCursos/duplicateCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> duplicateCourse(@RequestBody CursoItem cursoItem, HttpServletRequest request) {
		InsertResponseDTO response = busquedaCursosService.duplicateCourse(cursoItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	
	
}
