package org.itcgae.siga.scs.services.intercambios;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ICargaMasivaProcuradores {

	public InputStreamResource descargarModelo(HttpServletRequest request) 
			throws IOException, EncryptedDocumentException, InvalidFormatException;
	
}
