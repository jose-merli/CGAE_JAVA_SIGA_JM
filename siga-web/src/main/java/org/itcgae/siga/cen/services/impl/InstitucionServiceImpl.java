package org.itcgae.siga.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.extend.CenInstitucionExtendsMapper;
import org.itcgae.siga.logger.MethodLogging;
import org.itcgae.siga.services.IDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DbServiceImpl implements IDbService {
	
	@Autowired
	CenInstitucionMapper institucion;
	
	@Autowired
	CenInstitucionExtendsMapper institucionExtend;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	@MethodLogging
	public List<CenInstitucion> findAllInstitucion() {
		return institucion.selectByExample(new CenInstitucionExample());
	}
	@Override
	@MethodLogging
	public List<CenInstitucion> findInstitucionesByName(String name) {
		CenInstitucionExample example = new CenInstitucionExample();
		example.createCriteria().andNombreEqualTo(name);
		
		return institucion.selectByExample(example);
	}

	@Override
	@MethodLogging
	public List<CenInstitucion> findAllInstitucionSql() {
		try {
			sqlSession.getConnection();
			return (List<CenInstitucion>) sqlSession.getConnection().prepareStatement("SELECT * FROM CEN_INSTITUCIONES").executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<CenInstitucion>();
		}
	}
	@Override
	@MethodLogging
	public List<CenInstitucion> selectInstitucionPerfil() {
		return institucionExtend.selectInstitucionPerfil();
	}

}
