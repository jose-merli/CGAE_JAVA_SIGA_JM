package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.FinalidadConsultaDTO;
import org.itcgae.siga.DTOs.com.PersonaDTO;
import org.itcgae.siga.DTOs.com.PlantillaDatosConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaRemitenteDTO;
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
	

	
	@RequestMapping(value = "/plantillasEnvioSearch",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasEnvioDTO> PlantillasEnvioSearch(@RequestParam("numPagina") int numPagina,HttpServletRequest request, @RequestBody PlantillaEnvioSearchItem filtros) {
		
		PlantillasEnvioDTO respuesta = _plantillasEnvioService.PlantillasEnvioSearch(request, filtros);
		
		if(respuesta.getError()!= null)
			return new ResponseEntity<PlantillasEnvioDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<PlantillasEnvioDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/datosGenerales",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarDatosGenerales(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto datosTarjeta) {
		
		Error respuesta = _plantillasEnvioService.guardarDatosGenerales(request, datosTarjeta);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/asociarConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> asociarConsulta(HttpServletRequest request, @RequestBody PlantillaDatosConsultaDTO consulta) {
		
		Error respuesta = _plantillasEnvioService.asociarConsulta(request, consulta);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/desasociarConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> desasociarConsulta(HttpServletRequest request, @RequestBody PlantillaDatosConsultaDTO consulta) {
		
		Error respuesta = _plantillasEnvioService.borrarConsulta(request, consulta);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/borrarPlantilla",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarPlantillaEnvio(HttpServletRequest request, @RequestBody PlantillaEnvioItem[] plantillasEnvio) {
		
		Error respuesta = _plantillasEnvioService.borrarPlantillasEnvio(request, plantillasEnvio);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/consultasPlantillas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultasPlantilla(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto consulta) {
		
		ConsultasDTO respuesta = _plantillasEnvioService.detalleConsultas(request, consulta);
		
		if(respuesta.getError()== null)
			return new ResponseEntity<ConsultasDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/consultasDisp",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerConsultas(HttpServletRequest request) {
		
		ComboDTO response = _plantillasEnvioService.getComboConsultas(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalleRemitente",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemitenteDTO> obtenerDetalleRemitente(HttpServletRequest request, @RequestBody PlantillaDatosConsultaDTO datosPlantilla) {
		
		RemitenteDTO respuesta = _plantillasEnvioService.detalleRemitente(request, datosPlantilla);
		
		if(respuesta.getError() == null)
			return new ResponseEntity<RemitenteDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<RemitenteDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/personaYdirecciones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemitenteDTO> obtenerPersonaYdirecciones(HttpServletRequest request, @RequestBody PersonaDTO persona) {
		
		RemitenteDTO respuesta = _plantillasEnvioService.obtenerPersonaYdireccion(request, persona);
		
		if(respuesta.getError() == null)
			return new ResponseEntity<RemitenteDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<RemitenteDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@RequestMapping(value = "/finalidadConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FinalidadConsultaDTO> obtenerFinalidadConsulta(HttpServletRequest request, @RequestBody String idConsulta) {
		
		FinalidadConsultaDTO respuesta = _plantillasEnvioService.obtenerFinalidad(request, idConsulta);
		
		if(respuesta.getError() == null)
			return new ResponseEntity<FinalidadConsultaDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<FinalidadConsultaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/guardarRemitente",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarRemitente(HttpServletRequest request, @RequestBody TarjetaRemitenteDTO remitente){
		
		Error respuesta = _plantillasEnvioService.guardarRemitente(request, remitente);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
