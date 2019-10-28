package org.itcgae.siga.scs.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AreasDTO;
import org.itcgae.siga.DTO.scs.CosteFijoDTO;
import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTO.scs.TiposActuacionDTO;
import org.itcgae.siga.DTO.scs.TiposActuacionItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciasDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.scs.service.IGestionTiposActuacionService;
import org.itcgae.siga.scs.service.IGestionTiposAsistenciaService;
import org.itcgae.siga.servicesImpl.ComboServiceImpl;
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
	private ComboServiceImpl comboService;
	
	@RequestMapping(value = "/combossjcs/comboAreas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboAreas(HttpServletRequest request) {
		ComboDTO response = comboService.comboAreas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboMaterias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPopulation(String idArea, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = comboService.comboMaterias(request,idArea,dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboTiposTurno", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTiposTurno(String idLenguaje, HttpServletRequest request) { 
		ComboDTO response = comboService.comboTiposTurno(request,idLenguaje);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
}