package org.itcgae.siga.gen.controllers;


import org.itcgae.siga.DTOs.gen.DiccionarioDTO;
import org.itcgae.siga.gen.services.IDiccionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
	
	@Autowired
	IDiccionarioService diccionarioService;
    
    @RequestMapping(value = "/diccionarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DiccionarioDTO> getDiccionarios(@RequestParam(value="idioma", required=false) String idioma) {
    	DiccionarioDTO response = new DiccionarioDTO(); 
    	response = diccionarioService.getDiccionario(idioma);
    	return new ResponseEntity<DiccionarioDTO>(response, HttpStatus.OK);

	}
    
}
