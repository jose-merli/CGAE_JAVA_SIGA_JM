package org.itcgae.siga.adm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.AdmPerfil;

public interface IPerfilService {

	
	public List<AdmPerfil>findAllPerfil();

	
	public List<AdmPerfil> findPerfilesByName(String name);


	public List<AdmPerfil> findAllPerfilSql();

	
	public List<AdmPerfil> getComboPerfil(String name);
	
	public ComboDTO getPerfiles(HttpServletRequest request);
}
