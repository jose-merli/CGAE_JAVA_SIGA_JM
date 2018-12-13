package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IComunicacionesService;
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
@RequestMapping(value = "/comunicaciones")
public class ComunicacionesController {

	@Autowired
	IComunicacionesService _comunicacionesService;
	

	
	@RequestMapping(value = "/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> cargasMasivasSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody EnviosMasivosSearch filtros) {
		
		EnviosMasivosDTO response = _comunicacionesService.comunicacionesSearch(request, filtros); 
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/programarEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> programarEnvio(HttpServletRequest request, @RequestBody EnviosMasivosItem[] enviosProgramadosDto) {
		
		Error response = _comunicacionesService.programarEnvio(request, enviosProgramadosDto);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/cancelarEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> cancelarEnvio(HttpServletRequest request, @RequestBody EnvioProgramadoDto[] envios) {
		
		Error response = _comunicacionesService.cancelarEnvios(request, envios);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/documentosEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentosEnvioDTO> obtenerDocumentosEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		DocumentosEnvioDTO response = _comunicacionesService.obtenerDocumentosEnvio(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<DocumentosEnvioDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DocumentosEnvioDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/reenviar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> reenviar(HttpServletRequest request,  @RequestBody EnviosMasivosItem[] envioItem) {
		
		Error response = _comunicacionesService.reenviar(request, envioItem);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/destinatarios",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> obtenerDestinatariosEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		EnviosMasivosDTO response = _comunicacionesService.detalleDestinatarios(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/configuracion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> obtenerConfiguracionEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		EnviosMasivosDTO response = _comunicacionesService.detalleConfiguracion(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
