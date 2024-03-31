package org.itcgae.siga.cen.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDeleteDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ParametroColegioDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaPerJuridicaService;
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
public class BusquedaPerJuridicaController {
	
	
	@Autowired
	private IBusquedaPerJuridicaService busquedaPerJuridicaService;
	
	@RequestMapping(value = "/busquedaPerJuridica/tipoSociedad",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getSocietyTypes( HttpServletRequest request) {
		ComboDTO response = busquedaPerJuridicaService.getSocietyTypes(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/busquedaPerJuridica/etiquetas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboInstitucionDTO> getLabel( HttpServletRequest request) {
		ComboInstitucionDTO response = busquedaPerJuridicaService.getLabel(request);
		return new ResponseEntity<ComboInstitucionDTO>(response, HttpStatus.OK);
	}
	
					
	@RequestMapping(value = "/busquedaPerJuridica/etiquetasPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboEtiquetasDTO> getLabelPerson(@RequestBody PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request) throws ParseException{
		ComboEtiquetasDTO response = busquedaPerJuridicaService.getLabelPerson(personaJuridicaSearchDTO,request);
		return new ResponseEntity<ComboEtiquetasDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "busquedaPerJuridica/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaJuridicaDTO> searchLegalPersons(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request) { 
		BusquedaJuridicaDTO response = busquedaPerJuridicaService.searchLegalPersons(numPagina, busquedaJuridicaSearchDTO, request);
		return new ResponseEntity<BusquedaJuridicaDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/searchHistoric", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusquedaJuridicaDTO> searchHistoricLegalPersons(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request) { 
		BusquedaJuridicaDTO response = busquedaPerJuridicaService.searchHistoricLegalPersons(numPagina, busquedaJuridicaSearchDTO, request);
		return new ResponseEntity<BusquedaJuridicaDTO >(response, HttpStatus.OK);
	} 
	
	
	@RequestMapping(value = "/busquedaPerJuridica/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO>deleteNotCollegiate(@RequestBody BusquedaJuridicaDeleteDTO busquedaJuridicaDeleteDTO, HttpServletRequest request) { 
		DeleteResponseDTO response = busquedaPerJuridicaService.deleteNotCollegiate(busquedaJuridicaDeleteDTO, request);
		
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/parametroColegio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ParametroColegioDTO> searchParametroColegio(@RequestBody StringDTO stringDTO, HttpServletRequest request) { 
		ParametroColegioDTO response = busquedaPerJuridicaService.searchParametroColegio(stringDTO, request);
		return new ResponseEntity<ParametroColegioDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/property", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ParametroColegioDTO> searchproperty(@RequestBody StringDTO stringDTO, HttpServletRequest request) { 
		ParametroColegioDTO response = busquedaPerJuridicaService.searchProperty(stringDTO, request);
		return new ResponseEntity<ParametroColegioDTO>(response, HttpStatus.OK);
	}
	
	
	
	
}
