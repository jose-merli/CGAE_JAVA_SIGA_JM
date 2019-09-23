package org.itcgae.siga.scs.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ModulosDTO;
import org.itcgae.siga.DTO.scs.ModulosItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.scs.service.IModulosYBasesService;
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
public class ModulosYBasesController {

	@Autowired
	private IModulosYBasesService ModulosYBasesService;
	
		
	@RequestMapping(value = "/modulosybasesdecompensacion/searchModulos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModulosDTO> searchModules(@RequestBody ModulosItem modulosItem, HttpServletRequest request) {
		ModulosDTO response = ModulosYBasesService.searchModules(modulosItem, request);
		return new ResponseEntity<ModulosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/deleteModulos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteZones(@RequestBody ModulosDTO modulosDTO, HttpServletRequest request) {
		UpdateResponseDTO response = ModulosYBasesService.deleteModules(modulosDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	
//	@RequestMapping(value = "/areasMaterias/jurisdicciones",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> getJurisdicciones(HttpServletRequest request) {
//		ComboDTO response = fichaAreasService.getPartidoJudicial(request);
//		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "/areasMaterias/deleteAreas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> deleteZones(@RequestBody AreasDTO areasDTO, HttpServletRequest request) {
//		UpdateResponseDTO response = fichaAreasService.deleteAreas(areasDTO, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	@RequestMapping(value = "/areasMaterias/updateAreas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> updateGroupZone(@RequestBody AreasItem areasItem, HttpServletRequest request) {
//
//		UpdateResponseDTO response = fichaAreasService.updateArea(areasItem, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//	
//	@RequestMapping(value = "/areasMaterias/createAreas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<InsertResponseDTO> createGroupZone(@RequestBody AreasItem areasItem, HttpServletRequest request) {
//		InsertResponseDTO response = fichaAreasService.createArea(areasItem, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	@RequestMapping(value = "/areasMaterias/searchMaterias",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<MateriasDTO> searchSubzonas(@RequestParam("idArea") String idArea, HttpServletRequest request) {
//		MateriasDTO response = fichaAreasService.searchMaterias(idArea, request);
//		return new ResponseEntity<MateriasDTO>(response, HttpStatus.OK);
//	}
//	 
//	@RequestMapping(value = "/areasMaterias/updateMaterias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> updateMaterias(@RequestBody AreasDTO areasDTO, HttpServletRequest request) {
//
//		UpdateResponseDTO response = fichaAreasService.updateMaterias(areasDTO, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//	
//	@RequestMapping(value = "/areasMaterias/deleteMaterias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<UpdateResponseDTO> deleteMaterias(@RequestBody AreasDTO areasDTO, HttpServletRequest request) {
//
//		UpdateResponseDTO response = fichaAreasService.deleteMaterias(areasDTO, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//	
//	
//	@RequestMapping(value = "/areasMaterias/createMaterias",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<InsertResponseDTO> createMaterias(@RequestBody AreasItem areasItem, HttpServletRequest request) {
//		InsertResponseDTO response = fichaAreasService.createMaterias(areasItem, request);
//		if (response.getError().getCode() == 200)
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
	
}
