package org.itcgae.siga.gen.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuDTORequest;
import org.itcgae.siga.db.entities.AdmTiposaccesoExample;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.entities.GenMenuExample;
import org.itcgae.siga.db.services.gen.mappers.GenMenuExtendsMapper;
import org.itcgae.siga.gen.services.IMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class MenuServiceImpl implements IMenuService {
	

	@Autowired
	GenMenuExtendsMapper menuExtend;


	@Override
	public List<MenuDTO> selectMenuByExample(MenuDTORequest example) {
		AdmTiposaccesoExample exampleMenu = new AdmTiposaccesoExample();
		List<MenuDTO> menuReturn = new ArrayList<MenuDTO>();
		exampleMenu.setDistinct(true);
		exampleMenu.setOrderByClause(" MENU.ORDEN ASC");
		exampleMenu.createCriteria().andIdinstitucionEqualTo(Short.valueOf(example.getIdInstitucion())).andIdperfilEqualTo(example.getIdPerfil()).andDerechoaccesoGreaterThan(Short.valueOf("1"));
		List<GenMenu> menuEntities = menuExtend.selectMenuByExample(exampleMenu );
		
		if (null != menuEntities && !menuEntities.isEmpty()) {
			for (Iterator iterator = menuEntities.iterator(); iterator.hasNext();) {
				GenMenu genMenu = (GenMenu) iterator.next();
				MenuDTO menu = new MenuDTO();
				menu.setId(Long.valueOf(genMenu.getIdmenu()));
				menu.setValue(genMenu.getIdparent());
				menuReturn.add(menu);
			}
		}
		
		return menuReturn;
		
		
	}
}
