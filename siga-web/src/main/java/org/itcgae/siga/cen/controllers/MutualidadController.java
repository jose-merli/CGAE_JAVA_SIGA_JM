package org.itcgae.siga.cen.controllers;


import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;
import org.itcgae.siga.cen.services.IMutualidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutualidad")
public class MutualidadController {
	
	
	@Autowired
	private IMutualidadService _mutualidadService;
	
	@RequestMapping(value="/estadoMutualista",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> getEstadoMutualista(EstadoSolicitudDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.getEstadoMutualista();
		//TODO: se deja esta comprobacion de momento
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/estadoSolicitud",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> getEstadoSolicitud(EstadoSolicitudDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.getEstadoSolicitud();
		//TODO: se deja esta comprobacion de momento
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/enums",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> getEnums() {
		
		MutualidadResponseDTO response = _mutualidadService.getEnums();
		//TODO: se deja esta comprobacion de momento
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/solicitudPolizaAccuGratuitos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> solicitudPolizaAccuGratuitos(EstadoSolicitudDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.MGASolicitudPolizaAccuGratuitos();
		//TODO: se deja esta comprobacion de momento
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/solicitudPolizaProfesional",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> solicitudPolizaProfesional(EstadoSolicitudDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.MGASolicitudPolizaProfesional();
		//TODO: se deja esta comprobacion de momento
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/obtenerCuotaYCapObjetivo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> obtenerCuotaYCapObjetivo(EstadoSolicitudDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.ObtenerCuotaYCapObjetivo();
		//TODO: se deja esta comprobacion de momento
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

}
