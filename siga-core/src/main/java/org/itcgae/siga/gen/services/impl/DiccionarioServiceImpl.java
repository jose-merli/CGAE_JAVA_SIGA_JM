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
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
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
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
	@Autowired
	private CenClienteMapper cenClienteMapper;
	
	@Override
	public DiccionarioDTO getDiccionario(String lenguaje,HttpServletRequest request) {
		DiccionarioDTO response = new DiccionarioDTO();
		// Si nos viene un lenguaje predefinido lo cargamos
		if (null != lenguaje && !lenguaje.equals("")) {
			
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			
			CenPersonaExample examplePersona = new CenPersonaExample();
			examplePersona.createCriteria().andNifcifEqualTo(dni);
			List<CenPersona> personaList = cenPersonaMapper.selectByExample(examplePersona);
			
			CenColegiado colegiado = null;
			if(personaList.size() > 0){
				CenPersona persona = personaList.get(0);
				CenColegiadoKey key = new CenColegiadoKey();
				key.setIdinstitucion(idInstitucion);
				key.setIdpersona(persona.getIdpersona());
				colegiado = cenColegiadoMapper.selectByPrimaryKey(key);
				
			}
			CenCliente cliente = null;
			if(colegiado != null){
				CenClienteKey cke = new CenClienteKey();
				cke.setIdinstitucion(idInstitucion);
				cke.setIdpersona(colegiado.getIdpersona());
				cliente = cenClienteMapper.selectByPrimaryKey(cke);
			}
			
		
			
			List<DiccionarioItem> diccionarioResponse = new ArrayList<DiccionarioItem>();
			AdmLenguajesExample lenguajeExample = new AdmLenguajesExample();
			lenguajeExample.createCriteria().andCodigoextEqualTo(lenguaje.toUpperCase());
			List<AdmLenguajes> admLenguajes = lenguajesMapper.selectByExample(lenguajeExample);
			
			if (null != admLenguajes && !admLenguajes.isEmpty()) {
				
				AdmLenguajes admLenguaje = admLenguajes.get(0);
				GenDiccionarioExample example = new GenDiccionarioExample();
				
				if(cliente.getIdlenguaje()!= null){
					example.createCriteria().andIdlenguajeEqualTo(cliente.getIdlenguaje());
				}else{
					example.createCriteria().andIdlenguajeEqualTo(admLenguaje.getIdlenguaje());
				}
				
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
		
		
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		CenPersonaExample examplePersona = new CenPersonaExample();
		examplePersona.createCriteria().andNifcifEqualTo(dni);
		List<CenPersona> personaList = cenPersonaMapper.selectByExample(examplePersona);
		
		CenColegiado colegiado = null;
		if(personaList.size() > 0){
			CenPersona persona = personaList.get(0);
			CenColegiadoKey key = new CenColegiadoKey();
			key.setIdinstitucion(idInstitucion);
			key.setIdpersona(persona.getIdpersona());
			colegiado = cenColegiadoMapper.selectByPrimaryKey(key);
			
		}
		CenCliente cliente = null;
		if(colegiado != null){
			CenClienteKey cke = new CenClienteKey();
			cke.setIdinstitucion(idInstitucion);
			cke.setIdpersona(colegiado.getIdpersona());
			cliente = cenClienteMapper.selectByPrimaryKey(cke);
		}
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios .createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		
		UsuarioDTO response = new UsuarioDTO();
		
		List<UsuarioItem> usuarioItem = new ArrayList<>();

		UsuarioItem usuarioResponse = new UsuarioItem();
		
		usuarioResponse.setNif(usuario.getNif());
		usuarioResponse.setNombreApellidos(usuario.getDescripcion());
		if(cliente!= null){
			usuarioResponse.setIdLenguaje(cliente.getIdlenguaje());
		}else{
			usuarioResponse.setIdLenguaje(usuario.getIdlenguaje());
		}
		usuarioResponse.setIdLenguaje(usuario.getIdlenguaje());
		usuarioResponse.setIdInstitucion(String.valueOf(usuario.getIdinstitucion()));
		usuarioItem.add(usuarioResponse);
		response.setUsuarioItem(usuarioItem);
		
		return response;
	}

}
