package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AreasDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciasDTO;
import org.itcgae.siga.scs.services.maestros.IGestionTiposAsistenciaService;
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
public class TiposAsistenciaController {

	@Autowired
	private IGestionTiposAsistenciaService TiposAsistenciaService;
	
	@RequestMapping(value = "/gestionTiposAsistencia/busquedaTiposAsistencia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TiposAsistenciasDTO> searchTiposAsistencia(@RequestParam("historico") boolean historico, HttpServletRequest request) {
		TiposAsistenciasDTO response = TiposAsistenciaService.searchTiposAsistencia(historico, request);
		return new ResponseEntity<TiposAsistenciasDTO>(response, HttpStatus.OK);
	}
	
	/*
	@RequestMapping(value = "/gestionTiposAsistencia/ComboTiposAsistencia",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTiposGuardia(HttpServletRequest request) {
		ComboDTO response = TiposAsistenciaService.getTiposGuardia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		
	}
	*/
	@RequestMapping(value = "/gestionTiposAsistencia/ComboTiposAsistencia",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTiposGuardia(HttpServletRequest request) {
		ComboDTO response = TiposAsistenciaService.getTiposGuardia2(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/gestionTiposAsistencia/deleteTipoAsitencia", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteTipoAsitencia(@RequestBody TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = TiposAsistenciaService.deleteTipoAsitencia(tiposAsistenciasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionTiposAsistencia/activateTipoAsitencia", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateTipoAsitencia(@RequestBody TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = TiposAsistenciaService.activateTipoAsitencia(tiposAsistenciasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionTiposAsistencia/updateTiposAsistencias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateTiposAsistencias(@RequestBody TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = TiposAsistenciaService.updateTiposAsistencias(tiposAsistenciasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionTiposAsistencia/createTipoAsistencia", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createTipoAsistencia(@RequestBody TiposAsistenciaItem tiposAsistenciaItem, HttpServletRequest request) {

		InsertResponseDTO response = TiposAsistenciaService.createTiposAsistencia(tiposAsistenciaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}