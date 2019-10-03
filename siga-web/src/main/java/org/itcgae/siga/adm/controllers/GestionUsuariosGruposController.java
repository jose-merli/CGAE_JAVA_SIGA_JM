package org.itcgae.siga.adm.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoEditDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoItem;
import org.itcgae.siga.DTOs.adm.UsuarioGruposDTO;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
import org.itcgae.siga.commons.constants.SigaConstants;
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
public class GestionUsuariosGruposController {

	@Autowired
	private IGestionUsuariosGruposService gestionUsuariosGruposService;
	
	
	@RequestMapping(value = "/usuarios/rol", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<ComboDTO> getUsersRole() {
		ComboDTO response = gestionUsuariosGruposService.getUsersRole();
	   	return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	 
	@RequestMapping(value = "/usuarios/perfil", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getUsersProfile(HttpServletRequest request) {
		ComboDTO response = gestionUsuariosGruposService.getUsersProfile(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuarios/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UsuarioDTO> getUsersSearch(@RequestParam("numPagina") int numPagina, @RequestBody UsuarioRequestDTO usuarioRequestDTO, HttpServletRequest request) { 
		UsuarioDTO response = gestionUsuariosGruposService.getUsersSearch(numPagina, usuarioRequestDTO, request);
		return new ResponseEntity<UsuarioDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuarios/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateUsers(@RequestBody ArrayList<UsuarioUpdateDTO> usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateUsers(usuarioUpdateDTO, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
 	@RequestMapping(value = "/usuarios/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
 	ResponseEntity<CreateResponseDTO> createUsers(@RequestBody UsuarioCreateDTO usuarioCreateDTO, HttpServletRequest request) { 
		CreateResponseDTO response = gestionUsuariosGruposService.createUsers(usuarioCreateDTO, request);
	 
		if(response.getStatus().equals(SigaConstants.OK)) {
			return  new ResponseEntity<CreateResponseDTO>(response, HttpStatus.OK);
		}else {
			return  new ResponseEntity<CreateResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
	
	@RequestMapping(value = "/usuarios/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> updateUsers(@RequestBody UsuarioDeleteDTO usuarioDeleteDTO, HttpServletRequest request) {
		DeleteResponseDTO response = gestionUsuariosGruposService.deleteUsers(usuarioDeleteDTO, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuariosgrupos/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UsuarioGruposDTO> getUsersGroupsSearch(@RequestParam("numPagina") int numPagina , HttpServletRequest request) { 
		UsuarioGruposDTO response = gestionUsuariosGruposService.getUsersGroupsSearch(numPagina, request);
		return new ResponseEntity<UsuarioGruposDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuariosgrupos/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteGroupsUsers(@RequestBody UsuarioGrupoDeleteDTO[] usuarioDeleteDTO, HttpServletRequest request) {
		DeleteResponseDTO response = gestionUsuariosGruposService.deleteUsersGroup(usuarioDeleteDTO, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuariosgrupos/historico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UsuarioGruposDTO> getUsersGroupsHistoric(@RequestParam("numPagina") int numPagina , HttpServletRequest request) { 
		UsuarioGruposDTO response = gestionUsuariosGruposService.getUsersGroupsHistoric(numPagina, request);
		return new ResponseEntity<UsuarioGruposDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuariosgrupos/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGroupUsers(@RequestBody UsuarioGrupoItem[] usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateGroupUsers(usuarioUpdateDTO, request);		
			return  new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuariosgrupos/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> createGroupUsers(@RequestBody UsuarioGrupoItem usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.createGroupUsers(usuarioUpdateDTO, request);
		if(response.getStatus().equals("OK")) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
		
	@RequestMapping(value = "/usuariosgrupos/updateGrupoDefecto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGrupoDefecto(@RequestBody UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateGrupoDefecto(usuarioUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
}
