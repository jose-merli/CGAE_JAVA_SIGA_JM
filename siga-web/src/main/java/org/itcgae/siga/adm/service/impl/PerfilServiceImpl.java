package org.itcgae.siga.adm.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.PerfilDTO;
import org.itcgae.siga.DTOs.com.PerfilesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.adm.service.IPerfilService;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.logger.MethodLogging;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class PerfilServiceImpl implements IPerfilService {
	
	private Logger LOGGER = Logger.getLogger(PerfilServiceImpl.class);
	
	@Autowired
	private AdmPerfilExtendsMapper perfil;	
	
	@Autowired
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	@MethodLogging
	public List<AdmPerfil> findAllPerfil() {
		LOGGER.info("findAllPerfil() -> Entrada al servicio para obtener todos los perfiles disponibles");
		LOGGER.info("findAllPerfil() -> Salida al servicio para obtener todos los perfiles disponibles");
		return perfil.selectByExample(new AdmPerfilExample());
	}
	@Override
	@MethodLogging
	public List<AdmPerfil> findPerfilesByName(String name) {
		LOGGER.info("findPerfilesByName() -> Entrada al servicio para obtener los perfiles disponibles por nombre");
		AdmPerfilExample example = new AdmPerfilExample();
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(name));
		
		LOGGER.info("findPerfilesByName() -> Salida del servicio para obtener los perfiles disponibles por nombre");
		return perfil.selectByExample(example);
	}

	@Override
	@MethodLogging
	public List<AdmPerfil> findAllPerfilSql() {
		LOGGER.info("findAllPerfilSql() -> Entrada al servicio para obtener todos los perfiles disponibles por sql");
		try {
			sqlSession.getConnection();
			LOGGER.info("findAllPerfilSql() -> Salida del servicio para obtener todos los perfiles disponibles por sql");
			return (List<AdmPerfil>) sqlSession.getConnection().prepareStatement("SELECT * FROM CEN_PerfilES").executeQuery();
			
		} catch (SQLException e) {
			LOGGER.error("findAllPerfilSql() -> Fallo al obtener la conexión sql");
			e.printStackTrace();
			return new ArrayList<AdmPerfil>();
		}
	}


	@Override
	@MethodLogging
	public List<AdmPerfil> getComboPerfil(String name) {
		LOGGER.info("getComboPerfil() -> Entrada al servicio para obtener los perfiles por defecto");
		AdmPerfilExample examplePerfil = new AdmPerfilExample();
		examplePerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(name));
		examplePerfil.setOrderByClause("IDINSTITUCION ASC");
		
		LOGGER.info("getComboPerfil() -> Salida del servicio para obtener los perfiles por defecto");
		return perfil.selectComboPerfilByExample(examplePerfil);
	}

	@Override
	public ComboDTO getPerfiles(HttpServletRequest request) {
		LOGGER.info("getPerfiles() -> Entrada al servicio para obtener los perfiles disponibles");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);			
			if (null != usuarios && usuarios.size() > 0) {
				comboItems = admPerfilExtendsMapper.selectListadoPerfiles(idInstitucion);
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
			}		
		}		
		
		LOGGER.info("getPerfiles() -> Salida del servicio para obtener los perfiles disponibles");
		return comboDTO;
	}

}
