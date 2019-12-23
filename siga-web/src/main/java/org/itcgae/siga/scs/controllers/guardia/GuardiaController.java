
package org.itcgae.siga.scs.controllers.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/guardia")
public class GuardiaController {

	@Autowired
	GuardiasService guardiasService;
	
	@PostMapping(value = "/busquedaGuardia/searchGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> searchGuardias(@RequestBody GuardiasItem guardiasItem, HttpServletRequest request){
		GuardiasDTO response= guardiasService.searchGuardias(guardiasItem,request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaGuardia/deleteGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.deleteGuardias(guardiasDTO,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/activateGuardias", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateGuardias(@RequestBody GuardiasDTO guardiasDTO, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.activateGuardias(guardiasDTO,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaGuardia/updateGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGuardias(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.updateGuardia(guardiaItem,request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/detalleGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> detalleGuardia(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request){
		GuardiasItem response= guardiasService.detalleGuardia(guardiaItem,request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}


	@PostMapping(value = "/busquedaGuardia/createGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createGuardia(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request){
		InsertResponseDTO response= guardiasService.createGuardia(guardiaItem,request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/resumenGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> resumenGuardia(@RequestBody GuardiasItem guardiaItem, HttpServletRequest request){
		GuardiasItem response= guardiasService.resumenGuardia(guardiaItem,request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/resumenConfiguracionCola", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasItem> resumenConfiguracionCola(@RequestBody GuardiasItem guardiaTurno, HttpServletRequest request){
		GuardiasItem response= guardiasService.resumenConfiguracionCola(guardiaTurno,request);
		return new ResponseEntity<GuardiasItem>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/tarjetaIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> tarjetaIncompatibilidades(@RequestBody String idGuardia, HttpServletRequest request){
		GuardiasDTO response= guardiasService.tarjetaIncompatibilidades(idGuardia,request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/getBaremos", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getBaremos(@RequestBody String idGuardia, HttpServletRequest request){
		ComboDTO response= guardiasService.getBaremos(idGuardia,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/busquedaGuardia/getCalendario", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosCalendarioItem> getCalendario(@RequestBody String idGuardia, HttpServletRequest request){
		DatosCalendarioItem response= guardiasService.getCalendario(idGuardia,request);
		return new ResponseEntity<DatosCalendarioItem>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaGuardia/getColaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionGuardiaDTO> getColaGuardia(@RequestBody GuardiasItem guardia, HttpServletRequest request){
		InscripcionGuardiaDTO response= guardiasService.searchColaGuardia(guardia, request);
		return new ResponseEntity<InscripcionGuardiaDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/gestionGuardia/ultimo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateUltimoCola(@RequestBody GuardiasItem guardia, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.updateUltimoCola(guardia, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/gestionGuardia/updateColaGuardia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateColaGuardia(@RequestBody GuardiasItem guardia, HttpServletRequest request){
		UpdateResponseDTO response= guardiasService.updateUltimoCola(guardia, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/gestionGuardia/resumenIncompatibilidades", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GuardiasDTO> resumenIncompatibilidades(@RequestBody GuardiasItem guardia, HttpServletRequest request){
		GuardiasDTO response= guardiasService.resumenIncompatibilidades(guardia, request);
		return new ResponseEntity<GuardiasDTO>(response, HttpStatus.OK);
	}
	
}