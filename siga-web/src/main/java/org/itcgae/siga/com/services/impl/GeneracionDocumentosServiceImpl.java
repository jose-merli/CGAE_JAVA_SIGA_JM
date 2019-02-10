package org.itcgae.siga.com.services.impl;


import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.com.documentos.DataMailMergeDataSource;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.springframework.stereotype.Service;

import com.aspose.words.DataSet;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.MailMergeCleanupOptions;

@Service
public class GeneracionDocumentosServiceImpl implements IGeneracionDocumentosService{

	private Logger LOGGER = Logger.getLogger(GeneracionDocumentosServiceImpl.class);
	
	@Override
	public Document sustituyeDocumento(Document doc, HashMap<String, Object> dato) throws Exception{

		try {
			
			Set<String> claves=dato.keySet();
			
			for(String clave : claves){
				Object o = dato.get(clave);
                if (o instanceof List) {
                	List aux = (List)o;
                    doc = sustituyeRegionDocumento(doc,clave,aux);                   
                }
			}
			
			for(String clave : claves){
				Object datosMap = (Object) dato.get(clave);
				if (datosMap instanceof HashMap) {
					HashMap htRowDatosInforme = (HashMap) datosMap;
					doc = sustituyeDatos(doc, htRowDatosInforme);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("GeneracionDocumentosServiceImpl.sustituyeDocumento :: Error al sustituir los datos del documento", e);
			throw e;
		}
		return doc;
	}
	
	@Override
	public Document sustituyeRegionDocumento(Document doc, String region, List dato) throws Exception{
		DataMailMergeDataSource dataMerge = new DataMailMergeDataSource(region, dato);

		try {
			doc.getMailMerge().executeWithRegions(dataMerge);
		} catch (Exception e) {
			LOGGER.error("GeneracionDocumentosServiceImpl.sustituyeRegionDocumento :: Error al sustituir región", e);
			throw e;
		}
		return doc;
	}
	
	@Override
	public Document sustituyeDatos(Document doc, HashMap<String, Object> dato){

		try {
			
			Set<String> claves=dato.keySet();
			
			doc.getMailMerge().setCleanupOptions(MailMergeCleanupOptions.REMOVE_CONTAINING_FIELDS | MailMergeCleanupOptions.REMOVE_EMPTY_PARAGRAPHS  | MailMergeCleanupOptions.REMOVE_UNUSED_REGIONS | MailMergeCleanupOptions.REMOVE_UNUSED_FIELDS);
			
			DocumentBuilder builder=new DocumentBuilder(doc);
			
			for(String clave : claves){
				while(builder.moveToMergeField(clave))
				{
					Object o = dato.get(clave);
					try {
						if(o != null){
							builder.write(o.toString().trim());	
						}else{
							builder.write("");
						}					
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	@Override
	public DatosDocumentoItem grabaDocumento(Document doc, String pathfinal, String nombrefichero) throws Exception{
		// nombrefichero incluye la extension .doc
		File archivo = null;
		DatosDocumentoItem documento = new DatosDocumentoItem();
		try {
			doc.save(pathfinal + nombrefichero);
		} catch (Exception e) {
			throw e;
		}
		archivo = new File(pathfinal + nombrefichero);
		if (!archivo.exists())return null;

		documento.setFileName(nombrefichero);
		documento.setDatos(Files.readAllBytes(archivo.toPath()));
		
		return documento;
	}

}
