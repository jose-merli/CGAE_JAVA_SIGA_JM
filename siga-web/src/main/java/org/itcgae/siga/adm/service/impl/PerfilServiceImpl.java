package org.itcgae.siga.adm.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.itcgae.siga.adm.service.IPerfilService;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.logger.MethodLogging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class PerfilServiceImpl implements IPerfilService {
	
	@Autowired
	AdmPerfilExtendsMapper perfil;
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	@MethodLogging
	public List<AdmPerfil> findAllPerfil() {
		return perfil.selectByExample(new AdmPerfilExample());
	}
	@Override
	@MethodLogging
	public List<AdmPerfil> findPerfilesByName(String name) {
		AdmPerfilExample example = new AdmPerfilExample();
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(name));
		return perfil.selectByExample(example);
	}

	@Override
	@MethodLogging
	public List<AdmPerfil> findAllPerfilSql() {
		try {
			sqlSession.getConnection();
			return (List<AdmPerfil>) sqlSession.getConnection().prepareStatement("SELECT * FROM CEN_PerfilES").executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<AdmPerfil>();
		}
	}


	@Override
	@MethodLogging
	public List<AdmPerfil> getComboPerfil(String name) {
		AdmPerfilExample examplePerfil = new AdmPerfilExample();
		examplePerfil.createCriteria().andIdinstitucionEqualTo(Short.valueOf(name));
		examplePerfil.setOrderByClause("IDINSTITUCION ASC");
		return perfil.selectComboPerfilByExample(examplePerfil );
	}


}
