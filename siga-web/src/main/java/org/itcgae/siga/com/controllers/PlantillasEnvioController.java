package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.PlantillaDatosConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.itcgae.siga.DTOs.gen.Error;

@RestController
@RequestMapping(value = "/plantillasEnvio")
public class PlantillasEnvioController {
	
	@Autowired
	IPlantillasEnvioService _plantillasEnvioService;
	
	
	@RequestMapping(value = "/tiposComunicacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTiposComunicacion(HttpServletRequest request) {
		
		ComboDTO response = _plantillasEnvioService.getComboTipoEnvio(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/plantillasEnvioSearch",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasEnvioDTO> PlantillasEnvioSearch(@RequestParam("numPagina") int numPagina,HttpServletRequest request, @RequestBody PlantillaEnvioSearchItem filtros) {
		
		PlantillasEnvioDTO respuesta = _plantillasEnvioService.PlantillasEnvioSearch(request, filtros);
		
		if(respuesta.getError()!= null)
			return new ResponseEntity<PlantillasEnvioDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<PlantillasEnvioDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/datosGenerales",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> PlantillasEnvioSearch(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto datosTarjeta) {
		
		Error respuesta = _plantillasEnvioService.guardarDatosGenerales(request, datosTarjeta);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/asociarConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarConsulta(HttpServletRequest request, @RequestBody PlantillaDatosConsultaDTO consulta) {
		
		Error respuesta = _plantillasEnvioService.asociarConsulta(request, consulta);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/consultasPlantillas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultas(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto consulta) {
		
		ConsultasDTO respuesta = _plantillasEnvioService.detalleConsultas(request, consulta);
		
		if(respuesta.getError()!= null)
			return new ResponseEntity<ConsultasDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	

}
