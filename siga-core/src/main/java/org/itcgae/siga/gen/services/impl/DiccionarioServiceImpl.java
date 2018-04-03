package org.itcgae.siga.gen.services.impl;

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
import org.itcgae.siga.gen.services.IDiccionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






@Service
public class DiccionarioServiceImpl implements IDiccionarioService {

	
	@Autowired
	AdmLenguajesMapper lenguajesMapper;
	@Autowired
	GenDiccionarioMapper diccionarioMapper;
	
	@Override
	public DiccionarioDTO getDiccionario(String lenguaje) {
		DiccionarioDTO response = new DiccionarioDTO();
		
		if (null != lenguaje && !lenguaje.equals("")) {
			List<DiccionarioItem> diccionarioResponse = new ArrayList<DiccionarioItem>();
			AdmLenguajesExample lenguajeExample = new AdmLenguajesExample();
			lenguajeExample.createCriteria().andCodigoextEqualTo(lenguaje.toUpperCase());
			List<AdmLenguajes> admLenguajes = lenguajesMapper.selectByExample(lenguajeExample );
			if (null != admLenguajes && !admLenguajes.isEmpty()) {
				AdmLenguajes admLenguaje = admLenguajes.get(0);
				GenDiccionarioExample example = new GenDiccionarioExample();
				example.createCriteria().andIdlenguajeEqualTo(admLenguaje.getIdlenguaje());
				example.setOrderByClause(" IDRECURSO ASC");
				List<GenDiccionario> diccionarios = diccionarioMapper.selectByExample(example);
				if (null != diccionarios && !diccionarios.isEmpty()) {
					DiccionarioItem diccionarioItemResponse = new DiccionarioItem();
					HashMap<String,HashMap<String, String>> diccionariosItem = new HashMap<String,HashMap<String, String>>();
					HashMap<String, String> diccionarioIndividual = new HashMap<String, String>();
					for(GenDiccionario diccionario: diccionarios){
						diccionarioIndividual.put(diccionario.getIdrecurso(),diccionario.getDescripcion());
					}
					diccionariosItem.put(admLenguaje.getCodigoext().toLowerCase(),diccionarioIndividual);
					diccionarioItemResponse.setDiccionario(diccionariosItem);
					diccionarioResponse.add(diccionarioItemResponse);
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
					diccionarioItem.put(lang.getCodigoext().toLowerCase(),diccionarioIndividual);
					diccionarioItemResponse.setDiccionario(diccionarioItem);
    				diccionarioResponse.add(diccionarioItemResponse);
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
        		//new File(Utils.class.getResource(SIGAReferences.RESOURCE_FILES.diccionarioS_DIR+"/ApplicationResources_"+lenguajeExt.toLowerCase()+".properties").getPath()File());
        		//outputStream= new FileOutputStream(Utils.class.getResource(SIGAReferences.RESOURCE_FILES.diccionarioS_DIR.getFileName()+"/ApplicationResources_"+lenguajeExt.toLowerCase()+".properties").getPath());
				
        		outputStream= new FileOutputStream("/ApplicationResources_"+lenguajeExt.toLowerCase()+".properties");
				outputStreamWriter=new OutputStreamWriter(outputStream);
				bufferedWriter = new BufferedWriter(outputStreamWriter);
	      		List<GenDiccionario> enumdiccionarios= getdiccionariosDb(lang.getIdlenguaje());
	      		for (GenDiccionario diccionario:enumdiccionarios) {
	  				String key=(String) diccionario.getIddiccionario();
	  				String value=(String) diccionario.getDescripcion();
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
