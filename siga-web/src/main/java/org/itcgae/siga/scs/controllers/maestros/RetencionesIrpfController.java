package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.RetencionIRPFDTO;
import org.itcgae.siga.DTOs.scs.RetencionIRPFItem;
import org.itcgae.siga.scs.services.maestros.IRetencionesIrpfService;
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
public class RetencionesIrpfController {

	@Autowired
	private IRetencionesIrpfService retencionesService;
	
	@RequestMapping(value = "/gestionRetencionesIRPF/busquedaRetencionesIRPF",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RetencionIRPFDTO> searchProcuradores(@RequestBody RetencionIRPFItem retencionItem, HttpServletRequest request) {
		RetencionIRPFDTO response = retencionesService.searchRetenciones(retencionItem, request);
		return new ResponseEntity<RetencionIRPFDTO>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/gestionRetencionesIRPF/sociedades", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getSociedades(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = retencionesService.getSociedades(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionRetencionesIRPF/deleteRetencionesIRPF", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteRetenciones(@RequestBody RetencionIRPFDTO retencionesDTO, HttpServletRequest request) {

		UpdateResponseDTO response = retencionesService.deleteRetenciones(retencionesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionRetencionesIRPF/activateRetencionesIRPF", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateRetenciones(@RequestBody RetencionIRPFDTO retencionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = retencionesService.activateRetenciones(retencionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionRetencionesIRPF/updateRetencionesIRPF", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateRetencion(@RequestBody RetencionIRPFDTO retencionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = retencionesService.updateRetenciones(retencionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionRetencionesIRPF/createRetencionIRPF", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createRetencion(@RequestBody RetencionIRPFItem retencionItem, HttpServletRequest request) {

		InsertResponseDTO response = retencionesService.createRetencion(retencionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	
}
