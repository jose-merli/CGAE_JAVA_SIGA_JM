package org.itcgae.siga.adm.controllers;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoEditDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoDTO;
import org.itcgae.siga.DTOs.adm.UsuarioLogeadoItem;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.services.ITestHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	

	
	@Autowired
	ITestHeadersService testHeadersService;
	
	@Autowired
	IMenuService menuService;
	
	@RequestMapping(value="/", produces="application/json")
	ResponseEntity<Status>health() {
		return new ResponseEntity<Status>(new Status("UP"), HttpStatus.OK);
	}
	
    @RequestMapping(value = "/headers", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String getCabeceras(HttpServletRequest httpRequest) {
        
    	String headers = testHeadersService.getHeadersService(httpRequest);

        return headers;
    }
    
    @RequestMapping(value = "/usuario/logeado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioLogeadoDTO>  getUserLog(HttpServletRequest httpRequest) {
        
    	UsuarioLogeadoDTO headers = menuService.getUserLog(httpRequest);

        return new ResponseEntity<UsuarioLogeadoDTO>(headers, HttpStatus.OK);
    }
    
	
}
