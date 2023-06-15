package org.itcgae.siga.scs.controllers.intercambios;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.services.intercambios.ICargaMasivaProcuradores;
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
@RequestMapping(value = "/cargaMasivaProcuradores")
public class CargaMasivaProcuradoresController {
	
	private Logger LOGGER = Logger.getLogger(CargaMasivaProcuradoresController.class);
	List<String> cabecera = Arrays.asList(HttpHeaders.CONTENT_DISPOSITION);
	
	@Autowired
	private ICargaMasivaProcuradores iCargaMasivaProcuradores;
	
	@RequestMapping(value = "/descargarModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> descargarModelo(HttpServletRequest request) throws IOException, EncryptedDocumentException, InvalidFormatException {
		LOGGER.debug("Entra en el método descargarLogErrores");
		InputStreamResource response = iCargaMasivaProcuradores.descargarModelo(request);
		LOGGER.debug("Termina el método descargarLogErrores");
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PlantillaCargaMasivaProcuradores.xls");
		header.setAccessControlExposeHeaders(cabecera);
		return new ResponseEntity<InputStreamResource>(response, header, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CargaMasivaProcuradorDTO> listado(@RequestBody CargaMasivaProcuradorBusquedaItem cargaMasivaItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método listado de Carga Masiva Procuradores");
		CargaMasivaProcuradorDTO response = iCargaMasivaProcuradores.listado(cargaMasivaItem, request);
		LOGGER.debug("Termina el método listado de Carga Masiva Procuradores");
		return new ResponseEntity<CargaMasivaProcuradorDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cargarFichero", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> cargarFichero(MultipartHttpServletRequest request) throws Exception{
		LOGGER.debug("Entra en el método cargarFichero de Carga Masiva Procuradores");
		DeleteResponseDTO response = iCargaMasivaProcuradores.cargarFichero(request);
		LOGGER.debug("Termina el método cargarFichero de Carga Masiva Procuradores");
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/descargarFicheros", method = RequestMethod.POST, produces = "application/zip")
	ResponseEntity<InputStreamResource> descargarFicheros(@RequestBody List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem, HttpServletRequest request) {
		return iCargaMasivaProcuradores.descargarFicheros(cargaMasivaProcuradorItem, request);		
	}

}
