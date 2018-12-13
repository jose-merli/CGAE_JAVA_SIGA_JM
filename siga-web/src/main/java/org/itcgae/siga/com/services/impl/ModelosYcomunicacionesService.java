package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelosYcomunicacionesService implements IModelosYcomunicacionesService{

	private Logger LOGGER = Logger.getLogger(ModelosYcomunicacionesService.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	
	
	
	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros) {
		
		LOGGER.info("getTiposComunicacion() -> Entrada al servicio para obtener combo tipos de comunicación");


		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				
			}
		}
		
		
		LOGGER.info("getTiposComunicacion() -> Salida del servicio para obtener combo tipos de comunicación");
		return null;
	}
	
	
	@Override
	public ComboDTO getTiposComunicacion(HttpServletRequest request) {
		
		LOGGER.info("getTiposComunicacion() -> Entrada al servicio para obtener combo tipos de comunicación");


		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				
			}
		}
		
		
		LOGGER.info("getTiposComunicacion() -> Salida del servicio para obtener combo tipos de comunicación");
		return null;
	}


	@Override
	public ComboDTO claseComunicacion(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ComboDTO tipoEnvio(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesHistoricoSearch(HttpServletRequest request,
			DatosModelosComunicacionesSearch filtros) {
		// TODO Auto-generated method stub
		return null;
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
