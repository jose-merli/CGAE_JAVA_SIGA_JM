package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoItem;
import org.itcgae.siga.scs.services.maestros.IGestionCosteFijoService;
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
public class CosteFijoController {

	@Autowired
	private IGestionCosteFijoService gestionCosteFijoService;

	@RequestMapping(value = "/gestionCostesFijos/searchCostesFijos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CosteFijoDTO> searchCostesFijos(@RequestParam("historico") boolean historico, HttpServletRequest request) {
		CosteFijoDTO response = gestionCosteFijoService.searchCostesFijos(historico, request);
		return new ResponseEntity<CosteFijoDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionCostesFijos/comboCosteFijos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboCosteFijos(HttpServletRequest request) {
		ComboDTO response = gestionCosteFijoService.getComboCosteFijos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionCostesFijos/comboAsistencia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboAsistencia(HttpServletRequest request) {
		ComboDTO response = gestionCosteFijoService.getComboAsistencia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionCostesFijos/comboActuacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboActuacion(@RequestParam("idTipoAsistencia") String idTipoAsistencia,
			HttpServletRequest request) {
		ComboDTO response = gestionCosteFijoService.getComboActuacion(idTipoAsistencia, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionCostesFijos/createCosteFijo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createCosteFijo(@RequestBody CosteFijoItem costeFijoItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionCosteFijoService.createCosteFijo(costeFijoItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionCostesFijos/updateCostesFijos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateCosteFijo(@RequestBody CosteFijoDTO CosteFijoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionCosteFijoService.updateCosteFijo(CosteFijoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/gestionCostesFijos/deleteCostesFijos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteCosteFijo(@RequestBody CosteFijoDTO costeFijoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionCosteFijoService.deleteCosteFijo(costeFijoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionCostesFijos/activateCostesFijos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateCosteFijo(@RequestBody CosteFijoDTO costeFijoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionCosteFijoService.activateCosteFijo(costeFijoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
