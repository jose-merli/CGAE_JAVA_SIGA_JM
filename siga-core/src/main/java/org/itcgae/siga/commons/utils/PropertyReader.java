package org.itcgae.siga.commons.utils;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class PropertyReader {
	static Hashtable propHT = new Hashtable();
	
	@Autowired
	static
	GenPropertiesMapper genPropertiesMapper;
	
	private final static Logger log = Logger.getLogger(PropertyReader.class);
	
	/**
	 * Metodo que devuelve un Properties(clave, valor) indicado por el parametro resource.
	 * 
	 * @param resource El properties que se quiere recuperar
	 * @return Properties indicado por resource
	 */
	public static Properties getProperties(SIGAReferences.RESOURCE_FILES resource){
		// Recupera de la hashtable el properties indicado
		Properties prop = (Properties)propHT.get(resource);
		// Si es nulo es porque todavia no se ha cargado en memoria
		if(prop==null){
			// Lo recuperamos de fichero o bbdd y lo cargamos en memoria
			prop = returnPropertyFile(resource);
			propHT.put(resource, prop);
		}
		// Finalmente devolvemos el properties pedido
		return prop;
	}
	
	/**
	 * M�todo que carga en memoria el fichero properties indicado por el parametro resource 
	 * 
	 * @param resource el fichero de propiedades que se quiere cargar
	 * @return
	 */
	private static Properties returnPropertyFile (SIGAReferences.RESOURCE_FILES resource){
		
		Properties prop = new Properties();
		InputStream is=null;
		
		//TODO//jbd// Hay que revisar la captura de excepciones
		
		try {
			//ClsLogging.writeFileLog(" **** Leyendo properties " + resource.name() + " ****",3);
			// Si el fichero properties es SIGA o LOG4J se recoge de base de datos
			if(	SIGAReferences.bbdd.equals(resource.getFileName()) ) {
				// Si se van a meter mas properties a bbdd hay que indicarlo aqui
			
				GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
				genPropertiesExample.createCriteria().andFicheroEqualTo(resource.name());
				
				
				List<GenProperties> lista = genPropertiesMapper.selectByExample(genPropertiesExample);
				if (lista != null) {
					for (GenProperties registro : lista) {
						prop.setProperty(registro.getParametro(), registro.getValor());
					}
				}
								
			}else{
				// Si no est� en base de datos se coge de fichero
				is=SIGAReferences.getInputReference(resource);
				prop.load(is);
			}
			
		} catch (Exception e) {
			//ClsLogging.writeFileLog("MISSING PROPERTY FILE!!! "+resource.getFileName(),3);
			log.error("Error en returnPropertyFile con par�metro " + resource, e);			
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception eee) {
				log.error("Error al cerrar el inputStream con par�metro " + resource, eee);
			}
		}
		
		// Se devuelve el properties ya cargado
		return prop;
	}
}
