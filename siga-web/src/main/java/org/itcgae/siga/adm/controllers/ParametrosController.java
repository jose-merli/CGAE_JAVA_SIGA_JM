package org.itcgae.siga.adm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.ParametroDTO;
import org.itcgae.siga.DTOs.adm.ParametroDeleteDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IParametrosService;
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
public class ParametrosController {

	@Autowired
	private IParametrosService parametrosService;
	
	@RequestMapping(value = "/parametros/modelo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getParameterModules() {
		ComboDTO response = parametrosService.getParameterModules();
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/parametros/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ParametroDTO> getParametersSearch(@RequestParam("numPagina") int numPagina, @RequestBody ParametroRequestDTO parametroRequestDTO, HttpServletRequest request) { 
		ParametroDTO response = parametrosService.getParametersSearch(numPagina, parametroRequestDTO, request);
		return new ResponseEntity<ParametroDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/parametros/historico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ParametroDTO> getParametersRecord(@RequestParam("numPagina") int numPagina, @RequestBody ParametroRequestDTO parametroRequestDTO, HttpServletRequest request) { 
		ParametroDTO response = parametrosService.getParametersRecord(numPagina, parametroRequestDTO, request);
		return new ResponseEntity<ParametroDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/parametros/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO>updateParameter(@RequestBody ParametroUpdateDTO parametroUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = parametrosService.updateParameter(parametroUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/parametros/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO>deleteParameter(@RequestBody ParametroDeleteDTO parametroDeleteDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = parametrosService.deleteParameter(parametroDeleteDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	
	
	
	
}
