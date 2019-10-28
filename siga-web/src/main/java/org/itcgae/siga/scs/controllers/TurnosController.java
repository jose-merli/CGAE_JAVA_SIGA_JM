package org.itcgae.siga.scs.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AreasDTO;
import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTO.scs.ComisariaDTO;
import org.itcgae.siga.DTO.scs.ComisariaItem;
import org.itcgae.siga.DTO.scs.MateriasDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.service.IComisariasService;
import org.itcgae.siga.scs.service.IFichaAreasService;
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
@RequestMapping(value = "/oficio")
public class TurnosController {
	
	@Autowired
	private IComisariasService comisariasService;

	@RequestMapping(value = "/gestionTurnos/searchTurnos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComisariaDTO> searchCourt(@RequestBody ComisariaItem ComisariaItem, HttpServletRequest request) {
		ComisariaDTO response = comisariasService.searchComisarias(ComisariaItem, request);
		return new ResponseEntity<ComisariaDTO>(response, HttpStatus.OK);
	}
}
