package org.itcgae.siga.scs.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JuzgadoDTO;
import org.itcgae.siga.DTO.scs.JuzgadoItem;
import org.itcgae.siga.DTO.scs.ProcedimientoDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.scs.service.IBusquedaJuzgadosService;
import org.itcgae.siga.scs.service.IGestionJuzgadosService;
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
@RequestMapping(value = "/maestros")
public class JuzgadosController {

	@Autowired
	private IBusquedaJuzgadosService busquedaJuzgadosService;
	
	@Autowired
	private IGestionJuzgadosService gestionJuzgadosService;
	
	@Autowired
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	@Autowired
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;
	
	@RequestMapping(value = "/busquedaJuzgados/searchJudged",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JuzgadoDTO> searchJudged(@RequestBody JuzgadoItem juzgadoItem, HttpServletRequest request) {
		JuzgadoDTO response = busquedaJuzgadosService.searchJudged(juzgadoItem, request);
		return new ResponseEntity<JuzgadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaJuzgados/provinces", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaJuzgados/population", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPopulation(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request,idProvincia, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaJuzgados/deleteJudged", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteJudged(@RequestBody JuzgadoDTO juzgadoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaJuzgadosService.deleteJudged(juzgadoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/busquedaJuzgados/activateJudged", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateJudged(@RequestBody JuzgadoDTO juzgadoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaJuzgadosService.activateJudged(juzgadoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionJuzgados/searchProcess", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcedimientoDTO> searchProcess(HttpServletRequest request) { 
		ProcedimientoDTO response = gestionJuzgadosService.searchProcess(request);
		return new ResponseEntity<ProcedimientoDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJuzgados/searchProcJudged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcedimientoDTO> searchProcJudged(@RequestParam("idJuzgado") String idJuzgado, HttpServletRequest request) { 
		ProcedimientoDTO response = gestionJuzgadosService.searchProcJudged(idJuzgado, request);
		return new ResponseEntity<ProcedimientoDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJuzgados/updateJudged", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateJudged(@RequestBody JuzgadoItem juzgadoItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJuzgadosService.updateJudged(juzgadoItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionJuzgados/createJudged", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createJudged(@RequestBody JuzgadoItem juzgadoItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionJuzgadosService.createJudged(juzgadoItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionJuzgados/associateProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> associateProcess(@RequestBody ProcedimientoDTO procedimientoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJuzgadosService.associateProcess(procedimientoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
