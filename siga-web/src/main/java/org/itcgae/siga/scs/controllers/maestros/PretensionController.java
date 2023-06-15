package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.PretensionDTO;
import org.itcgae.siga.DTOs.scs.PretensionItem;
import org.itcgae.siga.scs.services.maestros.IFichaAreasService;
import org.itcgae.siga.scs.services.maestros.IPretensionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/maestros")
public class PretensionController {

	@Autowired
	private IPretensionesService pretensionesService;
	
	@Autowired
	private IFichaAreasService fichaAreasService;
	

	
	@RequestMapping(value = "/gestionPretensiones/busquedaPretensiones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PretensionDTO> searchPretensiones(@RequestBody PretensionItem pretensionItem, HttpServletRequest request) {
		PretensionDTO response = pretensionesService.searchPretensiones(pretensionItem, request);
		return new ResponseEntity<PretensionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionPretensiones/deletePretensiones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deletePretensiones(@RequestBody PretensionDTO pretensionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = pretensionesService.deletePretensiones(pretensionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionPretensiones/activatePretensiones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activatePretensiones(@RequestBody PretensionDTO pretensionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = pretensionesService.activatePretensiones(pretensionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionPretensiones/updatePretension", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updatePretension(@RequestBody PretensionDTO pretensionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = pretensionesService.updatePretension(pretensionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionPretensiones/createPretension", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createPretension(@RequestBody PretensionItem pretensionItem, HttpServletRequest request) {

		InsertResponseDTO response = pretensionesService.createPretension(pretensionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/busquedaPretensiones/procesos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProcesos(HttpServletRequest request) { 
		ComboDTO response = pretensionesService.getProcedimientos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/busquedaPretensiones/jurisdiccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPopulation(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = fichaAreasService.getJurisdicciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	
}
