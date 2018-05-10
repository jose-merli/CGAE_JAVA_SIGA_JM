package org.itcgae.siga.gen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.HeaderLogoDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;

public interface IMenuService {



	
	public MenuDTO getMenu(HttpServletRequest request);
	
	
	public  ComboDTO getInstituciones();
	
	
	public  ComboDTO getPerfiles(String idInstitucion);


	public PermisoDTO getPermisos(PermisoRequestItem permisoRequestItem, HttpServletRequest request);


	public UpdateResponseDTO updatePermisos(PermisoUpdateItem permisoRequestItem, HttpServletRequest request);
	
	
	public UsuarioLogeadoDTO getUserLog( HttpServletRequest request);


	public PermisoDTO getAccessControl(ControlRequestItem permisoRequestItem, HttpServletRequest request);
	
	
    public HeaderLogoDTO getHeaderLogo(HttpServletRequest httpRequest);


}
