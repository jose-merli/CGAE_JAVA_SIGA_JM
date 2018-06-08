package org.itcgae.siga.adm.controllers;



import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CatalogoDeleteDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroDTO;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboCatalogoDTO;
import org.itcgae.siga.adm.service.IMaestroCatalogoService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaestroCatalogosController {
	

	
	@Autowired
	IMaestroCatalogoService maestroCatalogoService;
	
   @RequestMapping(value = "/catmaestros/tabla", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboCatalogoDTO> getTabla(HttpServletRequest request) {
	   ComboCatalogoDTO response = maestroCatalogoService.getTabla(request);
    	return new ResponseEntity<ComboCatalogoDTO>(response, HttpStatus.OK);
	}
   
   
   @RequestMapping(value = "/catmaestros/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   ResponseEntity<CatalogoMaestroDTO> getCatalogos(@RequestBody CatalogoRequestDTO catalogo,HttpServletRequest request) {
	CatalogoMaestroDTO response = maestroCatalogoService.getDatosCatalogo(catalogo,request);
 	return new ResponseEntity<CatalogoMaestroDTO>(response, HttpStatus.OK);
	}
   
   @RequestMapping(value = "/catmaestros/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   ResponseEntity<UpdateResponseDTO> updateCatalogos(@RequestBody CatalogoUpdateDTO catalogo,HttpServletRequest request) {
	   UpdateResponseDTO response = maestroCatalogoService.updateDatosCatalogo(catalogo,request);
	   if(response.getStatus().equals(SigaConstants.OK))
		   return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	   else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
   
   @RequestMapping(value = "/catmaestros/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   ResponseEntity<UpdateResponseDTO> deleteCatalogos(@RequestBody CatalogoDeleteDTO catalogo,HttpServletRequest request) {
	   UpdateResponseDTO response = maestroCatalogoService.deleteDatosCatalogo(catalogo,request);
	   return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
   
   @RequestMapping(value = "/catmaestros/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   ResponseEntity<UpdateResponseDTO> createCatalogos(@RequestBody CatalogoUpdateDTO catalogo,HttpServletRequest request) {
	   UpdateResponseDTO response = maestroCatalogoService.createDatosCatalogo(catalogo,request);
	   if(response.getStatus().equals(SigaConstants.OK))
	   return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	   
	   else 
		   return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
   
   @RequestMapping(value = "/catmaestros/historico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   ResponseEntity<CatalogoMaestroDTO> getCatalogosHistorico(@RequestBody CatalogoRequestDTO catalogo,HttpServletRequest request) {
	CatalogoMaestroDTO response = maestroCatalogoService.getDatosCatalogoHistorico(catalogo,request);
 	return new ResponseEntity<CatalogoMaestroDTO>(response, HttpStatus.OK);
	}
	
}
