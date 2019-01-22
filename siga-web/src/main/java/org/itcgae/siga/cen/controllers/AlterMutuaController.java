package org.itcgae.siga.cen.controllers;


import org.itcgae.siga.DTOs.cen.AlterMutuaResponseDTO;
import org.itcgae.siga.DTOs.cen.EstadoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.PropuestasDTO;
import org.itcgae.siga.DTOs.cen.SolicitudDTO;
import org.itcgae.siga.cen.services.IAlterMutuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/alterMutua")
public class AlterMutuaController {

	
	
	@Autowired
	private IAlterMutuaService _alterMutuaService;

	
	@RequestMapping(value="/estadoSolicitud",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AlterMutuaResponseDTO> getEstadoSolicitud(@RequestBody EstadoSolicitudDTO estadoSolicitudDTO) {
		
		AlterMutuaResponseDTO response = _alterMutuaService.getEstadoSolicitud(estadoSolicitudDTO);
		if(response != null)
			return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value="/estadoColegiado",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AlterMutuaResponseDTO> getEstadoColegiado(@RequestBody EstadoColegiadoDTO estadoDTO) {
		
		AlterMutuaResponseDTO response = _alterMutuaService.getEstadoColegiado(estadoDTO);
		if(response != null)
			return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value="/propuestas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AlterMutuaResponseDTO> getPropuestas(@RequestBody PropuestasDTO propuestas) {
		
		AlterMutuaResponseDTO response = _alterMutuaService.getPropuestas(propuestas);
		if(response != null)
			return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/tarifaSolicitud",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AlterMutuaResponseDTO> getTarifaSolicitud(@RequestBody SolicitudDTO solicitud) {
		
		AlterMutuaResponseDTO response = _alterMutuaService.getTarifaSolicitud(solicitud);
		if(response.isError()!= true)
			return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value="/solicitudAlter",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AlterMutuaResponseDTO> setSolicitudAlter(@RequestBody SolicitudDTO solicitud) {
		
		AlterMutuaResponseDTO response = _alterMutuaService.setSolicitudAlter(solicitud);
		if(response != null)
			return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<AlterMutuaResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	

}
