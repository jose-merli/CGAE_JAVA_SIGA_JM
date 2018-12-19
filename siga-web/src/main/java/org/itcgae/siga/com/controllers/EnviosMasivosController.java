package org.itcgae.siga.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.NotFoundException;
import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaEtiquetasDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IEnviosMasivosService;
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
@RequestMapping(value = "/enviosMasivos")
public class EnviosMasivosController {
	
	@Autowired
	IEnviosMasivosService _enviosMasivosService;
	
	
	@RequestMapping(value = "/estadoEnvios",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoEnvios(HttpServletRequest request) {
		
		ComboDTO response = _enviosMasivosService.estadoEnvios(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/tipoEnvios",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoEnvios(HttpServletRequest request) {
		
		ComboDTO response = _enviosMasivosService.tipoEnvio(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> cargasMasivasSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody EnviosMasivosSearch filtros) {
		
		EnviosMasivosDTO response = _enviosMasivosService.enviosMasivosSearch(request, filtros); 
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/programarEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> programarEnvio(HttpServletRequest request, @RequestBody EnviosMasivosItem[] enviosProgramadosDto) {
		
		Error response = _enviosMasivosService.programarEnvio(request, enviosProgramadosDto);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/enviar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> enviar(HttpServletRequest request, @RequestBody List<EnvioProgramadoDto> envios) {
		
		Error response = _enviosMasivosService.enviar(request, envios) ;
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/cancelarEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> cancelarEnvio(HttpServletRequest request, @RequestBody EnvioProgramadoDto[] envios) {
		
		Error response = _enviosMasivosService.cancelarEnvios(request, envios);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/guardarConfiguracion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> tarjetaConfiguracion(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto datosTarjeta) {
		
		Error response = _enviosMasivosService.guardarConfiguracion(request, datosTarjeta);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/duplicarEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> duplicarEnvio(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto datosTarjeta) {
		
		Error response = _enviosMasivosService.duplicarEnvio(request, datosTarjeta);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/plantillas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPlantillas(HttpServletRequest request, @RequestBody String idTipoEnvio) {
		
		ComboDTO response = _enviosMasivosService.nombrePlantillas(request, idTipoEnvio);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/documentosEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentosEnvioDTO> obtenerDocumentosEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		DocumentosEnvioDTO response = _enviosMasivosService.obtenerDocumentosEnvio(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<DocumentosEnvioDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DocumentosEnvioDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/etiquetas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerEtiquetas(HttpServletRequest request) {
		
		ComboDTO response = _enviosMasivosService.obtenerEtiquetas(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/etiquetasEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerEtiquetasEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		ComboDTO response = _enviosMasivosService.obtenerEtiquetasEnvio(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/guardarEtiquetas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarEtiquetasEnvio(HttpServletRequest request, @RequestBody TarjetaEtiquetasDTO tarjetaEtiquetas) {
		
		Error response = _enviosMasivosService.guardarEtiquetasEnvio(request, tarjetaEtiquetas);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/subirDocumento",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<ResponseDocumentoDTO> uploadFile(MultipartHttpServletRequest request) throws Exception{
		
		ResponseDocumentoDTO response = _enviosMasivosService.uploadFile(request);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.OK);
		else{
			if(response.getError().getCode()==400){
				return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.BAD_REQUEST);
			}else{
				return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
			
	}

	@RequestMapping(value = "/detalle/guardarDocumentoEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarDocumentosEnvio(HttpServletRequest request, @RequestBody ResponseDocumentoDTO documento) {
		
		Error response = _enviosMasivosService.guardarDocumentoEnvio(request, documento);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/borrarDocumentoEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarDocumento(HttpServletRequest request, @RequestBody ResponseDocumentoDTO[] documento) {
		
		Error response = _enviosMasivosService.borrarDocumento(request, documento);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/detalle/descargarDocumento", method=RequestMethod.POST, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> obtenerAdjunto(@RequestBody ResponseDocumentoDTO documentoDTO){
		
              
              HttpHeaders headers = null;
              File file = null;
              InputStreamResource resource = null;
                               
              file = new File(documentoDTO.getRutaDocumento());
              
              try{
                        resource = new InputStreamResource(new FileInputStream(file));                  
              }catch(FileNotFoundException e){
                        
              }
              
			  headers = new HttpHeaders();
			  headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			  headers.add("Pragma", "no-cache");
			  headers.add("Expires", "0");
			  headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentoDTO.getNombreDocumento() + "\"");
			  System.out.println("The length of the file is : "+file.length());
  
              return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }


}
