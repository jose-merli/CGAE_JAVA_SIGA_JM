package org.itcgae.siga.adm.service;

import java.util.List;

import org.itcgae.siga.db.entities.AdmPerfil;

public interface IPerfilService {

	
	public List<AdmPerfil>findAllPerfil();

	
	public List<AdmPerfil> findPerfilesByName(String name);


	public List<AdmPerfil> findAllPerfilSql();

	
	public List<AdmPerfil> getComboPerfil(String name);
}
