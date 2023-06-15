package org.itcgae.siga.scs.controllers.oficio;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.scs.services.oficio.IGestionBajasTemporalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oficio")
public class BajasTemporalesController {
	
	@Autowired
	private IGestionBajasTemporalesService bajasTemporalesService;
	
	@GetMapping("/bajasTemporales/comboEstado")
	public ResponseEntity<ComboDTO> comboEstadoEjg(HttpServletRequest request) {
		ComboDTO response = bajasTemporalesService.comboEstado(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bajasTemporales/busquedaBajasTemporales",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BajasTemporalesDTO> busquedaTurnos(@RequestBody BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request) {
		BajasTemporalesDTO response = bajasTemporalesService.busquedaBajasTemporales(bajasTemporalesItem, request);
		return new ResponseEntity<BajasTemporalesDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/bajasTemporales/updateBaja", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateEstado(@RequestBody List<Object> bajasTemporalesItem, HttpServletRequest request) {
		UpdateResponseDTO response = bajasTemporalesService.updateEstado(bajasTemporalesItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/bajasTemporales/deleteBajaTemporal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteBaja(@RequestBody List<Object> bajasTemporalesItem, HttpServletRequest request) {
		UpdateResponseDTO response = bajasTemporalesService.deleteBaja(bajasTemporalesItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/bajasTemporales/saveBajaTemporal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> saveBajaTemporal(@RequestBody List<Object> bajasTemporalesItem, HttpServletRequest request) {
//		List<BajasTemporalesItem> bajasTemporales = convertObjectToBajaItem(bajasTemporalesItem);
		UpdateResponseDTO response = bajasTemporalesService.saveBajaTemporal(bajasTemporalesItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/bajasTemporales/nuevaBajaTemporal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevaBajaTemporal(@RequestBody List<Object> bajasTemporalesItem, HttpServletRequest request) {
		InsertResponseDTO response = bajasTemporalesService.nuevaBajaTemporal(bajasTemporalesItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	private List<BajasTemporalesItem> convertObjectToBajaItem(List<Object> bajasTemporalesItem){
		List<BajasTemporalesItem> bajasTemporales = new ArrayList<BajasTemporalesItem>();
		for(Object obj: bajasTemporalesItem) {
		}
		
		return bajasTemporales;
	}
}
 
