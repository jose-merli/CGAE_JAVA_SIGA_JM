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
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.controllers.remesas.RemesasController;
import org.itcgae.siga.scs.services.intercambios.ICargaMasivaProcuradores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/cargaMasivaProcuradores")
public class CargaMasivaProcuradoresController {
	
	private Logger LOGGER = Logger.getLogger(RemesasController.class);
	List<String> cabecera = Arrays.asList(HttpHeaders.CONTENT_DISPOSITION);
	
	@Autowired
	private ICargaMasivaProcuradores iCargaMasivaProcuradores;
	
	@RequestMapping(value = "/descargarModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> descargarModelo(HttpServletRequest request) throws IOException, EncryptedDocumentException, InvalidFormatException {
		LOGGER.info("Entra en el método descargarLogErrores");
		InputStreamResource response = iCargaMasivaProcuradores.descargarModelo(request);
		LOGGER.info("Termina el método descargarLogErrores");
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PlantillaCargaMasivaProcuradores.xls");
		header.setAccessControlExposeHeaders(cabecera);
		return new ResponseEntity<InputStreamResource>(response, header, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CargaMasivaProcuradorDTO> listado(@RequestBody CargaMasivaProcuradorBusquedaItem cargaMasivaItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método listado de Carga Masiva Procuradores");
		CargaMasivaProcuradorDTO response = iCargaMasivaProcuradores.listado(cargaMasivaItem, request);
		LOGGER.info("Termina el método listado de Carga Masiva Procuradores");
		return new ResponseEntity<CargaMasivaProcuradorDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/uploadFilePD", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> uploadFilePD(MultipartHttpServletRequest request) throws Exception{
		DeleteResponseDTO response = iCargaMasivaProcuradores.uploadFilePD(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/descargarFicheros", method = RequestMethod.POST, produces = "application/zip")
	ResponseEntity<InputStreamResource> descargarFicheros(@RequestBody List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem, HttpServletRequest request) {
		InputStreamResource response = iCargaMasivaProcuradores.descargarFicheros(cargaMasivaProcuradorItem, request);		
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CargaMasivaProcuradores.zip");
		header.setAccessControlExposeHeaders(cabecera);
		return new ResponseEntity<InputStreamResource>(response, header, HttpStatus.OK);	
	}

}
