package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

//import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IFichaDatosGeneralesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaDatosGeneralesController {
	
	
	@Autowired
	private IFichaDatosGeneralesService fichaDatosGenerales;
	
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
//	
//	@RequestMapping(value = "/busquedaPerJuridica/etiquetas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getLabel( HttpServletRequest request) {
//		ComboDTO response = busquedaPerJuridicaService.getLabel(request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}
//	
//					
//	@RequestMapping(value = "/busquedaPerJuridica/etiquetasPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getLabelPerson(@RequestBody PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request) {
//		ComboDTO response = busquedaPerJuridicaService.getLabelPerson(personaJuridicaSearchDTO,request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}
//	
//	
//	
//	@RequestMapping(value = "busquedaPerJuridica/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<BusquedaJuridicaDTO> searchLegalPersons(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request) { 
//		BusquedaJuridicaDTO response = busquedaPerJuridicaService.searchLegalPersons(numPagina, busquedaJuridicaSearchDTO, request);
//		return new ResponseEntity<BusquedaJuridicaDTO >(response, HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "busquedaPerJuridica/searchHistoric", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<BusquedaJuridicaDTO> searchHistoricLegalPersons(@RequestParam("numPagina") int numPagina, @RequestBody BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request) { 
//		BusquedaJuridicaDTO response = busquedaPerJuridicaService.searchHistoricLegalPersons(numPagina, busquedaJuridicaSearchDTO, request);
//		return new ResponseEntity<BusquedaJuridicaDTO >(response, HttpStatus.OK);
//	} 
//	
//	
//	@RequestMapping(value = "/busquedaPerJuridica/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<DeleteResponseDTO>deleteNotCollegiate(@RequestBody BusquedaJuridicaDeleteDTO busquedaJuridicaDeleteDTO, HttpServletRequest request) { 
//		DeleteResponseDTO response = busquedaPerJuridicaService.deleteNotCollegiate(busquedaJuridicaDeleteDTO, request);
//		
//		if(response.getStatus().equals(SigaConstants.OK))
//		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
//		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
//	}
//	
//	
//	
//	@RequestMapping(value = "busquedaPerJuridica/parametroColegio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ParametroColegioDTO> searchParametroColegio(@RequestBody StringDTO stringDTO, HttpServletRequest request) { 
//		ParametroColegioDTO response = busquedaPerJuridicaService.searchParametroColegio(stringDTO, request);
//		return new ResponseEntity<ParametroColegioDTO>(response, HttpStatus.OK);
//	}
	
	
	
}
