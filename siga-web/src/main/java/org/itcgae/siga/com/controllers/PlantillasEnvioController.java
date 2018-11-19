package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	ResponseEntity<DatosModelosComunicacionesDTO> PlantillasEnvioSearch(HttpServletRequest request, PlantillaEnvioSearchItem filtros) {
		
		/*DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesSearch(request, filtros);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);*/
		return null;
	}
	

}
