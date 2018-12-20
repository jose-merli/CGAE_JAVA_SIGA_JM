package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.db.services.com.providers.ConListadoPlantillasExtendsSqlProvider;
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
@RequestMapping(value = "/consultas")
public class ConsultasController {

	@Autowired
	IConsultasService _consultasService;
	
	
	
	/**COMBO DE MODULOS**/
	@RequestMapping(value = "/modulo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request) {
		
		ComboDTO response = _consultasService.modulo(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**COMBO DE OBJETIVOS**/
	@RequestMapping(value = "/objetivo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboObjetivos(HttpServletRequest request) {
		
		ComboDTO response = _consultasService.objetivo(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**COMBO CLASE DE COMUNICACIONES**/
	@RequestMapping(value = "/claseComunicacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClaseComunicacion(HttpServletRequest request) {
		
		ComboDTO response = _consultasService.claseComunicacion(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> cargasMasivasSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody ConsultasSearch filtros) {
		
		ConsultasDTO response = _consultasService.consultasSearch(request, filtros); 
		if(response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
<<<<<<< HEAD
	/**Obtiene el listado de los modelos que contienen la consulta**/
	@RequestMapping(value = "/modelosconsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultaListadoModelosDTO> obtenerModelosComunicacion(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody String idConsulta) {
		
		ConsultaListadoModelosDTO response = _consultasService.obtenerModelosComunicacion(request, idConsulta);
		if(response.getError() == null)
			return new ResponseEntity<ConsultaListadoModelosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultaListadoModelosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**Obtiene el listado de las plantillas de envío que contienen la consulta**/
	@RequestMapping(value = "/plantillasconsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultaListadoPlantillasDTO> obtenerPlantillasEnvio(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody String idConsulta) {
		
		ConsultaListadoPlantillasDTO response = _consultasService.obtenerPlantillasEnvio(request, idConsulta);
		if(response.getError() == null)
			return new ResponseEntity<ConsultaListadoPlantillasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultaListadoPlantillasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
=======
	@RequestMapping(value = "/borrarConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> cargasMasivasSearch(HttpServletRequest request, @RequestBody String[] idConsulta) {
		
		Error response = _consultasService.borrarConsulta(request, idConsulta);
		if(response.getCode()==200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
>>>>>>> generado entidad para consultas y servicio de borrar
	
}
