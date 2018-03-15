package org.itcgae.siga.gen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;

public interface IMenuService {



	
	public MenuDTO getMenu(HttpServletRequest request);
	
	
	public  ComboDTO getInstituciones();
	
	
	public  ComboDTO getPerfiles(String idInstitucion);


}
