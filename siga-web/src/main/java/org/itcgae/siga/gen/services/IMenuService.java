package org.itcgae.siga.gen.services;

import java.util.List;

import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuDTORequest;

public interface IMenuService {



	
	public List<MenuDTO> selectMenuByExample(MenuDTORequest example);


}
