package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaColegiadosService;
import org.itcgae.siga.cen.services.IBusquedaNoColegiadosService;
import org.itcgae.siga.cen.services.ISubtipoCurricularService;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.cen.services.ITipoCurricularService;
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
public class BusquedaNoColegiadosController {

	@Autowired
	private IBusquedaNoColegiadosService busquedaNoColegiadosService;
	
	@Autowired
	private IBusquedaColegiadosService busquedaColegiadosService;
	
	
	@Autowired
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	@Autowired
	private ITipoCurricularService tipoCurricularService;
	
	@Autowired
	private ISubtipoCurricularService subtipoCurricularService;
	
	@Autowired
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;
	
	// Faltan combos: Subtipo curriculares (pendiente de analisis)
	@RequestMapping(value = "/busquedaNoColegiados/estadoCivil",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCivilStatus(HttpServletRequest request) {
		ComboDTO response = busquedaColegiadosService.getCivilStatus(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaNoColegiados/situacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getSituacion(HttpServletRequest request) {
		ComboDTO response = busquedaColegiadosService.getSituacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaNoColegiados/provincias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaNoColegiados/poblacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPoblacion(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request,idProvincia, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaNoColegiados/tipoDireccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTipoDireccion(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getTipoDireccion(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaNoColegiados/categoriaCurricular", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCVCategory(HttpServletRequest request) { 
		ComboDTO response = busquedaColegiadosService.getCVCategory(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaNocolegiado/searchNoColegiado",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NoColegiadoDTO> searchNoColegiado(@RequestBody NoColegiadoItem noColegiadoItem, HttpServletRequest request) {
		NoColegiadoDTO response = busquedaNoColegiadosService.searchNoColegiado(noColegiadoItem, request);
		return new ResponseEntity<NoColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaNocolegiado/searchHistoric", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NoColegiadoDTO> searchHistoricLegalPersons(@RequestParam("numPagina") int numPagina, @RequestBody NoColegiadoItem noColegiadoItem, HttpServletRequest request) { 
		NoColegiadoDTO response = busquedaNoColegiadosService.searchHistoricNoColegiado(numPagina, noColegiadoItem, request);
		return new ResponseEntity<NoColegiadoDTO >(response, HttpStatus.OK);
	} 
	
	@RequestMapping(value = "/busquedaNocolegiado/deleteNoColegiado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO>deleteNoColegiado(@RequestBody NoColegiadoItem noColegiadoItem, HttpServletRequest request) { 
		DeleteResponseDTO response = busquedaNoColegiadosService.deleteNoColegiado(noColegiadoItem, request);
		
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "busquedaNoColegiados/getCurricularTypeCombo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCurricularTypeCombo(@RequestParam("idTipoCV") String idTipoCV, HttpServletRequest request) { 
		ComboDTO response = tipoCurricularService.getCurricularTypeCombo(idTipoCV, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaNoColegiados/getCurricularSubtypeCombo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCurricularSubtypeCombo(@RequestParam("idTipoCV") String idTipoCV, HttpServletRequest request) { 
		ComboDTO response = subtipoCurricularService.getCurricularSubtypeCombo(idTipoCV, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}