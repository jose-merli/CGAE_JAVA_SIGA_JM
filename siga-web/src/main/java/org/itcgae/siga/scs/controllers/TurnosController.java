package org.itcgae.siga.scs.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.servicesImpl.ComboServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		ComboDTO response = comboService.comboTiposTurno(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboZonas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboZonas(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboSubZonas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboSubZonas(String idZona, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = comboService.getComboSubZonas(request,idZona);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboPartidaPresupuestaria",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPartidaPresupuestaria(HttpServletRequest request) {
		ComboDTO response = comboService.getComboPartidasPresupuestarias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboGruposFacturacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboGruposFacturacion(HttpServletRequest request) {
		ComboDTO response = comboService.getComboGrupoFacturacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/gestionTiposAsistencia/busquedaTiposAsistencia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<TiposAsistenciasDTO> searchTiposAsistencia(@RequestParam("historico") boolean historico, HttpServletRequest request) {
//		TiposAsistenciasDTO response = TiposAsistenciaService.searchTiposAsistencia(historico, request);
//		return new ResponseEntity<TiposAsistenciasDTO>(response, HttpStatus.OK);
//	}
	
}