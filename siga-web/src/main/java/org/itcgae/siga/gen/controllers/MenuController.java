package org.itcgae.siga.gen.controllers;


import java.security.cert.CertificateEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ControlRequestItem;
import org.itcgae.siga.DTOs.gen.EntornoDTO;
import org.itcgae.siga.DTOs.gen.LoginMultipleItem;
import org.itcgae.siga.DTOs.gen.MenuDTO;
import org.itcgae.siga.DTOs.gen.ParamsItem;
import org.itcgae.siga.DTOs.gen.PermisoDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.DTOs.gen.PermisoUpdateItem;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.services.IFusionadorPersonasServerService;
import org.itcgae.siga.gen.services.IProcesoService;
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
	private IProcesoService procesoService;
	
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
    
    @RequestMapping(value = "/validaUsuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> validaUsuario( HttpServletRequest request) {
    	UpdateResponseDTO response = menuService.validaUsuario(request);
    	return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/perfiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPerfiles(@RequestParam("institucion") String idInstitucion) {
    	ComboDTO response = menuService.getPerfiles(idInstitucion);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/institucionesUsuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getInstitucionesUsuario(HttpServletRequest request) {
    	ComboDTO response = menuService.getInstitucionesUsuario(request);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/rolesColegioUsuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getRolesUsuario(HttpServletRequest request, @RequestParam("institucion") String idInstitucion) {
    	ComboDTO response = menuService.getRolesUsuario(request, idInstitucion);
    	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/perfilesColegioRol", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPerfilesColegioRol(@RequestBody LoginMultipleItem loginMultipleItem, HttpServletRequest request) {
    	ComboDTO response = menuService.getPerfilesColegioRol(loginMultipleItem);
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
    
    @RequestMapping(value = "/accesControls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisoDTO> getAccesControls(@RequestBody List<ControlRequestItem> permisoRequestItem,HttpServletRequest request) {
    	PermisoDTO response = new PermisoDTO(); 
    	response = menuService.getVariosPermisos(permisoRequestItem,request);
    	return new ResponseEntity<PermisoDTO>(response, HttpStatus.OK);

	}
    
    @RequestMapping(value = "/accesControlUrl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  	ResponseEntity<PermisoDTO> getAccesUrl(@RequestBody String url,HttpServletRequest request) {
      	ControlRequestItem permisoRequestItem = new ControlRequestItem();
      	PermisoDTO response = new PermisoDTO();
      	permisoRequestItem.setIdProceso(procesoService.getIdProceso(url));      	
      	response = menuService.getAccessControl(permisoRequestItem,request);     	
      	return new ResponseEntity<PermisoDTO>(response, HttpStatus.OK);

  	}
    
    
    @RequestMapping(value = "/getInstitucionActual", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboItem> getInstitucionActual(HttpServletRequest request) {
    	ComboItem response = menuService.getInstitucionActual(request);
       	return new ResponseEntity<ComboItem>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value = "/getEnvParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ParamsItem> getEnvParams(HttpServletRequest request) {
    	ParamsItem response = menuService.getEnvParams(request);
       	return new ResponseEntity<ParamsItem>(response, HttpStatus.OK);
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

    @RequestMapping(value = "/getTokenOldSiga", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<StringDTO> getTokenOldSiga(HttpServletRequest request) {
    	StringDTO response = menuService.getTokenOldSiga(request);
       	return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
   	}
    
    /*@RequestMapping(value = "/eliminaCookie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> eliminaCookie( HttpServletRequest request) {
    	UpdateResponseDTO response = menuService.eliminaCookie(request);
    	ResponseEntity<UpdateResponseDTO> response2 = new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}*/
    @RequestMapping(value = "/eliminaCookie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private void eliminaCookie( HttpServletRequest request, HttpServletResponse response ) {
    	Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
          cookie.setMaxAge(0);
          cookie.setValue(null);
          cookie.setPath("/");          
          response.addCookie(cookie);
         }
    }
    
}
