package org.itcgae.siga.com.services.impl;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.itcgae.siga.com.documentos.DataMailMergeDataSource;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.springframework.stereotype.Service;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;

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
			
			DocumentBuilder builder=new DocumentBuilder(doc);
			
			for(String clave : claves){
				while(builder.moveToMergeField(clave))
				{
					Object o = dato.get(clave);
					try {
						builder.write(o.toString().trim());	
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
	public File grabaDocumento(Document doc, String pathfinal, String nombrefichero) throws Exception{
		// nombrefichero incluye la extension .doc
		File archivo = null;
		try {
			doc.save(pathfinal + nombrefichero);
		} catch (Exception e) {
			throw e;
		}
		archivo = new File(pathfinal + nombrefichero);
		if (!archivo.exists())return null;

		return archivo;
	}

}
