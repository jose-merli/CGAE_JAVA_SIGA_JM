package org.itcgae.siga.gen.controllers;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.gen.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
	
	@Autowired
	IMenuService menuService;
    
    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MenuDTO> getMenu(HttpServletRequest request) {
    	MenuDTO response = menuService.getMenu(request);
    	return new ResponseEntity<MenuDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/instituciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getInstituciones() {
    	ComboDTO response = menuService.getInstituciones();
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    
    @RequestMapping(value = "/perfiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPerfiles(@RequestParam("institucion") String idInstitucion) {
    	ComboDTO response = menuService.getPerfiles(idInstitucion);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
//    @RequestMapping(value = "/perfilespost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	List<ComboDTO> getPerfilesPost(@RequestBody ComboDTORequest institucion) {
//    	List<ComboDTO> lista = menuService.getComboParametros(SigaConstants.COMBO_PERFILES,institucion);
//    	return lista;
//	}

    
    @RequestMapping(value = "/permisos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisoDTO> getPermisos(@RequestBody PermisoRequestItem permisoRequestItem,HttpServletRequest request) {
    	PermisoDTO response = menuService.getPermisos(permisoRequestItem,request);
    	return new ResponseEntity<PermisoDTO>(response, HttpStatus.OK);
	}
    
}
