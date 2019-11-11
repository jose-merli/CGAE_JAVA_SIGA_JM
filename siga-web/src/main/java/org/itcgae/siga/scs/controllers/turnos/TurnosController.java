package org.itcgae.siga.scs.controllers.turnos;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AreasDTO;
 
import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTO.scs.ComisariaDTO;
import org.itcgae.siga.DTO.scs.ComisariaItem;
import org.itcgae.siga.DTO.scs.MateriasDTO;
import org.itcgae.siga.DTO.scs.ModulosItem;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

 
import org.itcgae.siga.DTO.scs.CosteFijoDTO;
import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTO.scs.TiposActuacionDTO;
import org.itcgae.siga.DTO.scs.TiposActuacionItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciasDTO;
import org.itcgae.siga.DTO.scs.TurnosDTO;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.scs.service.IGestionTurnosService;
import org.itcgae.siga.scs.service.maestros.IGestionTiposActuacionService;
import org.itcgae.siga.scs.service.maestros.IGestionTiposAsistenciaService;
import org.itcgae.siga.servicesImpl.ComboServiceImpl;

import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
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
	private ComboService comboService;
	
	@Autowired
	private IGestionTurnosService turnosService;
	
	@RequestMapping(value = "/combossjcs/comboTurnos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTurnos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
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
	
	@RequestMapping(value = "/combossjcs/comboPartidasPresupuestaria",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPartidasPresupuestaria(HttpServletRequest request) {
		ComboDTO response = comboService.getComboPartidasPresupuestarias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combossjcs/comboGruposFacturacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboGruposFacturacion(HttpServletRequest request) {
		ComboDTO response = comboService.getComboGrupoFacturacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/turnos/busquedaTurnos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TurnosDTO> busquedaTurnos(@RequestBody TurnosItem turnosItem, HttpServletRequest request) {
		TurnosDTO response = turnosService.busquedaTurnos(turnosItem, request);
		return new ResponseEntity<TurnosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/turnos/eliminateTurnos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteZones(@RequestBody TurnosDTO turnosDTO, HttpServletRequest request) {
		UpdateResponseDTO response = turnosService.eliminateTurnos(turnosDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/turnos/busquedaFichaTurnos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TurnosDTO> busquedaFichaTurnos(@RequestBody TurnosItem turnosItem, HttpServletRequest request) {
		TurnosDTO response = turnosService.busquedaFichaTurnos(turnosItem, request);
		return new ResponseEntity<TurnosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/turnos/updateDatosGenerales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosGenerales(@RequestBody TurnosItem turnosItem, HttpServletRequest request) {
		UpdateResponseDTO response = turnosService.updateDatosGenerales(turnosItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/turnos/createnewTurno", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createTurnos(@RequestBody TurnosItem turnosItem, HttpServletRequest request) {
		InsertResponseDTO response = turnosService.createTurnos(turnosItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
 