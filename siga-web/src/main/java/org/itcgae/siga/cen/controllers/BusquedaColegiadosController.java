package org.itcgae.siga.cen.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ComboSubtiposCVDTO;
import org.itcgae.siga.DTOs.cen.ComboTiposCVDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IBusquedaColegiadosService;
import org.itcgae.siga.cen.services.ISubtipoCurricularService;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.cen.services.ITipoCurricularService;
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
public class BusquedaColegiadosController {

	@Autowired
	private IBusquedaColegiadosService busquedaColegiadosService;
	
	@Autowired
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	@Autowired
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;

	@Autowired
	private ITipoCurricularService tipoCurricularService;
	
	@Autowired
	private ISubtipoCurricularService subtipoCurricularService;
	
	// Faltan combos: Subtipo curriculares (pendiente de analisis)
	@RequestMapping(value = "busquedaColegiados/estadoCivil",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCivilStatus(HttpServletRequest request) {
		ComboDTO response = busquedaColegiadosService.getCivilStatus(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaColegiados/situacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getSituacion(HttpServletRequest request) {
		ComboDTO response = busquedaColegiadosService.getSituacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaColegiados/provincias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaColegiados/poblacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPoblacion(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request,idProvincia, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaColegiados/tipoDireccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTipoDireccion(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getTipoDireccion(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaColegiados/categoriaCurricular", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCVCategory(HttpServletRequest request) { 
		ComboDTO response = busquedaColegiadosService.getCVCategory(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaColegiado/etiquetas",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboInstitucionDTO> getLabel( HttpServletRequest request) {
		ComboInstitucionDTO response = busquedaColegiadosService.getLabel(request);
		return new ResponseEntity<ComboInstitucionDTO>(response, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/busquedaColegiado/searchColegiado",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> searchColegiado(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) {
		ColegiadoDTO response = busquedaColegiadosService.searchColegiado(colegiadoItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaColegiados/getCurricularTypeCombo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboTiposCVDTO> getCurricularTypeCombo(@RequestParam("idTipoCV") String idTipoCV, HttpServletRequest request) { 
		ComboTiposCVDTO response = tipoCurricularService.getCurricularTypeCombo(idTipoCV, false, request);
		return new ResponseEntity<ComboTiposCVDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaColegiados/getCurricularSubtypeCombo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboSubtiposCVDTO> getCurricularSubtypeCombo(@RequestParam("idTipoCV") String idTipoCV, HttpServletRequest request) { 
		ComboSubtiposCVDTO response = subtipoCurricularService.getCurricularSubtypeCombo(idTipoCV, false, request);
		return new ResponseEntity<ComboSubtiposCVDTO>(response, HttpStatus.OK);
	}
		
}
