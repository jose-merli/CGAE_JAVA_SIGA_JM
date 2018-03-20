package org.itcgae.siga.gen.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.itcgae.siga.DTOs.gen.DiccionarioDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioItem;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmLenguajesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.gen.services.IDiccionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






@Service
public class DiccionarioServiceImpl implements IDiccionarioService {

	
	@Autowired
	AdmLenguajesMapper lenguajesMapper;
	@Autowired
	GenRecursosMapper recursosMapper;
	
	@Override
	public DiccionarioDTO getRecursos(String lenguaje) {
		DiccionarioDTO response = new DiccionarioDTO();
		GenRecursosExample example = new GenRecursosExample();
		if (null != lenguaje && lenguaje != "") {
			List<DiccionarioItem> diccionarioResponse = new ArrayList<DiccionarioItem>();
			AdmLenguajesExample lenguajeExample = new AdmLenguajesExample();
			lenguajeExample.createCriteria().andCodigoextEqualTo(lenguaje);
			List<AdmLenguajes> admLenguajes = lenguajesMapper.selectByExample(lenguajeExample );
			if (null != admLenguajes && !admLenguajes.isEmpty()) {
				AdmLenguajes admLenguaje = admLenguajes.get(0);
				example.createCriteria().andIdlenguajeEqualTo(admLenguaje.getIdlenguaje());
				example.setOrderByClause(" IDRECURSO ASC");
				List<GenRecursos> recursos = recursosMapper.selectByExample(example);
				if (null != recursos && !recursos.isEmpty()) {
					DiccionarioItem diccionarioItem = new DiccionarioItem();
					HashMap<String,HashMap<String, String>> recursoItem = new HashMap<String,HashMap<String, String>>();
					HashMap<String, String> recursoIndividual = new HashMap<String, String>();
					for(GenRecursos recurso: recursos){
						recursoIndividual.put(recurso.getIdrecurso(),recurso.getDescripcion());
					}
					recursoItem.put(lenguaje,recursoIndividual);
					diccionarioItem.setDiccionario(recursoItem);
					diccionarioResponse.add(diccionarioItem);
					response.setDiccionarioItems(diccionarioResponse);
					return response;
				}else{
					return response;
				}
			}
		}else{
      		List<AdmLenguajes> enumLanguages= getLanguages();
      		List<DiccionarioItem> diccionarioResponse = new ArrayList<DiccionarioItem>();
      		for(AdmLenguajes lang:enumLanguages){
      			String lenguajeExt = lang.getIdlenguaje();
    			example.createCriteria().andIdlenguajeEqualTo(lenguajeExt);
    			example.setOrderByClause(" IDRECURSO ASC");
    			List<GenRecursos> recursos = recursosMapper.selectByExample(example);
    			if (null != recursos && !recursos.isEmpty()) {
    				DiccionarioItem diccionarioItem = new DiccionarioItem();
    				HashMap<String,HashMap<String, String>> recursoItem = new HashMap<String,HashMap<String, String>>();
					HashMap<String, String> recursoIndividual = new HashMap<String, String>();
					for(GenRecursos recurso: recursos){
						recursoIndividual.put(recurso.getIdrecurso(),recurso.getDescripcion());
					}
					recursoItem.put(lenguaje,recursoIndividual);
    				diccionarioItem.setDiccionario(recursoItem);
    				diccionarioResponse.add(diccionarioItem);
					response.setDiccionarioItems(diccionarioResponse);
					return response;
    			}else{
    				return response;
    			}
      		}
			response.setDiccionarioItems(diccionarioResponse);
			return response;
			
		}
		return response;
	
	}
/*
  		FileOutputStream outputStream=null;
  		BufferedWriter bufferedWriter = null;
    	try {
      		
      		OutputStreamWriter outputStreamWriter=null;
      		List<AdmLenguajes> enumLanguages= getLanguages();

      		for(AdmLenguajes lang:enumLanguages){
        		// RGG cambio de codigos de lenguaje
        		String lenguajeExt = lang.getCodigoext();
        		URLClassLoader cargador = (URLClassLoader)ClassLoader.getSystemClassLoader();
        		this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath(); 
        		//new File(Utils.class.getResource(SIGAReferences.RESOURCE_FILES.RECURSOS_DIR+"/ApplicationResources_"+lenguajeExt.toLowerCase()+".properties").getPath()File());
        		//outputStream= new FileOutputStream(Utils.class.getResource(SIGAReferences.RESOURCE_FILES.RECURSOS_DIR.getFileName()+"/ApplicationResources_"+lenguajeExt.toLowerCase()+".properties").getPath());
				
        		outputStream= new FileOutputStream("/ApplicationResources_"+lenguajeExt.toLowerCase()+".properties");
				outputStreamWriter=new OutputStreamWriter(outputStream);
				bufferedWriter = new BufferedWriter(outputStreamWriter);
	      		List<GenRecursos> enumRecursos= getRecursosDb(lang.getIdlenguaje());
	      		for (GenRecursos recurso:enumRecursos) {
	  				String key=(String) recurso.getIdrecurso();
	  				String value=(String) recurso.getDescripcion();
	  				bufferedWriter.write(key+"="+value+"\n");
	  				bufferedWriter.flush();
				}
				outputStream.close();
				bufferedWriter.close();
      		}
    	} catch (IOException e) {
      		throw new SigaExceptions("Error generando fichero, "+ e.toString(), "","","","");
    	} catch (Exception ex) {
      		throw new SigaExceptions("Error generando fichero, "+ ex.toString(), "","","","");
    	} finally {
    	    try {
				bufferedWriter.close();
    	        outputStream.close();
    	    } catch (Exception eee) {}
    	}
	}
		*/
		
		
	
		

	private List<AdmLenguajes> getLanguages() {


		AdmLenguajesExample example = new AdmLenguajesExample();
		example.setOrderByClause(" IDLENGUAJE ASC");
		List<AdmLenguajes> lenguajes = lenguajesMapper.selectByExample(example );
		
		return lenguajes;
	}

}
