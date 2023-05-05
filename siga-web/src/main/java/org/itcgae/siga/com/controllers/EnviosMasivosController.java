package org.itcgae.siga.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.com.ComboConsultaInstitucionDTO;
import org.itcgae.siga.DTOs.com.ConsultaDestinatarioItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DestinatarioIndvEnvioMasivoItem;
import org.itcgae.siga.DTOs.com.DestinatariosDTO;
import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaEtiquetasDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.FileInfoDTO;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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
	
	private Logger LOGGER = Logger.getLogger(EnviosMasivosController.class);
	
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
	
	@RequestMapping(value = "/searchBusqueda",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> busquedaEnvioMasivoSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody EnviosMasivosSearch filtros) {
		
		EnviosMasivosDTO response = _enviosMasivosService.busquedaEnvioMasivoSearch(request, filtros); 
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
	ResponseEntity<Error> enviar(HttpServletRequest request, @RequestBody EnvioProgramadoDto[] envios) {
		
		Error response = _enviosMasivosService.enviar(request, envios);
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
	
	@RequestMapping(value = "/duplicarEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> duplicarEnvio(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto datosTarjeta) {
		
		EnviosMasivosDTO response = _enviosMasivosService.duplicarEnvio(request, datosTarjeta);
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/plantillas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPlantillas(HttpServletRequest request, @RequestBody String idTipoEnvio) {
		
		ComboDTO response = _enviosMasivosService.nombrePlantillas(request, idTipoEnvio);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/detallePlantilla",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillaEnvioItem> obtenerAsuntoYCuerpo(HttpServletRequest request, @RequestBody TarjetaConfiguracionDto datosTarjeta) {
		
		PlantillaEnvioItem response = _enviosMasivosService.obtenerAsuntoYcuerpo(request, datosTarjeta);
		return new ResponseEntity<PlantillaEnvioItem>(response, HttpStatus.OK);
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
	ResponseEntity<ComboInstitucionDTO> obtenerEtiquetas(HttpServletRequest request) {
		
		ComboInstitucionDTO response = _enviosMasivosService.obtenerEtiquetas(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboInstitucionDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboInstitucionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/etiquetasEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboInstitucionDTO> obtenerEtiquetasEnvio(HttpServletRequest request, @RequestBody String idEnvio) {
		
		ComboInstitucionDTO response = _enviosMasivosService.obtenerEtiquetasEnvio(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<ComboInstitucionDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboInstitucionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
	ResponseEntity<ResponseDocumentoDTO> uploadFile(@RequestParam("idEnvio") Long idEnvio, MultipartHttpServletRequest request) throws Exception{
		
		ResponseDocumentoDTO response = _enviosMasivosService.uploadFile(idEnvio, request);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDocumentoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
		else if (response.getCode() == 400)
			return new ResponseEntity<Error>(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/detalle/descargarDocumento", method=RequestMethod.POST, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> obtenerAdjunto(@RequestBody ResponseDocumentoDTO documentoDTO) throws Exception{
            
		HttpHeaders headers = null;
		File file = null;
		Resource resource = null;  
		long contentLength = -1;
		
		if (documentoDTO.getIdDocumento() != null) {
			//busco si es un destintario de burosms
			resource = buroSMS(documentoDTO);
			if (resource != null) {
				contentLength = resource.contentLength();
			}
		}
		
		if (resource == null) {
			String filePath = _enviosMasivosService.getPathFicheroEnvioMasivo(documentoDTO.getIdInstitucion(), Long.valueOf(documentoDTO.getIdEnvio()),null);
			file = new File(filePath, documentoDTO.getRutaDocumento());
			try{
				if (file != null && !file.exists()) {
					//si no lo encontramos para descargar es pq es del siga antiguo y se guardaba con este nombre
					file = new File(filePath, documentoDTO.getIdInstitucion() + "_" + documentoDTO.getIdEnvio() + "_" + documentoDTO.getIdDocumento());
				}
				resource = new InputStreamResource(new FileInputStream(file)); 
				contentLength = file.length();
			}catch(FileNotFoundException e){
	    		LOGGER.error("Error al recuperar el fichero ", e);
			}
		}
		
		
			  
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentoDTO.getNombreDocumento() + "\"");
//		System.out.println("The length of the file is : "+file.length());
		  
		return ResponseEntity.ok().headers(headers).contentLength(contentLength).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }
	
	@RequestMapping(value = "/detalle/nombreFicheroLog",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FileInfoDTO> nombreFicheroLog(@RequestBody ResponseDocumentoDTO documentoDTO) {
		
		FileInfoDTO fileInfoDTO = new FileInfoDTO();
		
		File file = null;
		
		File[] files = _enviosMasivosService.getFicherosLOGEnvioMasivo(documentoDTO.getIdInstitucion(), Long.valueOf(documentoDTO.getIdEnvio()));
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && files[i].exists()) {
					file = files[i];
				}
			}
		}
		
		if (file == null || !file.exists()) {
			throw new BusinessException("El fichero de log no existe");
		}
		
		fileInfoDTO.setFilePath(file.getAbsolutePath());
		fileInfoDTO.setName(file.getName());
		return new ResponseEntity<FileInfoDTO>(fileInfoDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/detalle/descargarLog", method=RequestMethod.POST, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> descargarLog(@RequestBody ResponseDocumentoDTO documentoDTO) throws Exception{
            
		HttpHeaders headers = null;
		File file = null;
		Resource resource = null;  
		long contentLength = -1;
		
		
		File[] files = _enviosMasivosService.getFicherosLOGEnvioMasivo(documentoDTO.getIdInstitucion(), Long.valueOf(documentoDTO.getIdEnvio()));
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && files[i].exists()) {
					file = files[i];
				}
			}
		}
		
		if (file == null || !file.exists()) {
			throw new BusinessException("El fichero de log no existe");
		}
		resource = new InputStreamResource(new FileInputStream(file)); 
		contentLength = file.length();
		
			  
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
		  
		return ResponseEntity.ok().headers(headers).contentLength(contentLength).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }
	
	private Resource buroSMS(ResponseDocumentoDTO documentoDTO) {
		Resource inputStreamResource = null;
		if (documentoDTO.getIdInstitucion() != null && documentoDTO.getIdEnvio() != null && !documentoDTO.getIdEnvio().trim().equals("") 
				&& documentoDTO.getIdDocumento() != null) {
			inputStreamResource = _enviosMasivosService.recuperaPdfBuroSMS(documentoDTO.getIdInstitucion(), Long.parseLong(documentoDTO.getIdEnvio()), documentoDTO.getIdDocumento());
		}
		
		return inputStreamResource;
	}


	@RequestMapping(value = "/detalle/consultasDest",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboConsultaInstitucionDTO> obtenerConsultasDest(HttpServletRequest request) {
		
		ComboConsultaInstitucionDTO response = _enviosMasivosService.obtenerconsultasDestinatarios(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboConsultaInstitucionDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboConsultaInstitucionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/ConsultasEnvAsociadas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultasAsociadas(HttpServletRequest request, @RequestBody String idEnvio) {
		
		ConsultasDTO response = _enviosMasivosService.consultasDestAsociadas(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/asociarConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarConsultaDest(HttpServletRequest request, @RequestBody ConsultaDestinatarioItem consulta) {
		
		Error response = _enviosMasivosService.asociarConsulta(request, consulta);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else if (response.getCode() == 400)
			return new ResponseEntity<Error>(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/desAsociarConsulta",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> desAsociarConsulta(HttpServletRequest request, @RequestBody ConsultaDestinatarioItem[] consulta) {
		
		Error response = _enviosMasivosService.desAsociarConsulta(request, consulta);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "detalle/consultasDestinatariosDisp",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboConsultaInstitucionDTO> obtenerConsultas(@RequestParam("filtro") String filtro, HttpServletRequest request) {
		
		ComboConsultaInstitucionDTO response = _enviosMasivosService.getComboConsultas(request, filtro);
		if(response.getError() == null)
			return new ResponseEntity<ComboConsultaInstitucionDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboConsultaInstitucionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "detalle/destinatariosIndv",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DestinatariosDTO> obtenerDestinatariosIndv(HttpServletRequest request, @RequestBody String idEnvio) {
		
		DestinatariosDTO response = _enviosMasivosService.obtenerDestinatariosIndv(request, idEnvio);
		if(response.getError() == null)
			return new ResponseEntity<DestinatariosDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DestinatariosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "detalle/asociarDestinatariosIndv",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> asociarDestinatarioIndv(HttpServletRequest request, @RequestBody DestinatarioIndvEnvioMasivoItem destinatario) {
		
		Error response = _enviosMasivosService.asociarDestinatario(request, destinatario);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "detalle/desAsociarDestinatarioIndv",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> desAsociarDestinatario(HttpServletRequest request, @RequestBody DestinatarioIndvEnvioMasivoItem[] destinatarios) {
		
		Error response = _enviosMasivosService.desAsociarDestinatarios(request, destinatarios);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else if (response.getCode() == 400)
			return new ResponseEntity<Error>(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "detalle/direccionesDestinatarioIndv",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosDireccionesDTO> obtenerDireccionesDisp(HttpServletRequest request, @RequestBody String nif) {
		
		DatosDireccionesDTO response = _enviosMasivosService.obtenerDireccionesDisp(request, nif);
		if(response.getError() == null)
			return new ResponseEntity<DatosDireccionesDTO>(response, HttpStatus.OK);
		else{
			if(response.getError().getCode() == 400)
				return new ResponseEntity<DatosDireccionesDTO>(response, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<DatosDireccionesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@RequestMapping(value = "/obtenerDestinatarios",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> obtenerDestinatarios(HttpServletRequest request, @RequestBody EnviosMasivosDTO enviosDTO) {
		
		EnviosMasivosDTO response = _enviosMasivosService.obtenerDestinatarios(request, enviosDTO);
		if(response.getError() == null)
			return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
		else{
			if(response.getError().getCode() == 400)
				return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
}
