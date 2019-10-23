package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CatalogoDeleteDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroDTO;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboCatalogoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IMaestroCatalogoService {


	public  ComboCatalogoDTO getTabla(HttpServletRequest request);

	public  CatalogoMaestroDTO  getDatosCatalogo(CatalogoRequestDTO catalogoRequest,HttpServletRequest request);
	
	public  UpdateResponseDTO  updateDatosCatalogo(CatalogoUpdateDTO catalogoUpdate,HttpServletRequest request);
	
	public  UpdateResponseDTO  deleteDatosCatalogo(CatalogoDeleteDTO catalogoDelete,HttpServletRequest request);
	
	public  UpdateResponseDTO  createDatosCatalogo(CatalogoUpdateDTO catalogoCreate,HttpServletRequest request);
	
	public  CatalogoMaestroDTO  getDatosCatalogoHistorico(CatalogoRequestDTO catalogoRequest,HttpServletRequest request);
	
	public  UpdateResponseDTO  activateDatosCatalogo(CatalogoDeleteDTO catalogoDelete,HttpServletRequest request);

}
