package org.itcgae.siga.gen.controllers;


import java.util.List;

import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.MenuDTORequest;
import org.itcgae.siga.gen.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
	
	@Autowired
	IMenuService menuService;
    
    @RequestMapping(value = "/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	List<MenuDTO> getMenu(@RequestBody MenuDTORequest institucion) {
    	List<MenuDTO> lista = menuService.selectMenuByExample(institucion);
    	return lista;
	}
	
}
