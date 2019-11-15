package org.itcgae.siga.scs.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTO.scs.JusticiableDTO;
import org.itcgae.siga.DTO.scs.JusticiableItem;
import org.itcgae.siga.DTO.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.service.IBusquedaJusticiablesService;
import org.itcgae.siga.scs.service.IGestionJusticiableService;
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
@RequestMapping(value = "/justiciables")
public class JusticiablesController {
	
	@Autowired
	IBusquedaJusticiablesService busquedaJusticiablesService;
	
	@Autowired
	IGestionJusticiableService gestionJusticiableService;
	
	@RequestMapping(value = "/busquedaJusticiables/comboRoles", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboRoles(HttpServletRequest request) {
		ComboDTO response = busquedaJusticiablesService.getComboRoles(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/getTelefonos", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableTelefonoDTO> getTelefonos(@RequestBody JusticiableItem justiciableItem, HttpServletRequest request) {
		JusticiableTelefonoDTO response = gestionJusticiableService.getTelefonos(justiciableItem, request);
		return new ResponseEntity<JusticiableTelefonoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaJusticiables/searchJusticiables", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableBusquedaDTO> searchJusticiables(@RequestBody JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request) {
		JusticiableBusquedaDTO response = busquedaJusticiablesService.searchJusticiables(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableBusquedaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboMinusvalias", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getMinusvalias(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getMinusvalias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboProfesiones", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProfesiones(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getProfesiones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboTipoVias", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoVias(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getTipoVias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/searchJusticiable", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableDTO> busquedaJusticiable(@RequestBody JusticiableBusquedaItem justiciableBusquedaItem,HttpServletRequest request) {
		JusticiableDTO response = gestionJusticiableService.searchJusticiable(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboPoblacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPoblacion(@RequestParam("idPoblacion") String idPoblacion, HttpServletRequest request) { 
		ComboDTO response = gestionJusticiableService.getPoblacion(idPoblacion, request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/createJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createJusticiable(@RequestBody JusticiableItem justiciableItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionJusticiableService.createJusticiable(justiciableItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	@RequestMapping(value = "/gestionJusticiables/updateJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateJusticiable(@RequestBody JusticiableItem justiciableItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.updateJusticiable(justiciableItem, true, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	@RequestMapping(value = "/gestionJusticiables/updateDatosSolicitudJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosSolicitudJusticiable(@RequestBody JusticiableItem justiciableItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.updateJusticiable(justiciableItem, false, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	@RequestMapping(value = "/gestionJusticiables/searchAsuntosJusticiable", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AsuntosJusticiableDTO> searchAsuntosJusticiable(@RequestBody String idPersonaJusticiable,HttpServletRequest request) {
		AsuntosJusticiableDTO response = gestionJusticiableService.searchAsuntosJusticiable(idPersonaJusticiable, request);
		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/gestionJusticiables/searchAsuntosConClave", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	void searchAsuntosConClave(@RequestBody AsuntosJusticiableDTO asuntosJusticiableDTO,HttpServletRequest request) {
//		AsuntosJusticiableDTO response = gestionJusticiableService.searchAsuntos(idPersonaJusticiable, request);
//		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/gestionJusticiables/getJusticiableByNif", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableDTO> getJusticiableByNif(@RequestBody JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request) {
		JusticiableDTO response = gestionJusticiableService.getJusticiableByNif(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/associateRepresentante", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> associateRepresentante(@RequestBody JusticiableItem justiciableItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.associateRepresentante(justiciableItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	@RequestMapping(value = "/gestionJusticiables/disassociateRepresentante", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> disassociateRepresentante(@RequestBody JusticiableItem justiciableItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.disassociateRepresentante(justiciableItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	
}
