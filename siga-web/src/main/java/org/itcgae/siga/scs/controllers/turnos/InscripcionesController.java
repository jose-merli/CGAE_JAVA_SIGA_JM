package org.itcgae.siga.scs.controllers.turnos;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.oficio.IGestionInscripcionesService;
import org.itcgae.siga.scs.services.oficio.IGestionTurnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oficio")
public class InscripcionesController {
	@Autowired
	private ComboService comboService;
	
	@Autowired
	private IGestionInscripcionesService inscripcionesService;
	
	@RequestMapping(value = "/inscripciones/comboTurnos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = inscripcionesService.comboTurnos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inscripciones/busquedaInscripciones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionesDTO> busquedaTurnos(@RequestBody InscripcionesItem inscripcionesItem, HttpServletRequest request) {
		InscripcionesDTO response = inscripcionesService.busquedaInscripciones(inscripcionesItem, request);
		return new ResponseEntity<InscripcionesDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inscripciones/updateSolicitarBaja", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateSolicitarBaja(@RequestBody InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = inscripcionesService.updateSolicitarBaja(inscripcionesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/inscripciones/busquedaTarjetaInscripciones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionesDTO> busquedaTarjetaInscripciones(@RequestBody InscripcionesItem inscripcionesItem, HttpServletRequest request) {
		InscripcionesDTO response = inscripcionesService.busquedaTarjetaInscripciones(inscripcionesItem, request);
		return new ResponseEntity<InscripcionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inscripciones/updateValidar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateValidar(@RequestBody InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = inscripcionesService.updateValidar(inscripcionesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/inscripciones/updateDenegar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDenegar(@RequestBody InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = inscripcionesService.updateDenegar(inscripcionesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/inscripciones/updateCambiarFecha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateCambiarFecha(@RequestBody InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = inscripcionesService.updateCambiarFecha(inscripcionesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
 
