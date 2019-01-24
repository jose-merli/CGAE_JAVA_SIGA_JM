package org.itcgae.siga.com.services.impl;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModClasecomunicacionRutaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DialogoComunicacionServiceImpl implements IDialogoComunicacionService{
	
	private Logger LOGGER = Logger.getLogger(DialogoComunicacionServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ModClasecomunicacionRutaExtendsMapper _modClasecomunicacionRutaExtendsMapper;
	
	@Autowired
	private ModModeloComunicacionExtendsMapper _modModeloComunicacionExtendsMapper;
	

	@Override
	public ComboDTO obtenerClaseComunicaciones(HttpServletRequest request, String rutaClaseComunicacion) {
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

				comboItems = _modClasecomunicacionRutaExtendsMapper.getClaseComunicaciones(rutaClaseComunicacion);
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
	public ModelosComunicacionSearch obtenerModelos(HttpServletRequest request, String[] idClaseComunicacion) {
		
		LOGGER.info("obtenerModelos() -> Entrada al servicio para obtener los modelos de comunicacion");
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ModelosComunicacionSearch respuesta = new ModelosComunicacionSearch();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (usuarios != null && usuarios.size() > 0) {
				try{
					List<ModelosComunicacionItem> modelos = _modModeloComunicacionExtendsMapper.selectModelosComunicacionDialogo(idClaseComunicacion);
					
					for (ModelosComunicacionItem modelosComunicacionItem : modelos) {
						ComboDTO comboDTO = new ComboDTO();
						List<ComboItem> comboItems = _modModeloComunicacionExtendsMapper.selectPlantillasModelos(modelosComunicacionItem.getIdModeloComunicacion());
						if(null != comboItems && comboItems.size() > 0) {
							ComboItem element = new ComboItem();
							element.setLabel("");
							element.setValue("");
							comboItems.add(0, element);
						}		
						comboDTO.setCombooItems(comboItems);
						modelosComunicacionItem.setPlantillas(comboDTO);
					}
					respuesta.setModelosComunicacionItems(modelos);
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setDescription("Error al obtener los modelos");
					error.setMessage(e.getMessage());
				}
			}
		}
		
		LOGGER.info("obtenerModelos() -> Salida del servicio para obtener los modelos de comunicacion");
		
		return respuesta;
	}



	@Override
	public ComboDTO obtenertipoEnvioPlantilla(HttpServletRequest request, String idPlantilla) {
		
		LOGGER.info("obtenertipoEnvioPlantilla() -> Entrada al servicio para obtener tipos de envio de la plantilla ");
		
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
				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _modModeloComunicacionExtendsMapper.selectTipoEnvioPlantilla(usuario.getIdlenguaje(), idPlantilla);
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		LOGGER.info("obtenertipoEnvioPlantilla() -> Salida del servicio para obtener tipos de envio de la plantilla ");
		
		return comboDTO;
	}
	
}
