package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PerfilesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.adm.service.impl.PerfilServiceImpl;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
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
@RequestMapping(value = "/modelos")
public class ModelosYComunicacionesController {
	
	@Autowired
	IModelosYcomunicacionesService _modelosYcomunicacionesService;
	
	@Autowired
	PerfilServiceImpl perfilServiceImpl;
	
	@RequestMapping(value = "/detalle/perfiles",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> perfiles(HttpServletRequest request) {
		
		ComboDTO response = perfilServiceImpl.getPerfiles(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/perfilesModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerEtiquetasEnvio(HttpServletRequest request, @RequestBody String idModeloComuncacion) {
		
		ComboDTO response = _modelosYcomunicacionesService.obtenerPerfilesModelo(request, idModeloComuncacion);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosModelosComunicacionesDTO> modelosComunicacionSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody DatosModelosComunicacionesSearch filtros) {
		
		DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesSearch(request, filtros);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/historico",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosModelosComunicacionesDTO> modelosComunicacionHistoricoSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody DatosModelosComunicacionesSearch filtros) {
		
		DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesHistoricoSearch(request, filtros);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/duplicar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> duplicarModelo(HttpServletRequest request, @RequestBody ModelosComunicacionItem modelo) {
		
		Error response = _modelosYcomunicacionesService.duplicarModeloComunicaciones(request, modelo);

		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/borrar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarModelo(HttpServletRequest request, ModelosComunicacionItem modelo) {
		
		Error response = _modelosYcomunicacionesService.borrarModeloComunicaciones(request, modelo);

		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
