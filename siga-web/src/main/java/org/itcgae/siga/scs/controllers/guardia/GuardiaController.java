
package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.guardia.GuardiasDTO;
import org.itcgae.siga.DTO.scs.guardia.GuardiasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardia")
public class GuardiaController {

	@Autowired
	GuardiasService guardiasService;
	
	@PostMapping(value = "/busquedaGuardia/searchGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> searchGuardias(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request){
		GuardiasDTO response= guardiasService.searchGuardias(guardiasItem,request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaGuardia/deleteGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.deleteGuardias(guardiasDTO,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/activateGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.activateGuardias(guardiasDTO,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	

	@GetMapping(value = "/busquedaGuardia/detalleGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> detalleGuardia(String idGuardia, HttpServletRequest request){
		GuardiasItem response= guardiasService.detalleGuardia(idGuardia,request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}
	
}