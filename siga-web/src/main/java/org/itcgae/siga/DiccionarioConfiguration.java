package org.itcgae.siga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.itcgae.siga.DTOs.gen.DiccionarioDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioItem;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmLenguajesExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiccionarioConfiguration{

	private static Logger LOGGER = LoggerFactory.getLogger(DiccionarioConfiguration.class);

	@Autowired
	AdmLenguajesMapper lenguajesMapper;
	
	@Autowired
	GenDiccionarioMapper diccionarioMapper;
		
	@Bean
    public DiccionarioDTO getDiccionario() {
    	
    	LOGGER.info("**************: GENERAMOS TODOS LOS DICCIONARIOS");
    	
    	DiccionarioDTO diccionarioCompleto = new DiccionarioDTO();
    	
    	List<AdmLenguajes> enumLanguages= getLanguages();
  		List<DiccionarioItem> diccionarioResponse = new ArrayList<DiccionarioItem>();
  		for(AdmLenguajes lang:enumLanguages){
  			String lenguajeExt = lang.getIdlenguaje();
  			GenDiccionarioExample example = new GenDiccionarioExample();
			example.createCriteria().andIdlenguajeEqualTo(lenguajeExt);
			example.setOrderByClause(" IDRECURSO ASC");
			List<GenDiccionario> diccionarios = diccionarioMapper.selectByExample(example);
			if (null != diccionarios && !diccionarios.isEmpty()) {
				DiccionarioItem diccionarioItemResponse = new DiccionarioItem();
				HashMap<String,HashMap<String, String>> diccionarioItem = new HashMap<String,HashMap<String, String>>();
				HashMap<String, String> diccionarioIndividual = new HashMap<String, String>();
				for(GenDiccionario diccionario: diccionarios){
					diccionarioIndividual.put(diccionario.getIdrecurso(),diccionario.getDescripcion());
				}
				diccionarioItem.put(lang.getIdlenguaje(),diccionarioIndividual);
				diccionarioItemResponse.setDiccionario(diccionarioItem);
				diccionarioResponse.add(diccionarioItemResponse);
			}
  		}
  		diccionarioCompleto.setDiccionarioItems(diccionarioResponse);
  		
  		LOGGER.info("**************: FIN GENERACIÓN TODOS LOS DICCIONARIOS");
		return diccionarioCompleto;
   	
    }
	
	private List<AdmLenguajes> getLanguages() {

		//Cargamos todos los lenguajes
		AdmLenguajesExample example = new AdmLenguajesExample();
		example.setOrderByClause(" IDLENGUAJE ASC");
		List<AdmLenguajes> lenguajes = lenguajesMapper.selectByExample(example );
		
		return lenguajes;
	}
	
	
	
}
