package org.itcgae.siga.gen.controllers;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.EntornoDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;
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
    
	
	
    @RequestMapping(value = "/getEntorno", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EntornoDTO> getEntorno(HttpServletRequest request) {
    	EntornoDTO response = menuService.getEntorno(request);
    	return new ResponseEntity<EntornoDTO>(response, HttpStatus.OK);
	}
	
	
    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MenuDTO> getMenu(HttpServletRequest request) {
    	MenuDTO response = menuService.getMenu(request);
    	return new ResponseEntity<MenuDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/instituciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getInstituciones( HttpServletRequest request) {
    	ComboDTO response = menuService.getInstituciones(request);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    
    @RequestMapping(value = "/perfiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPerfiles(@RequestParam("institucion") String idInstitucion) {
    	ComboDTO response = menuService.getPerfiles(idInstitucion);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    
    @RequestMapping(value = "/permisos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisoDTO> getPermisos(@RequestBody PermisoRequestItem permisoRequestItem,HttpServletRequest request) {
    	PermisoDTO response = menuService.getPermisos(permisoRequestItem,request);
    	return new ResponseEntity<PermisoDTO>(response, HttpStatus.OK);
	}

    @RequestMapping(value = "/permisos/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updatePermisos(@RequestBody PermisoUpdateItem permisoRequestItem,HttpServletRequest request) {
    	UpdateResponseDTO response = menuService.updatePermisos(permisoRequestItem,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/accesControl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisoDTO> getAcces(@RequestBody ControlRequestItem permisoRequestItem,HttpServletRequest request) {
    	PermisoDTO response = new PermisoDTO(); 
    	response = menuService.getAccessControl(permisoRequestItem,request);
    	return new ResponseEntity<PermisoDTO>(response, HttpStatus.OK);

	}
    
}
