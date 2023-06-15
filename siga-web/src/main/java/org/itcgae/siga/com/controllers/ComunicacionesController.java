package org.itcgae.siga.com.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.com.DestinatariosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.NuevaComunicacionItem;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IComunicacionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/comunicaciones")
public class ComunicacionesController {

	@Autowired
	IComunicacionesService _comunicacionesService;
	

	
	@RequestMapping(value = "/clasesComunicacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClasesComunicacion(HttpServletRequest request) {
		
		ComboDTO response = _comunicacionesService.claseComunicacion(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modelosComunicacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> modelosClasesComunicacion(HttpServletRequest request) {
		
		ComboDTO response = _comunicacionesService.claseComunicacion(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> cargasMasivasSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody EnviosMasivosSearch filtros) {
		
		EnviosMasivosDTO response = _comunicacionesService.comunicacionesSearch(request, filtros); 
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
	ResponseEntity<DestinatariosDTO> obtenerDestinatariosEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		DestinatariosDTO response = _comunicacionesService.detalleDestinatarios(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<DestinatariosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DestinatariosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/detalle/descargarDocumento", method=RequestMethod.POST, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> obtenerDocumento(HttpServletRequest request, @RequestBody ResponseDocumentoDTO documentoDTO){
            
		HttpHeaders headers = null;
		File file = null;
		InputStreamResource resource = null;                
		
		try{
			ResponseFileDTO documento = _comunicacionesService.descargarDocumento(request, documentoDTO);
			file = documento.getFile();
			resource = new InputStreamResource(new FileInputStream(file));                  
		}catch(FileNotFoundException e){
    		e.printStackTrace();    
		}	  
		headers = new HttpHeaders();
//		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//		headers.add("Pragma", "no-cache");
//		headers.add("Expires", "0");
//		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentoDTO.getNombreDocumento() + "\"");
        headers.set("Content-Disposition",
                "attachment; filename=\"" + documentoDTO.getNombreDocumento() + "\"");
        headers.setContentLength(file.length());
		System.out.println("The length of the file is : "+file.length());
		  
		if(file != null) {
			return new ResponseEntity<InputStreamResource>(resource, headers,
                HttpStatus.OK);
		}
		else {
			return new ResponseEntity<InputStreamResource>(resource, headers,
	                HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }
	
	@RequestMapping(value="/detalle/descargarCertificado", method=RequestMethod.POST, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> obtenerCertificado(HttpServletRequest request, @RequestBody String idEnvio){
            
		HttpHeaders headers = null;
		InputStreamResource resource = null;                
		
		try{
			String docBase64 = _comunicacionesService.descargarCertificado(request, idEnvio);
			
			byte[] decodedValue = Base64.getDecoder().decode(docBase64);
			
			ByteArrayInputStream byteArray = new ByteArrayInputStream(decodedValue);
			resource = new InputStreamResource(byteArray);	
			
		}catch(Exception e){
    		e.printStackTrace();
		}
		
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		 
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + SigaConstants.NOMBRE_FICHERO_BUROSMS + "\"");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
		
		return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.OK);
    }	
	
	@RequestMapping(value = "/saveNuevaComm",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveNuevaComm(MultipartHttpServletRequest request) throws SigaExceptions, NumberFormatException, IOException, Exception {
		
		InsertResponseDTO response = _comunicacionesService.saveNuevaComm(request);

		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
}
