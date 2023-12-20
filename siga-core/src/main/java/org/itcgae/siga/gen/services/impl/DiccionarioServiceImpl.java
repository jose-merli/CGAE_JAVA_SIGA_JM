package org.itcgae.siga.gen.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmLenguajesExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.gen.services.IDiccionarioService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiccionarioServiceImpl implements IDiccionarioService {

	private Logger LOGGER = Logger.getLogger(DiccionarioServiceImpl.class);
	
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
	
	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;
	
	@Autowired
	private DiccionarioDTO getDiccionario;
	
	@Override
	public DiccionarioDTO getDiccionario(String lenguaje,HttpServletRequest request) {
		
		DiccionarioDTO diccionario = getDiccionario;
		
		
		if (lenguaje !=null && !"".equals(lenguaje)) {
			
			try {
			
				DiccionarioDTO diccionarioDTOFiltrado = new DiccionarioDTO();
				List<DiccionarioItem> diccionarioLstFilt = new ArrayList<DiccionarioItem>();
				DiccionarioItem diccItemFilt = new DiccionarioItem();
				HashMap<String,HashMap<String,String>> diccionarioFil = new HashMap<String, HashMap<String, String>>();
				
				List<DiccionarioItem> diccionarios = diccionario.getDiccionarioItems();
					diccionarioFil.put(lenguaje, diccionarios.get(Integer.parseInt(lenguaje)-1).getDiccionario().get(lenguaje));
					diccItemFilt.setDiccionario(diccionarioFil);
					diccionarioLstFilt.add(diccItemFilt);
					diccionarioDTOFiltrado.setDiccionarioItems(diccionarioLstFilt);
					
				
				return diccionarioDTOFiltrado;
			
			}catch(Exception e) {
				return getDiccionario;
			}
		}else {
			return getDiccionario;
		}
		
		
	
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
	
	@Override
	public ResponseDataDTO obtenerTinyApiKey(HttpServletRequest request) {
		LOGGER.info("obtenerTinyApiKey() -> Entrada al servicio para obtener la apiKey del editor de html");
		
		ResponseDataDTO response = new ResponseDataDTO();
		Error error = new Error();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					GenPropertiesKey key = new GenPropertiesKey();
					key.setFichero(SigaConstants.FICHERO_SIGA);
					key.setParametro(SigaConstants.TINY_APIKEY);
					
					GenProperties numMaximo = _genPropertiesMapper.selectByPrimaryKey(key);
					response.setData(numMaximo.getValor());
					
				}catch(Exception e){
					LOGGER.error("Error al obtener la apikey del editor", e);
					error.setCode(500);
					error.setDescription("Error al obtener la apikey del editor");
					error.setMessage(e.getMessage());
					response.setError(error);
				}					
			}
		}
		
		LOGGER.info("obtenerTinyApiKey() -> Salida del servicio para obtener la apiKey del editor de html");
		return response;
	}

}
