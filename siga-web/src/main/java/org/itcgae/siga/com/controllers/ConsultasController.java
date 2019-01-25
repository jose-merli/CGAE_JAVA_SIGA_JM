package org.itcgae.siga.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> cargasMasivasSearch(@RequestParam("numPagina") int numPagina,
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
	ResponseEntity<Error> duplicarConsulta(HttpServletRequest request, @RequestBody ConsultaItem[] consultas) {

		Error response = _consultasService.duplicarConsulta(request, consultas);
		if (response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
	ResponseEntity<InputStreamResource> ejecutarConsulta(HttpServletRequest request, @RequestBody String consulta) {

		File file = _consultasService.ejecutarConsulta(request, consulta);
		HttpHeaders headers = null;
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
		System.out.println("The length of the file is : "+file.length());
		  
		return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}

}
