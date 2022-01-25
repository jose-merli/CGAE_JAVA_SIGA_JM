package org.itcgae.siga.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTOs.com.CamposDinamicosDTO;
import org.itcgae.siga.DTOs.com.ConfigColumnasQueryBuilderDTO;
import org.itcgae.siga.DTOs.com.ConfigColumnasQueryBuilderItem;
import org.itcgae.siga.DTOs.com.ConstructorConsultasDTO;
import org.itcgae.siga.DTOs.com.ConstructorConsultasRuleDTO;
import org.itcgae.siga.DTOs.com.ConsultaDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.com.QueryBuilderDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IConsultasService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/consultas")
public class ConsultasController {

	@Autowired
	IConsultasService _consultasService;

	/** COMBO DE MODULOS **/
	@RequestMapping(value = "/modulo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request) {

		ComboDTO response = _consultasService.modulo(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/** COMBO DE OBJETIVOS **/
	@RequestMapping(value = "/objetivo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboObjetivos(HttpServletRequest request) {

		ComboDTO response = _consultasService.objetivo(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/** COMBO CLASE DE COMUNICACIONES **/
	@RequestMapping(value = "/claseComunicacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClaseComunicacion(HttpServletRequest request) {

		ComboDTO response = _consultasService.claseComunicacion(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/claseComunicacionByModulo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClasesComunicacionByModulo(@RequestParam("idModulo") String idModulo, HttpServletRequest request) {
		
		ComboDTO response = _consultasService.claseComunicacionByModulo(idModulo, request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> consultasSearch(@RequestParam("numPagina") int numPagina,
			HttpServletRequest request, @RequestBody ConsultasSearch filtros) {

		ConsultasDTO response = _consultasService.consultasSearch(request, filtros);
		if (response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/** Obtiene el listado de los modelos que contienen la consulta **/
	@RequestMapping(value = "/modelosconsulta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultaListadoModelosDTO> obtenerModelosComunicacion(HttpServletRequest request,
			@RequestBody ConsultaItem consulta) {

		ConsultaListadoModelosDTO response = _consultasService.obtenerModelosComunicacion(request, consulta);
		if (response.getError() == null)
			return new ResponseEntity<ConsultaListadoModelosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultaListadoModelosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Obtiene el listado de las plantillas de envío que contienen la consulta
	 **/
	@RequestMapping(value = "/plantillasconsulta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultaListadoPlantillasDTO> obtenerPlantillasEnvio(HttpServletRequest request,
			@RequestBody ConsultaItem consulta) {

		ConsultaListadoPlantillasDTO response = _consultasService.obtenerPlantillasEnvio(request, consulta);
		if (response.getError() == null)
			return new ResponseEntity<ConsultaListadoPlantillasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultaListadoPlantillasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/borrarConsulta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarConsulta(HttpServletRequest request, @RequestBody ConsultaItem[] consultas) {

		Error response = _consultasService.borrarConsulta(request, consultas);
		if (response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/duplicarConsulta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultaDTO> duplicarConsulta(HttpServletRequest request, @RequestBody ConsultaItem consulta) {

		ConsultaDTO response = _consultasService.duplicarConsulta(request, consulta);
		if (response.getError() == null)
			return new ResponseEntity<ConsultaDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/confGeneral", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarTarjetaGeneral(HttpServletRequest request, @RequestBody ConsultaItem consulta) {

		Error response = _consultasService.guardarDatosGenerales(request, consulta);
		if (response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/confConsulta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarConsulta(HttpServletRequest request, @RequestBody ConsultaItem consulta) {

		Error response = _consultasService.guardarConsulta(request, consulta);
		if (response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else if (response.getCode() == 400)
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/ejecutarConsulta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> ejecutarConsulta(HttpServletRequest request, @RequestBody ConsultaItem consulta) {
		
		ResponseFileDTO response = _consultasService.ejecutarConsulta(request, consulta);

		File file = response.getFile();		
		HttpHeaders headers = null;
		InputStreamResource resource = null;
		
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		if(response.isResultados()){
			try {
				resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			System.out.println("The length of the file is : "+file.length());
			return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.OK);
		}else{
			if(response.getError() != null && response.getError().getCode() == 400) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SinArchivo\"");
				System.out.println("Consulta no permitida");
				return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.BAD_REQUEST);
			}else{
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SinArchivo\"");
				System.out.println("No se ha generado el fichero");
				return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.NO_CONTENT);
			}			
		}
	}
	
	@RequestMapping(value = "/obtenerCamposDinamicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CamposDinamicosDTO> obtenerCamposDinamicos(HttpServletRequest request, @RequestBody ConsultaItem consulta) {
		CamposDinamicosDTO response = _consultasService.obtenerCamposConsulta(request, consulta.getIdClaseComunicacion(), consulta.getSentencia());
		if (response.getError() == null)
			return new ResponseEntity<CamposDinamicosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<CamposDinamicosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/pys/constructorConsultas")
	ResponseEntity<QueryBuilderDTO> constructorConsultas(HttpServletRequest request, @RequestBody QueryBuilderDTO queryBuilderDTO) throws Exception {
		QueryBuilderDTO response = _consultasService.constructorConsultas(request, queryBuilderDTO);
		if (response.getError() == null)
			return new ResponseEntity<QueryBuilderDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<QueryBuilderDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//SQL
	@GetMapping(value = "/pys/obtenerDatosConsulta")
	ResponseEntity<ConstructorConsultasDTO> obtenerDatosConsulta(HttpServletRequest request, @RequestParam String idConsulta, @RequestParam String idInstitucion) { 
		ConstructorConsultasDTO response = _consultasService.obtenerDatosConsulta(request, idConsulta, idInstitucion);
		if (response.getError() == null)
			return new ResponseEntity<ConstructorConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConstructorConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pys/obtenerConfigColumnasQueryBuilder")
	ResponseEntity<ConfigColumnasQueryBuilderDTO> obtenerConfigColumnasQueryBuilder(HttpServletRequest request) { 
		ConfigColumnasQueryBuilderDTO response = _consultasService.obtenerConfigColumnasQueryBuilder(request);
		if (response.getError() == null)
			return new ResponseEntity<ConfigColumnasQueryBuilderDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConfigColumnasQueryBuilderDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/pys/obtenerCombosQueryBuilder")
	ResponseEntity<ComboDTO> obtenerCombosQueryBuilder(HttpServletRequest request, @RequestBody ConfigColumnasQueryBuilderItem configColumnasQueryBuilderItem) { 
		ComboDTO response = _consultasService.obtenerCombosQueryBuilder(request, configColumnasQueryBuilderItem);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
