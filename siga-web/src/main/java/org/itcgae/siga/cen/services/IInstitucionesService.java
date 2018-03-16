package org.itcgae.siga.cen.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuDTORequest;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.logger.MethodLogging;

public interface IInstitucionesService {



	
	public List<CenInstitucion> findAllInstitucion();
	public List<CenInstitucion> findInstitucionesByName(String name);
	public List<CenInstitucion> findAllInstitucionSql();
	public List<CenInstitucion> selectInstitucionPerfil();
	public List<CenInstitucion> getComboInstitucionPerfil();


}
