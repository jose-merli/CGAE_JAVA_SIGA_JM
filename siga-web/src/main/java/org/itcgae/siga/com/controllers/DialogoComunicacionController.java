package org.itcgae.siga.com.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ByteResponseDto;
import org.itcgae.siga.DTOs.com.CamposDinamicosDTO;
import org.itcgae.siga.DTOs.com.ClaseComunicacionesDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.KeysDTO;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDateDTO;
import org.itcgae.siga.DTOs.com.TipoEnvioDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.itcgae.siga.DTOs.gen.Error;

@RestController
@RequestMapping(value = "/dialogoComunicacion")
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
	
	@RequestMapping(value = "/tipoEnvios",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TipoEnvioDTO> obtenertiposEnvioModelo(HttpServletRequest request, @RequestBody String idPlantilla) {
		
		TipoEnvioDTO response = _dialogoComunicacionService.obtenertipoEnvioPlantilla(request, idPlantilla);
		if(response.getError() == null)
			return new ResponseEntity<TipoEnvioDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<TipoEnvioDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/descargar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargar(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {
		
		ByteResponseDto response = _dialogoComunicacionService.descargarComunicacion(request, dialogo);
		
		byte[] zip = null;
		
		zip = response.getData();
			
		InputStream targetStream = new ByteArrayInputStream(zip);		

		InputStreamResource resource = new InputStreamResource(targetStream);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Access-Control-Expose-Headers","Content-Disposition");
		headers.add("Content-Disposition", "filename=" + SigaConstants.nombreZip + ".zip");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		return ResponseEntity.ok().headers(headers).contentLength(zip.length)
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	@RequestMapping(value = "/generarEnvios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> generarEnvios(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {

		Error response = _dialogoComunicacionService.generarEnvios(request, dialogo);
		if (response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/generarDocumentosEnvio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> generarDocumentosEnvio(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {

		Error response = _dialogoComunicacionService.generarDocumentosEnvio(request, "283265");
		if (response.getCode() == 200)
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
	

	@RequestMapping(value = "/obtenerCamposDinamicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerCamposDinamicos(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {
		ConsultasDTO response = _dialogoComunicacionService.obtenerCamposModelo(request, dialogo);
		if (response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/envioTest",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> enviarSMSTest(HttpServletRequest request) {
		
		Error response = _dialogoComunicacionService.enviarTest(request);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/fechaProgramada",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDateDTO> obtenerFechaProgramada(HttpServletRequest request) {
		
		ResponseDateDTO response = _dialogoComunicacionService.obtenerFechaProgramada(request);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDateDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDateDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/maxModelos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> obtenerNumMaximoModelos(HttpServletRequest request) {
		
		ResponseDataDTO response = _dialogoComunicacionService.obtenerNumMaximoModelos(request);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
