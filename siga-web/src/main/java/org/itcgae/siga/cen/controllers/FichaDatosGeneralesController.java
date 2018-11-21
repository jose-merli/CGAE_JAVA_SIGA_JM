package org.itcgae.siga.cen.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
//import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IFichaDatosGeneralesService;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
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
public class FichaDatosGeneralesController {
	
	
	@Autowired
	private IFichaDatosGeneralesService fichaDatosGenerales;
	
//	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;

	
	@RequestMapping(value = "/fichaDatosGenerales/tratamiento",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboEtiquetas(HttpServletRequest request) {
		ComboDTO response = fichaDatosGenerales.getTratamiento(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosGenerales/estadoCivil",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboEstadoCivil(HttpServletRequest request) {
		ComboDTO response = fichaDatosGenerales.getEstadoCivil(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}	
//	createColegiado
	
	@RequestMapping(value = "/fichaDatosGenerales/datosGeneralesUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGeneralData(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) throws Exception { 
		UpdateResponseDTO response = fichaDatosGenerales.updateColegiado(colegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosGenerales/etiquetasPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboEtiquetasDTO> getLabelPerson(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException{
		ComboEtiquetasDTO response = fichaDatosGenerales.getLabelPerson(colegiadoItem,request);
		return new ResponseEntity<ComboEtiquetasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosGenerales/datosGeneralesCreateNoColegiado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createNoColegiado(@RequestBody NoColegiadoItem noColegiadoItem, HttpServletRequest request) throws Exception { 
		InsertResponseDTO response = fichaDatosGenerales.createNoColegiado(noColegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosGenerales/datosGeneralesSolicitudModificación", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> solicitudModificacion(@RequestBody NoColegiadoItem noColegiadoItem, HttpServletRequest request) throws Exception { 
		InsertResponseDTO response = fichaDatosGenerales.solicitudModificacion(noColegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosGenerales/partidoJudicialSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosDireccionesDTO> partidoJudicialSearch(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		DatosDireccionesDTO response = fichaDatosGenerales.partidoJudicialSearch(colegiadoItem,  request);
		return new ResponseEntity<DatosDireccionesDTO> (response, HttpStatus.OK);
	}
	
	
}
