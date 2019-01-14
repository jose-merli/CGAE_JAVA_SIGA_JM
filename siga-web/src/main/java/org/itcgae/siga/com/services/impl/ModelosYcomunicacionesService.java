package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelosYcomunicacionesService implements IModelosYcomunicacionesService{

	private Logger LOGGER = Logger.getLogger(ModelosYcomunicacionesService.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	
	@Autowired
	private ModModeloComunicacionExtendsMapper modModeloComunicacionExtendsMapper;

	
	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros) {
		
		LOGGER.info("modeloYComunicacionesSearch() -> Entrada al servicio para busqueda de modelos de comunicación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		DatosModelosComunicacionesDTO respuesta = new DatosModelosComunicacionesDTO();
		List<ModelosComunicacionItem> modelosItem = new ArrayList<ModelosComunicacionItem>();
		
		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			if (null != usuarios && usuarios.size() > 0) {
				try{
					modelosItem = modModeloComunicacionExtendsMapper.selectModulosComunicacion(filtros, false);	
					if(modelosItem != null && modelosItem.size()> 0){
						respuesta.setModelosComunicacionItem(modelosItem);
					}
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}				
			}
		}
		
		
		LOGGER.info("modeloYComunicacionesSearch() -> Salida del servicio para busqueda de modelos de comunicación");
		return respuesta;
	}

	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesHistoricoSearch(HttpServletRequest request,
			DatosModelosComunicacionesSearch filtros) {
		
		LOGGER.info("modeloYComunicacionesHistoricoSearch() -> Entrada al servicio para busqueda de modelos de comunicación con histórico");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		DatosModelosComunicacionesDTO respuesta = new DatosModelosComunicacionesDTO();
		List<ModelosComunicacionItem> modelosItem = new ArrayList<ModelosComunicacionItem>();
		
		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			if (null != usuarios && usuarios.size() > 0) {
				try{
					modelosItem = modModeloComunicacionExtendsMapper.selectModulosComunicacion(filtros, true);			
					if(modelosItem != null && modelosItem.size()> 0){
						respuesta.setModelosComunicacionItem(modelosItem);
					}
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}				
			}
		}
		
		
		LOGGER.info("modeloYComunicacionesHistoricoSearch() -> Salida del servicio para busqueda de modelos de comunicación con histórico");
		return respuesta;
	}


	@Override
	public Error duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion) {
		// TODO Auto-generated method stub
		return null;
	}

}
