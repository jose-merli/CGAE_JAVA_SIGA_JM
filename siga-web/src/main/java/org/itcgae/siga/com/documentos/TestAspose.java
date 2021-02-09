package org.itcgae.siga.com.documentos;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.springframework.beans.factory.annotation.Autowired;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.License;

public class TestAspose {

	@Autowired
	static
	IGeneracionDocumentosService generacionDocumentosService;
	
	public static void main(String[] args) throws Exception {
		
		String rutaLicencia = "C:/FILERMSA1000/prueba/Aspose.Words.lic";
		
		License license = new License();
		
		try {
			license.setLicense(rutaLicencia);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String pathFichero = "C:/FILERMSA1000/prueba/allinone_ES";
		
		Document doc = new Document(pathFichero + ".doc");
		
		HashMap<String, Object> datosRemplazo = new HashMap<String, Object>();
		HashMap<String, Object> datoRow = new HashMap<String, Object>();		
		HashMap<String, Object> datoRegion = new HashMap<String, Object>();
		
		datoRow.put("NOMBRE_POBLACION_REPR_DEF", "Esta es la poblacion");		
		datosRemplazo.put("row", datoRow);
		
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String,Object>>();
		
		for(int i=0; i< 3; i++){
			datoRegion = new HashMap<String, Object>();
			datoRegion.put("CALIDAD_INTERESADO", "NOMBRE ABOGADO CONTRA " + i);
			lista.add(datoRegion);
		}
		
		datosRemplazo.put("defendido", lista);
		
		
		/*Set<String> claves = datosRemplazo.keySet();				
		
		for(String clave : claves){
			Object o = datosRemplazo.get(clave);
            if (o instanceof List) {
            	List aux = (List)o;
                doc = sustituyeRegionDocumento(doc,clave,aux);                   
            }
		}	
		
		for(String clave : claves){
			Object datosMap = (Object) datosRemplazo.get(clave);
			if (datosMap instanceof HashMap) {
				HashMap htRowDatosInforme = (HashMap) datosMap;
				doc = sustituyeDocumento(doc, htRowDatosInforme);
			}
		}*/
		doc = sustituyeDocumento(doc, datosRemplazo);
		File file = grabaDocumento(doc, pathFichero, new Date().getTime() + ".doc");
		System.out.println("Fichero generado " + file.getAbsolutePath());
		
	}
	
	public static File generarPlantillaDocumento(Document doc, String pathFichero){		
		
		try {
			doc.save(pathFichero + new Date().getTime() + ".doc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file = new File(pathFichero + new Date().getTime() + ".doc");

		return file;
	
	}
	
	public static File generarPlantillaDocumento(Document doc, String pathFichero, HashMap<String, Object> dato){		
		
		Set<String> claves = dato.keySet();

		File file = null;
		try {
			if(doc == null){
				doc = new Document(pathFichero + ".doc");
			}			
			
			for (String key : claves) {
				DocumentBuilder builder = new DocumentBuilder(doc);				
				do {
					if (builder.moveToMergeField(key)) // si lo encuentra
					{
						builder.write((String) dato.get(key));
					} else {
						break;
					}
				} while (true);
			}
			
			doc.save(pathFichero + new Date().getTime() + ".doc");
			file = new File(pathFichero);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	
	}
	
	public static Document sustituyeDocumento(Document doc, HashMap<String, Object> dato){

		try {
			
			Set<String> claves = dato.keySet();				
			
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
			e.printStackTrace();
		}
		return doc;
	}
	
	public static Document sustituyeDatos(Document doc, HashMap<String, Object> dato){

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
	
	public static Document sustituyeRegionDocumento(Document doc, String region, List dato) throws Exception{
		DataMailMergeDataSource dataMerge = new DataMailMergeDataSource(region, dato);

		try {
			// doc=new Document(pathPlantilla);
			doc.getMailMerge().executeWithRegions(dataMerge);
			// doc.save(rutaFinal+".doc");

		} catch (Exception e) {
			throw e;
		}
		return doc;
	}
	
	public static File grabaDocumento(Document doc, String pathfinal, String nombrefichero) throws Exception{
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
	
	
	
	public static Document sustituyeDocumentoTable(Document doc, Hashtable<String, Object> dato){

		try {
			
			Enumeration<String> claves2=dato.keys();
            while(claves2.hasMoreElements()){
                String clave = (String) claves2.nextElement();
                Object o = dato.get(clave);
                if (o instanceof Vector) {
                    Vector aux = (Vector)o;
                    doc = sustituyeRegionDocumento(doc,clave,aux);
                   
                }
            }
			
			Enumeration<String> claves=dato.keys();
			DocumentBuilder builder=new DocumentBuilder(doc);
			while(claves.hasMoreElements()){
				
				String clave = (String) claves.nextElement();
				while(builder.moveToMergeField(clave))
					{
						Object o = dato.get(clave);
						/*if (o instanceof Vector) {
							Vector aux = (Vector)o;
							doc = sustituyeRegionDocumento(doc,clave,aux);
						}else*/
//						if (!(o instanceof String)) {
//							o = o.toString();
//						}
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

}
