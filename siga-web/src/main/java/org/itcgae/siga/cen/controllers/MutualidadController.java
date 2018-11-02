package org.itcgae.siga.cen.controllers;


import org.itcgae.siga.DTOs.cen.CuotaYCapObjetivoDTO;
import org.itcgae.siga.DTOs.cen.DatosSolicitudGratuitaDTO;
import org.itcgae.siga.DTOs.cen.EstadoMutualistaDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.MutualidadCombosDTO;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;
import org.itcgae.siga.cen.services.IMutualidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutualidad")
public class MutualidadController {
	
	
	@Autowired
	private IMutualidadService _mutualidadService;
	
	@RequestMapping(value="/estadoMutualista",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> getEstadoMutualista(@RequestBody EstadoMutualistaDTO estadoMutualistaDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.getEstadoMutualista(estadoMutualistaDTO);
		if(response!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/estadoSolicitud",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> getEstadoSolicitud(@RequestBody EstadoSolicitudDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.getEstadoSolicitud(estadoSolicitudDTO);
		if(response!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/enums",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadCombosDTO> getEnums() {
		
		MutualidadCombosDTO response = _mutualidadService.getEnums();
		if(response!=null)
			return new ResponseEntity<MutualidadCombosDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadCombosDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/solicitudPolizaAccuGratuitos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> solicitudPolizaAccuGratuitos(@RequestBody DatosSolicitudGratuitaDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.MGASolicitudPolizaAccuGratuitos(estadoSolicitudDTO);
		if(response.getIdSolicitudRespuesta()!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/solicitudPolizaProfesional",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> solicitudPolizaProfesional(@RequestBody DatosSolicitudGratuitaDTO estadoSolicitudDTO) {
		
		MutualidadResponseDTO response = _mutualidadService.MGASolicitudPolizaProfesional(estadoSolicitudDTO);
		if(response!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/obtenerCuotaYCapObjetivo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MutualidadResponseDTO> obtenerCuotaYCapObjetivo(@RequestBody CuotaYCapObjetivoDTO datosCuota) {
		
		MutualidadResponseDTO response = _mutualidadService.ObtenerCuotaYCapObjetivo(datosCuota);
		if(response!=null)
			return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<MutualidadResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

}
