package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.DestinatariosDTO;
import org.itcgae.siga.DTOs.scs.DestinatariosItem;
import org.itcgae.siga.scs.services.maestros.IDestinatariosRetencionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/maestros")
public class DestinatariosRetencionesController {

	@Autowired
	private IDestinatariosRetencionesService DestinatariosRetencionesService;
	
		
	@RequestMapping(value = "/gestionDestinatariosRetenc/searchDestinatariosRetenc",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DestinatariosDTO> searchDestinatario(@RequestBody DestinatariosItem destinatariosItem, HttpServletRequest request) {
		DestinatariosDTO response = DestinatariosRetencionesService.searchDestinatario(destinatariosItem, request);
		return new ResponseEntity<DestinatariosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionDestinatariosRetenc/eliminateDestinatariosRetenc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteZones(@RequestBody DestinatariosDTO destinatariosDTO, HttpServletRequest request) {
		UpdateResponseDTO response = DestinatariosRetencionesService.deleteDestinatariosRetenc(destinatariosDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/gestionDestinatariosRetenc/updateDestinatariosRetenc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDestinatariosRetenc(@RequestBody DestinatariosDTO destinatariosDTO, HttpServletRequest request) {
		UpdateResponseDTO response = DestinatariosRetencionesService.updateDestinatariosRet(destinatariosDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionDestinatariosRetenc/createDestinatarioRetenc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createDestinatarioRetenc(@RequestBody DestinatariosItem destinatariosItem, HttpServletRequest request) {
		InsertResponseDTO response = DestinatariosRetencionesService.createDestinatarioRetenc(destinatariosItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	
}
