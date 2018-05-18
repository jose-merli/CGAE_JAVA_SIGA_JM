package org.itcgae.siga.adm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.HistoricoUsuarioDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IAuditoriaUsuariosService;
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
public class AuditoriaUsuariosController {
	
	@Autowired
	private IAuditoriaUsuariosService auditoriaUsuariosService;
	
	@RequestMapping(value = "auditoriaUsuarios/tipoAccion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getAuditUserActionType( HttpServletRequest request) {
		ComboDTO response = auditoriaUsuariosService.getActionType( request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "auditoriaUsuarios/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<HistoricoUsuarioDTO> auditUsersSearch(@RequestParam("numPagina") int numPagina, @RequestBody HistoricoUsuarioRequestDTO  historicoUsuarioRequestDTO , HttpServletRequest request){
		HistoricoUsuarioDTO response = auditoriaUsuariosService.auditUsersSearch(numPagina, historicoUsuarioRequestDTO, request);
		return new ResponseEntity<HistoricoUsuarioDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "auditoriaUsuarios/update",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> auditUsersUpdate(@RequestBody HistoricoUsuarioUpdateDTO  historicoUsuarioUpdateDTO , HttpServletRequest request){
		UpdateResponseDTO response = auditoriaUsuariosService.auditUsersUpdate(historicoUsuarioUpdateDTO, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	

}
