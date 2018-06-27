package org.itcgae.siga.cen.services;

import java.util.List;

import org.itcgae.siga.db.entities.CenInstitucion;

public interface IInstitucionesService {



	
	public List<CenInstitucion> findAllInstitucion();
	public List<CenInstitucion> findInstitucionesByName(String name);
	public List<CenInstitucion> findAllInstitucionSql();
	public List<CenInstitucion> selectInstitucionPerfil();
	public List<CenInstitucion> getComboInstitucionPerfil();
	public List<CenInstitucion> getidInstitucionByCodExterno(String codExterno);
	public List<CenInstitucion> getCodExternoByidInstitucion(String idInstitucion);
	


}
