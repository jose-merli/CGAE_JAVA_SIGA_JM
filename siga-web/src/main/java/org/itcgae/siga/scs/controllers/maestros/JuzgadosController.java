package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.scs.services.maestros.IBusquedaJuzgadosService;
import org.itcgae.siga.scs.services.maestros.IGestionJuzgadosService;
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
	
	@RequestMapping(value = "/busquedaJuzgados/searchCourt",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JuzgadoDTO> searchCourt(@RequestBody JuzgadoItem juzgadoItem, HttpServletRequest request) {
		JuzgadoDTO response = busquedaJuzgadosService.searchCourt(juzgadoItem, request);
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
	
	@RequestMapping(value = "/busquedaJuzgados/deleteCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteJudged(@RequestBody JuzgadoDTO juzgadoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaJuzgadosService.deleteCourt(juzgadoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/busquedaJuzgados/activateCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateJudged(@RequestBody JuzgadoDTO juzgadoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaJuzgadosService.activateCourt(juzgadoDTO, request);
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
	
	@RequestMapping(value = "/gestionJuzgados/searchProcCourt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcedimientoDTO> searchProcJudged(@RequestParam("idJuzgado") String idJuzgado, HttpServletRequest request) { 
		ProcedimientoDTO response = gestionJuzgadosService.searchProcCourt(idJuzgado, request);
		return new ResponseEntity<ProcedimientoDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJuzgados/updateCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateJudged(@RequestBody JuzgadoItem juzgadoItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJuzgadosService.updateCourt(juzgadoItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionJuzgados/createCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createCourt(@RequestBody JuzgadoItem juzgadoItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionJuzgadosService.createCourt(juzgadoItem, request);
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
	
	@RequestMapping(value = "/gestionJuzgados/asociarModulosAJuzgados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarModulosAJuzgados(@RequestBody ProcedimientoDTO procedimientoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJuzgadosService.asociarModulosAJuzgados(procedimientoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
