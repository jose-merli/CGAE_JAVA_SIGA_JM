package org.itcgae.siga.adm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGrupoEditDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGruposDTO;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
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
	
	
	// Invalid CORS request => create a CORS Filter that will check Origin header
	// Invalid CORS request 403 => the request was a legal request, but the server is refusing to respond to it. Unlike a 401 Unauthorized response, authenticanting will make no difference.
	// check that the authentication is correct (DevAuthenticationFilter.java). Maybe don't allow patch method:
	// response.addHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
    // "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	//@CrossOrigin(allowCredentials = "false")
	@RequestMapping(value = "/usuarios/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateUsers(@RequestBody UsuarioUpdateDTO usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateUsers(usuarioUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	
 	@RequestMapping(value = "/usuarios/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
 	ResponseEntity<CreateResponseDTO> createUsers(@RequestBody UsuarioCreateDTO usuarioCreateDTO, HttpServletRequest request) { 
		CreateResponseDTO response = gestionUsuariosGruposService.createUsers(usuarioCreateDTO, request);
		return new ResponseEntity<CreateResponseDTO>(response, HttpStatus.OK);
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
	ResponseEntity<DeleteResponseDTO> deleteGroupsUsers(@RequestBody UsuarioDeleteDTO[] usuarioDeleteDTO, HttpServletRequest request) {
		DeleteResponseDTO response = gestionUsuariosGruposService.deleteUsersGroup(usuarioDeleteDTO, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuariosgrupos/historico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UsuarioGruposDTO> getUsersGroupsHistoric(@RequestParam("numPagina") int numPagina , HttpServletRequest request) { 
		UsuarioGruposDTO response = gestionUsuariosGruposService.getUsersGroupsHistoric(numPagina, request);
		return new ResponseEntity<UsuarioGruposDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuariosgrupos/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGroupUsers(@RequestBody UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateGroupUsers(usuarioUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/usuariosgrupos/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> createGroupUsers(@RequestBody UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateGroupUsers(usuarioUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/usuariosgrupos/updateGrupoDefecto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGrupoDefecto(@RequestBody UsuarioGrupoEditDTO usuarioUpdateDTO, HttpServletRequest request) {
		UpdateResponseDTO response = gestionUsuariosGruposService.updateGrupoDefecto(usuarioUpdateDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
}
