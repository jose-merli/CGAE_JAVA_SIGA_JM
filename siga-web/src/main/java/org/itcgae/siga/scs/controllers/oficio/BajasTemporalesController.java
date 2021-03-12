package org.itcgae.siga.scs.controllers.oficio;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.scs.services.oficio.IGestionBajasTemporalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oficio")
public class BajasTemporalesController {
	
	@Autowired
	private IGestionBajasTemporalesService bajasTemporalesService;
	
	@RequestMapping(value = "/bajasTemporales/busquedaBajasTemporales",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BajasTemporalesDTO> busquedaTurnos(@RequestBody BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request) {
		BajasTemporalesDTO response = bajasTemporalesService.busquedaBajasTemporales(bajasTemporalesItem, request);
		return new ResponseEntity<BajasTemporalesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bajasTemporales/nuevaBajaTemporal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevaBajaTemporal(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) {
		InsertResponseDTO response = bajasTemporalesService.nuevaBajaTemporal(colegiadoItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/bajasTemporales/updateDenegar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDenegar(@RequestBody BajasTemporalesDTO bajasTemporalesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = bajasTemporalesService.updateDenegar(bajasTemporalesDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
 
