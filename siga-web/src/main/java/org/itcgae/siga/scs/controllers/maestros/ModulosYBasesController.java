package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionItem;
import org.itcgae.siga.DTOs.scs.ModulosDTO;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.DTOs.scs.ModulosJuzgadoItem;
import org.itcgae.siga.scs.services.maestros.IModulosYBasesService;
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
public class ModulosYBasesController {

	@Autowired
	private IModulosYBasesService ModulosYBasesService;
	
		
	@RequestMapping(value = "/modulosybasesdecompensacion/searchModulos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModulosDTO> searchModules(@RequestBody ModulosItem modulosItem, HttpServletRequest request) {
		ModulosDTO response = ModulosYBasesService.searchModules(modulosItem, request);
		return new ResponseEntity<ModulosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/searchModulosJuzgados",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModulosDTO> searchModules(@RequestBody ModulosJuzgadoItem modulosJuzgadoItem, HttpServletRequest request) {
		ModulosDTO response = ModulosYBasesService.searchModulesJuzgados(modulosJuzgadoItem, request);
		return new ResponseEntity<ModulosDTO>(response, HttpStatus.OK);
}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/procedimientos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProcedimientos(@RequestParam("idProcedimiento") String idProcedimiento, HttpServletRequest request) {
		ComboDTO response = ModulosYBasesService.getProcedimientos(request, idProcedimiento);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/getComplementoProcedimiento",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> getComplementoProcedimiento(@RequestBody ModulosItem modulosItem, HttpServletRequest request) {
		StringDTO response = ModulosYBasesService.getComplementoProcedimiento(modulosItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/deleteModulos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteModules(@RequestBody ModulosDTO modulosDTO, HttpServletRequest request) {
		UpdateResponseDTO response = ModulosYBasesService.deleteModules(modulosDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/checkModulos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> checkModules(@RequestBody ModulosDTO modulosDTO, HttpServletRequest request) {
		UpdateResponseDTO response = ModulosYBasesService.checkModules(modulosDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/updatemoduloybasedecompensacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateModules(@RequestBody ModulosItem modulosItem, HttpServletRequest request) {
		UpdateResponseDTO response = ModulosYBasesService.updateModules(modulosItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/createmoduloybasedecompensacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createModules(@RequestBody ModulosItem modulosItem, HttpServletRequest request) {
		InsertResponseDTO response = ModulosYBasesService.createModules(modulosItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/searchAcreditaciones",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AcreditacionDTO> searchSubzonas(@RequestParam("idProcedimiento") String idProcedimiento, HttpServletRequest request) {
		AcreditacionDTO response = ModulosYBasesService.searchAcreditaciones(idProcedimiento, request);
		return new ResponseEntity<AcreditacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/deleteAcreditacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteAcreditacion(@RequestBody AcreditacionDTO acreditacionDTO, HttpServletRequest request) {
		UpdateResponseDTO response = ModulosYBasesService.deleteAcreditacion(acreditacionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/updateAcreditacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateAcreditacion(@RequestBody AcreditacionDTO acreditacionDTO, HttpServletRequest request) {
		UpdateResponseDTO response = ModulosYBasesService.updateAcreditacion(acreditacionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/createAcreditacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createGroupZone(@RequestBody AcreditacionItem acreditacionItem, HttpServletRequest request) {
		InsertResponseDTO response = ModulosYBasesService.createAcreditacion(acreditacionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/comboAcreditacionesDisponibles",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getAcreditaciones(@RequestParam("idProcedimiento") String idProcedimiento, HttpServletRequest request) {
		ComboDTO response = ModulosYBasesService.getAcreditaciones(idProcedimiento, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modulosybasesdecompensacion/comboAcreditaciones",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getAcreditacionesCombo(HttpServletRequest request) {
		ComboDTO response = ModulosYBasesService.getAcreditaciones(null, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
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
