package org.itcgae.siga.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.itcgae.siga.DTOs.com.ComboConsultasDTO;
import org.itcgae.siga.DTOs.com.ComboSufijoDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.DTOs.com.DocumentosPlantillaDTO;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoBorrarDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDataListDTO;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.com.TarjetaPlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IPlantillasDocumentoService;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/plantillasDoc")
public class PlantillasDocumentoController {
	
	@Autowired
	IPlantillasDocumentoService _plantillasDocumentoService;
	
	@RequestMapping(value = "/combo/consultas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboConsultasDTO> obtenerComboConsultas(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ComboConsultasDTO response = _plantillasDocumentoService.obtenerConsultasDisponibles(request, plantillaDoc);
		
		if(response.getError() == null)
			return new ResponseEntity<ComboConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/combo/formatos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerFormatoSalida(HttpServletRequest request) {
		
		ComboDTO response = _plantillasDocumentoService.obtenerFormatoSalida(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/combo/sufijos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboSufijoDTO> obtenerSufijos(HttpServletRequest request) {
		
		ComboSufijoDTO response = _plantillasDocumentoService.obtenerSufijos(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboSufijoDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboSufijoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/consultas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultas(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ConsultasDTO response = _plantillasDocumentoService.obtenerConsultasPlantilla(request, plantillaDoc, false);
		if(response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/consultas/historico",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultasHistorico(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ConsultasDTO response = _plantillasDocumentoService.obtenerConsultasPlantilla(request, plantillaDoc, true);
		if(response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/consultas/guardar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarConsultas(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		Error response = _plantillasDocumentoService.guardarConsultasPlantilla(request, plantillaDoc);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/consultas/borrar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarConsultas(HttpServletRequest request, @RequestBody PlantillaDocumentoBorrarDTO[] plantillaDoc) {
		
		Error response = _plantillasDocumentoService.borrarConsultasPlantilla(request, plantillaDoc);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/guardar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> guardarPlantillaDocumento(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ResponseDataDTO response = _plantillasDocumentoService.guardarModPlantillaDocumento(request, plantillaDoc);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value = "/guardarPlantillas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataListDTO> guardarPlantillasDocumento(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO[] plantillaDoc) {
		
		ResponseDataListDTO response = _plantillasDocumentoService.guardarModPlantillasDocumento(request, plantillaDoc);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDataListDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataListDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value = "/guardar/datosSalida",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> guardarDatosSalida(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ResponseDataDTO response = _plantillasDocumentoService.guardarDatosSalida(request, plantillaDoc);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value = "/subirPlantilla",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<ResponseDocumentoDTO> uploadFile(MultipartHttpServletRequest request, @QueryParam("idClaseComunicacion") String idClaseComunicacion) throws Exception{
		
		ResponseDocumentoDTO response = _plantillasDocumentoService.uploadFile(request, idClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.OK);	
		else
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/insertarPlantilla",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDocumentoDTO> insertarPlantilla(HttpServletRequest request, @RequestBody DocumentoPlantillaItem documento) throws Exception{
		
		ResponseDocumentoDTO response = _plantillasDocumentoService.guardarPlantillaDocumento(request, documento);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.OK);	
		else
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/plantillas",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentosPlantillaDTO> obtenerPlantillasInforme(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) throws Exception{
		
		DocumentosPlantillaDTO response = _plantillasDocumentoService.obtenerPlantillasInforme(request, plantillaDoc);
		if(response.getError() == null)
			return new ResponseEntity<DocumentosPlantillaDTO>(response, HttpStatus.OK);	
		else
			return new ResponseEntity<DocumentosPlantillaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/borrar",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarPlantillas(HttpServletRequest request, @RequestBody PlantillaDocumentoBorrarDTO[] plantillaDoc) throws Exception{
		
		Error response = _plantillasDocumentoService.borrarPlantillas(request, plantillaDoc);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);	
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/descargarPlantilla",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarPlantilla(HttpServletRequest request, @RequestBody DocumentoPlantillaItem plantillaDoc) throws Exception{
		
		HttpHeaders headers = null;
		File file = null;
		InputStreamResource resource = null;                
		
		try{
			ResponseFileDTO documento = _plantillasDocumentoService.descargarPlantilla(request, plantillaDoc);
			file = documento.getFile();
			if(file != null) {
			resource = new InputStreamResource(new FileInputStream(file));
			}else {
//				return ResponseEntity.status(SigaCon);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();    
		}	  
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + plantillaDoc.getNombreDocumento() + "\"");
		if(file != null) {
			System.out.println("The length of the file is : "+file.length());
			return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		}else {
			return null;
		}
	}
	
	@RequestMapping(value = "/consulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultaById(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ConsultasDTO response = _plantillasDocumentoService.obtenerConsultasById(request, plantillaDoc);
		if(response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/consulta/sizeFichero",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerSizeFichero(HttpServletRequest request) {
		
		ComboDTO response = _plantillasDocumentoService.obtenerSizeFichero(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}