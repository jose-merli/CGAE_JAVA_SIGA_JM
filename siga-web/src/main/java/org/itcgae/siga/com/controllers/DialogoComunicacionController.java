package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ClaseComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.KeysDTO;
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
import org.itcgae.siga.DTOs.gen.Error;

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
	
	@RequestMapping(value = "/claseComunicacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ClaseComunicacionesDTO> obtenerClaseComunicacion(HttpServletRequest request, @RequestBody String rutaClaseComunicacion) {
		
		ClaseComunicacionesDTO response = _dialogoComunicacionService.obtenerClaseComunicacionesUnica(request, rutaClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<ClaseComunicacionesDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ClaseComunicacionesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/modelosSearch",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModelosComunicacionSearch> modelosComunicacionSearch(HttpServletRequest request, @RequestBody String idClaseComunicacion) {
		
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
	
	@RequestMapping(value = "/descargar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> descargar(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {
		
		Error response = _dialogoComunicacionService.descargarComunicacion(request, dialogo);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/keys",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<KeysDTO> obtenertiposKeysClase(HttpServletRequest request, @RequestBody String idClaseComunicacion) {
		
		KeysDTO response = _dialogoComunicacionService.obtenerKeysClaseComunicacion(request, idClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<KeysDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<KeysDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/envioTest",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> enviarSMSTest(HttpServletRequest request) {
		
		Error response = _dialogoComunicacionService.enviarTest(request);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
