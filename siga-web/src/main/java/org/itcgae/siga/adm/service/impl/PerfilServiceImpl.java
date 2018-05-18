package org.itcgae.siga.adm.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.itcgae.siga.adm.service.IPerfilService;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.logger.MethodLogging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class PerfilServiceImpl implements IPerfilService {
	
	private Logger LOGGER = Logger.getLogger(PerfilServiceImpl.class);
	
	@Autowired
	private AdmPerfilExtendsMapper perfil;
	
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


}
