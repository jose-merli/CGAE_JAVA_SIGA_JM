/**
 * 
 */
package org.itcgae.siga.scs.controllers.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.PreAsistenciaDTO;
import org.itcgae.siga.DTOs.scs.PreAsistenciaItem;
import org.itcgae.siga.scs.services.guardia.PreAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pablo Jarana
 *
 */
@RestController
@RequestMapping(value = "/preasistencia")
public class PreAsistenciaController {
	
	@Autowired
	private PreAsistenciaService preAsistenciaService;
	
	@PostMapping(value = "/buscarPreAsistencias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PreAsistenciaDTO> searchPreAsistencias(HttpServletRequest request, @RequestBody PreAsistenciaItem filtro) {
		PreAsistenciaDTO response = null;
		try {
			response = preAsistenciaService.searchPreasistencias(request, filtro);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<PreAsistenciaDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/denegarPreAsistencias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> denegarPreAsistencias(HttpServletRequest request, @RequestBody List<PreAsistenciaItem> preasistencias) {
		UpdateResponseDTO response = null;
		try {
			response = preAsistenciaService.denegarPreasistencias(request, preasistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/activarPreAsistenciasDenegadas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> activarPreAsistenciasDenegadas(HttpServletRequest request, @RequestBody List<PreAsistenciaItem> preasistencias) {
		UpdateResponseDTO response = null;
		try {
			response = preAsistenciaService.activarPreAsistenciasDenegadas(request, preasistencias);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

}
