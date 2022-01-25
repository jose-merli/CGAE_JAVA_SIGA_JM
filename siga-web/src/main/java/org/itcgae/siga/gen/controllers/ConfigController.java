package org.itcgae.siga.gen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioDTO;
import org.itcgae.siga.gen.services.IDiccionarioService;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
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

	@Autowired
	SigaUserDetailsService sigaUserDetailsService;

	@RequestMapping(value = "/diccionarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DiccionarioDTO> getDiccionarios(@RequestParam(value = "idioma", required = false) String idioma,
			HttpServletRequest request) {
		DiccionarioDTO response = new DiccionarioDTO();
		response = diccionarioService.getDiccionario(idioma, request);
		return new ResponseEntity<DiccionarioDTO>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/usuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UsuarioDTO> getUsuario(HttpServletRequest request) {
		UsuarioDTO response = new UsuarioDTO();
		response = diccionarioService.getUsuario(request);
		return new ResponseEntity<UsuarioDTO>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/recuperarApiKey", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> recuperarApiKey(HttpServletRequest request) {
		ResponseDataDTO response = diccionarioService.obtenerTinyApiKey(request);
		if (response.getError() == null)
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
