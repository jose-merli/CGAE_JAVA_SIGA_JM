package org.itcgae.siga.scs.services.intercambios;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorItem;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Primary
public interface ICargaMasivaProcuradores {

	public InputStreamResource descargarModelo(HttpServletRequest request) 
			throws IOException, EncryptedDocumentException, InvalidFormatException;

	public CargaMasivaProcuradorDTO listado(CargaMasivaProcuradorBusquedaItem cargaMasivaItem, HttpServletRequest request);

	public DeleteResponseDTO cargarFichero(MultipartHttpServletRequest request) throws BusinessException, IOException, Exception;

	public ResponseEntity<InputStreamResource> descargarFicheros(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem, HttpServletRequest request);
	
}
