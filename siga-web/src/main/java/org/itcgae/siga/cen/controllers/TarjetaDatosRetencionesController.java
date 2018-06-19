package org.itcgae.siga.cen.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaRetencionesDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionDTO;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.RetencionesDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosRetencionesService;
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
public class TarjetaDatosRetencionesController {

	
	@Autowired
	private ITarjetaDatosRetencionesService tarjetaDatosRetencionesService;
	
	@RequestMapping(value = "retenciones/tipoRetencion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MaestroRetencionDTO> getRetentionTypes(HttpServletRequest request) { 
		MaestroRetencionDTO response = tarjetaDatosRetencionesService.getRetentionTypes(request);
		return new ResponseEntity<MaestroRetencionDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "retenciones/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RetencionesDTO> getRetenciones(@RequestParam("numPagina") int numPagina,@RequestBody PersonaSearchDTO personaSearchDTO, HttpServletRequest request) { 
		RetencionesDTO response = tarjetaDatosRetencionesService.getRetenciones(numPagina,personaSearchDTO, request);
		return new ResponseEntity<RetencionesDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "retenciones/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateRetenciones(@RequestBody List<EtiquetaRetencionesDTO> etiquetaRetencionesDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosRetencionesService.updateRetenciones(etiquetaRetencionesDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
}
