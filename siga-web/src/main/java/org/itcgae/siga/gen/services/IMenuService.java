package org.itcgae.siga.services;

import java.util.List;

import org.itcgae.siga.db.entities.CenInstitucion;

public interface IDbService {

	
	public List<CenInstitucion>findAllInstitucion();

	
	public List<CenInstitucion> findInstitucionesByName(String name);


	public List<CenInstitucion> findAllInstitucionSql();
	
	
	public List<CenInstitucion> selectInstitucionPerfil();
}
