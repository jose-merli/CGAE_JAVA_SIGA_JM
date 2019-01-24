package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/DialogoComunicacion")
public class DialogoComunicacionController {
	
	@Autowired
	IDialogoComunicacionService _dialogoComunicacionService;
	
	@RequestMapping(value = "/clasesComunicacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClasesComunicacion(HttpServletRequest request, @RequestBody String rutaClaseComunicacion) {
		
		ComboDTO response = _dialogoComunicacionService.obtenerClaseComunicaciones(request, rutaClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/modelosSearch",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModelosComunicacionSearch> modelosComunicacionSearch(HttpServletRequest request, @RequestBody String[] idClaseComunicacion) {
		
		ModelosComunicacionSearch response = _dialogoComunicacionService.obtenerModelos(request, idClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<ModelosComunicacionSearch>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ModelosComunicacionSearch>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/tiposEnvioModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenertiposEnvioModelo(HttpServletRequest request, @RequestBody String idPlantilla) {
		
		ComboDTO response = _dialogoComunicacionService.obtenertipoEnvioPlantilla(request, idPlantilla);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
