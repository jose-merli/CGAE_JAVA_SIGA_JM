package org.itcgae.siga.gen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;

public interface IMenuService {



	
	public MenuDTO getMenu(HttpServletRequest request);
	
	
	public  ComboDTO getInstituciones();
	
	
	public  ComboDTO getPerfiles(String idInstitucion);


	public PermisoDTO getPermisos(PermisoRequestItem permisoRequestItem, HttpServletRequest request);


}
