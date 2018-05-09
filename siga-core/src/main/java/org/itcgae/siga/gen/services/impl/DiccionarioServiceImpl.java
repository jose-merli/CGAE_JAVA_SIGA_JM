package org.itcgae.siga.gen.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.gen.DiccionarioDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioItem;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmLenguajesExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.gen.services.IDiccionarioService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






@Service
public class DiccionarioServiceImpl implements IDiccionarioService {

	
	@Autowired
	AdmLenguajesMapper lenguajesMapper;
	@Autowired
	GenDiccionarioMapper diccionarioMapper;
	
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;
	
	@Override
	public DiccionarioDTO getDiccionario(String lenguaje) {
		DiccionarioDTO response = new DiccionarioDTO();
		// Si nos viene un lenguaje predefinido lo cargamos
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
			//En caso de no tener un lenguaje predefinido, los cargamos todos para generar el diccionario en sesión
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
					diccionarioItem.put(lang.getIdlenguaje(),diccionarioIndividual);
					diccionarioItemResponse.setDiccionario(diccionarioItem);
    				diccionarioResponse.add(diccionarioItemResponse);
    			}
      		}
			response.setDiccionarioItems(diccionarioResponse);
			return response;
			
		}
		return response;
	
	}

		

	private List<AdmLenguajes> getLanguages() {

		//Cargamos todos los lenguajes
		AdmLenguajesExample example = new AdmLenguajesExample();
		example.setOrderByClause(" IDLENGUAJE ASC");
		List<AdmLenguajes> lenguajes = lenguajesMapper.selectByExample(example );
		
		return lenguajes;
	}



	@Override
	public UsuarioDTO getUsuario(HttpServletRequest request) {
		// TODO Auto-generated method stub
		

		// Obtener idInstitucion del certificado y idUsuario del certificado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios .createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		
		UsuarioDTO response = new UsuarioDTO();
		
		List<UsuarioItem> usuarioItem = new ArrayList<>();

		UsuarioItem usuarioResponse = new UsuarioItem();
		
		usuarioResponse.setNif(usuario.getNif());
		usuarioResponse.setNombreApellidos(usuario.getDescripcion());
		usuarioResponse.setIdLenguaje(usuario.getIdlenguaje());
		usuarioResponse.setIdInstitucion(String.valueOf(usuario.getIdinstitucion()));
		usuarioItem.add(usuarioResponse);
		
		
		response.setUsuarioItem(usuarioItem );
		
		return response;
	}

}
