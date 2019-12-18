package org.itcgae.siga.gen.controllers;


import java.security.cert.CertificateEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.EntornoDTO;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.services.IFusionadorPersonasServerService;
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
	private IMenuService menuService;
    
	@Autowired
	private IFusionadorPersonasServerService fusionadorService;
	
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
    @RequestMapping(value = "/validaInstitucion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> validaInstitucion( HttpServletRequest request) {
    	UpdateResponseDTO response = menuService.validaInstitucion(request);
    	return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/perfiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPerfiles(@RequestParam("institucion") String idInstitucion) {
    	ComboDTO response = menuService.getPerfiles(idInstitucion);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    
    @RequestMapping(value = "/permisos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisoDTO> getPermisos(@RequestBody PermisoRequestItem permisoRequestItem,HttpServletRequest request) throws CertificateEncodingException {
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
    
    
    @RequestMapping(value = "/getInstitucionActual", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboItem> getInstitucionActual(HttpServletRequest request) {
    	ComboItem response = menuService.getInstitucionActual(request);
       	return new ResponseEntity<ComboItem>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value = "/getLetrado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboItem> getLetrado(HttpServletRequest request) {
    	ComboItem response = menuService.getLetrado(request);
       	return new ResponseEntity<ComboItem>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value = "/isColegiado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ColegiadoItem> isColegiado(HttpServletRequest request) {
    	ColegiadoItem response = menuService.isColegiado(request);
       	return new ResponseEntity<ColegiadoItem>(response, HttpStatus.OK);
   	}

    
}
