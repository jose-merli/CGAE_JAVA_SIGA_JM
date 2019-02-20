package org.itcgae.siga.com.services;

import java.util.HashMap;
import java.util.List;

import org.itcgae.siga.DTOs.com.DatosDocumentoItem;

import com.aspose.words.Document;

public interface IGeneracionDocumentosService {

	public Document sustituyeDocumento(Document doc, HashMap<String, Object> dato) throws Exception;

	public Document sustituyeRegionDocumento(Document doc, String region, List dato) throws Exception;

	public DatosDocumentoItem grabaDocumento(Document doc, String pathfinal, String nombrefichero, boolean firmado) throws Exception;

	public Document sustituyeDatos(Document doc, HashMap<String, Object> dato);

	
	
}
