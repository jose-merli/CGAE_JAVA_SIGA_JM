package org.itcgae.siga.com.services;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.aspose.words.Document;

public interface IGeneracionDocumentosService {

	public Document sustituyeDocumento(Document doc, HashMap<String, Object> dato) throws Exception;

	public Document sustituyeRegionDocumento(Document doc, String region, List dato) throws Exception;

	public File grabaDocumento(Document doc, String pathfinal, String nombrefichero) throws Exception;

	public Document sustituyeDatos(Document doc, HashMap<String, Object> dato);

	
	
}
