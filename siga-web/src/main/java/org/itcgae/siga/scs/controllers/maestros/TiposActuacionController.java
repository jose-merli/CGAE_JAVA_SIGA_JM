package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AreasDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.scs.TiposActuacionDTO;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciasDTO;
import org.itcgae.siga.scs.services.maestros.IGestionTiposActuacionService;
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
public class TiposActuacionController {

	@Autowired
	private IGestionTiposActuacionService TiposActuacionService;
	
	@RequestMapping(value = "/gestionTiposActuacion/busquedaTiposActuacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TiposActuacionDTO> searchTiposActuacion(@RequestParam("historico") boolean historico, HttpServletRequest request) {
		TiposActuacionDTO response = TiposActuacionService.searchTiposActuacion(historico, request);
		return new ResponseEntity<TiposActuacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionTiposAsistencia/ComboTiposActuacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTiposAsistencia(HttpServletRequest request) {
		ComboDTO response = TiposActuacionService.getTiposActuacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionTiposActuacion/deleteTipoActuacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteTipoAsitencia(@RequestBody TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = TiposActuacionService.deleteTipoActuacion(tiposActuacionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionTiposActuacion/activateTipoActuacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateTipoAsitencia(@RequestBody TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = TiposActuacionService.activateTipoActuacion(tiposActuacionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionTiposActuacion/updateTiposActuacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateTiposAsistencias(@RequestBody TiposActuacionDTO tiposActuacionItem, HttpServletRequest request) {

		UpdateResponseDTO response = TiposActuacionService.updateTiposActuaciones(tiposActuacionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionTiposActuacion/createTipoActuacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createTipoAsistencia(@RequestBody TiposActuacionItem tiposAsistenciaItem, HttpServletRequest request) {

		InsertResponseDTO response = TiposActuacionService.createTiposActuacion(tiposAsistenciaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}