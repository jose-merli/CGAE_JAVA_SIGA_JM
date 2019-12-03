package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.services.maestros.IPartidasJudicialesService;
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
public class PartidaJudicialController {

	@Autowired
	private IPartidasJudicialesService PartidaJudicialesService;
	
		
	@RequestMapping(value = "/gestionPartidosJudi/searchoPartidosJudi",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PartidasJudicialesDTO> searchPartida(@RequestBody PartidasJudicialesItem partidasJudicialesItem, HttpServletRequest request) {
		PartidasJudicialesDTO response = PartidaJudicialesService.searchPartida(partidasJudicialesItem, request);
		return new ResponseEntity<PartidasJudicialesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deletePartidosJudi/deletePartidosJudi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deletePartidasJudi(@RequestBody PartidasJudicialesDTO partidasjudicialesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = PartidaJudicialesService.deletePartidasJudi(partidasjudicialesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/gestionPartidosJudi/ComboPartidosJudi",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPartidosJudiciales(HttpServletRequest request) {
		ComboDTO response = PartidaJudicialesService.getPartidoJudicial(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionPartidosJudi/createPartidosJudi",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createPartidosJudi(@RequestBody PartidasJudicialesItem partidasJudicialesItem, HttpServletRequest request) {
		InsertResponseDTO response = PartidaJudicialesService.createPartidoJudi(partidasJudicialesItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
//	
//	@RequestMapping(value = "/gestionPartidasPres/updatePartidasPres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> updatePartidaPres(@RequestBody PartidasDTO partidasDTO, HttpServletRequest request) {
//		UpdateResponseDTO response = PartidaPresupuestariaService.updatePartidasPres(partidasDTO, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//	
//	@RequestMapping(value = "/gestionPartidasPres/createPartidasPres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<InsertResponseDTO> createPartidasPres(@RequestBody PartidasItem partidasItem, HttpServletRequest request) {
//		InsertResponseDTO response = PartidaPresupuestariaService.createPartidaPres(partidasItem, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	
}
