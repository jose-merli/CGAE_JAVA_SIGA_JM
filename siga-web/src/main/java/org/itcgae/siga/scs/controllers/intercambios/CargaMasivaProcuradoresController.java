package org.itcgae.siga.scs.controllers.intercambios;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.itcgae.siga.scs.controllers.remesas.RemesasController;
import org.itcgae.siga.scs.services.intercambios.ICargaMasivaProcuradores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
