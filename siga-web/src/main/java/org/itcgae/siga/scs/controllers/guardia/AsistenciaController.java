package org.itcgae.siga.scs.controllers.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseDTO;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.scs.services.guardia.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/asistencia")
public class AsistenciaController {

	@Autowired
	private AsistenciaService asistenciaService;
	
	@GetMapping(value = "/turnosByColegiadoFecha")
	public ResponseEntity<ComboDTO> getTurnosByColegiadoFecha(HttpServletRequest request, 
			@RequestParam String guardiaDia,
			@RequestParam(required = false) String idPersona) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getTurnosByColegiadoFecha(request, guardiaDia, idPersona);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getTiposAsistencia")
	public ResponseEntity<ComboDTO> getTiposAsistencia(HttpServletRequest request, 
			@RequestParam String idTurno,
			@RequestParam String idGuardia) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getTiposAsistenciaColegio(request, idTurno, idGuardia);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getColegiadosGuardiaDia")
	public ResponseEntity<ComboDTO> getColegiadosGuardiaDia(HttpServletRequest request, 
			@RequestParam String idTurno,
			@RequestParam String idGuardia,
			@RequestParam String guardiaDia) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getColegiadosGuardiaDia(request, idTurno, idGuardia, guardiaDia);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/buscarAsistencias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TarjetaAsistenciaResponseDTO> searchAsistencias(HttpServletRequest request, @RequestBody FiltroAsistenciaItem filtro) {
		TarjetaAsistenciaResponseDTO response = null;
		try {
			response = asistenciaService.searchAsistencias(request, filtro);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaAsistenciaResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/buscarAsistenciasByIdSolicitud")
	public ResponseEntity<TarjetaAsistenciaResponseDTO> buscarAsistenciasByIdSolicitud(HttpServletRequest request, 
			@RequestParam String idSolicitud) {
		TarjetaAsistenciaResponseDTO response = null;
		try {
			response = asistenciaService.searchAsistenciasByIdSolicitud(request, idSolicitud);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<TarjetaAsistenciaResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getJuzgados")
	public ResponseEntity<ComboDTO> getJuzgados(HttpServletRequest request, 
			@RequestParam String idTurno) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getJuzgados(request, idTurno);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getComisarias")
	public ResponseEntity<ComboDTO> getComisarias(HttpServletRequest request, 
			@RequestParam String idTurno) {
		ComboDTO response = null;
		try {
			response = asistenciaService.getComisarias(request, idTurno);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardarAsistencias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> guardarAsistencias(HttpServletRequest request, @RequestBody List<TarjetaAsistenciaResponseItem> asistencias) {
		DeleteResponseDTO response = null;
		try {
			response = asistenciaService.guardarAsistencias(request, asistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
}
