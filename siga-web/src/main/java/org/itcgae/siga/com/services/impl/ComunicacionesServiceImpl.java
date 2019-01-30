package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.DestinatariosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IComunicacionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConClaseComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestinatariosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComunicacionesServiceImpl implements IComunicacionesService {

	private Logger LOGGER = Logger.getLogger(ComunicacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;
	
	@Autowired
	private ConClaseComunicacionExtendsMapper _conClaseComunicacionExtendsMapper;
	
	@Autowired
	private EnvDestinatariosExtendsMapper _envDestinatariosExtendsMapper;
	
	@Autowired
	private ModModeloComunicacionExtendsMapper _modModeloComunicacionExtendsMapper;


	/**Realiza la busqueda de comunicaciones **/
	@Override
 	public EnviosMasivosDTO comunicacionesSearch(HttpServletRequest request, EnviosMasivosSearch filtros) {
		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");

		EnviosMasivosDTO enviosMasivos = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosItemList = new ArrayList<EnviosMasivosItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {
					enviosItemList = _envEnviosExtendsMapper.selectEnviosComunicacionSearch(idInstitucion, usuario.getIdlenguaje(), filtros);
					if (enviosItemList.size() > 0) {
						enviosMasivos.setEnviosMasivosItem(enviosItemList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					e.printStackTrace();
					enviosMasivos.setError(error);
				}

			}
		}

		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");
		return enviosMasivos;
	}

	

	/**Duplica el envío pero mandandolo a Envios masivos**/
	@Override
	public Error reenviar(HttpServletRequest request, EnviosMasivosItem[] envio) {
		LOGGER.info("reenviar() -> Entrada al servicio para reenviar");
		
		// TODO Auto-generated method stub
		LOGGER.info("reenviar() -> Salida del servicio para reenviar");
		return null;
	}



	/**Obtiene los destinatarios del envío, para cargar los destinatarios en la ficha**/
	@Override
	public DestinatariosDTO detalleDestinatarios(HttpServletRequest request, String idEnvio) {
		LOGGER.info("detalleDestinatarios() -> Entrada al servicio para obtener el detalle de los destinatarios del envio");
		
		DestinatariosDTO respuesta = new DestinatariosDTO();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		try{
			
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				
				if (null != usuarios && usuarios.size() > 0) {
					
					List<DestinatarioItem> destinatarios = _envDestinatariosExtendsMapper.selectDestinatarios(idInstitucion, idEnvio);
					if(destinatarios.size()>0){
						respuesta.setDestinatarios(destinatarios);
					}
					
				}
				
			}
		
		}catch(Exception e){
			Error error = new Error();
			error.setCode(500);
			error.setDescription("Internal Server error");
			error.setMessage(e.getMessage());
			respuesta.setError(error);
			e.printStackTrace();
		}
		
		LOGGER.info("detalleDestinatarios() -> Salida del servicio para obtener el detalle de los destinatarios de envio");
		return respuesta;
	}



	@Override
	public ComboDTO claseComunicacion(HttpServletRequest request) {
		LOGGER.info("claseComunicacion() -> Entrada al servicio para obtener combo clases comunicacion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _conClaseComunicacionExtendsMapper.selectTipoClaseComunicacion();
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		
		LOGGER.info("claseComunicacion() -> Salida del servicio para obtener combo clases comunicacion");
		
		
		return comboDTO;
	}

	@Override
	public ComboDTO modelosClasesComunicacion(HttpServletRequest request, String idClaseComunicacion) {

		
		LOGGER.info("modelosClasesComunicacion() -> Entrada al servicio para obtener combo modelos de comunicacion de las clases comunicacion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _modModeloComunicacionExtendsMapper.selectModelosClasesComunicacion(idClaseComunicacion);
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		LOGGER.info("modelosClasesComunicacion() -> Salida del servicio para obtener combo modelos de comunicacion de las clases comunicacion");
		return comboDTO;
	}
}

