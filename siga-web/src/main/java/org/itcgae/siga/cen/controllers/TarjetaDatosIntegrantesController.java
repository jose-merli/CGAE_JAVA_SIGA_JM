package org.itcgae.siga.cen.controllers;



import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.cen.services.ITarjetaDatosRegistralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
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
public class TarjetaDatosIntegrantesController {

	@Autowired 
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;
	
	@Autowired
	private ITarjetaDatosRegistralesService tarjetaDatosRegistralesService;
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosIntegrantesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosIntegrantesDTO> searchIntegrantesData(@RequestParam("numPagina") int numPagina, @RequestBody DatosIntegrantesSearchDTO datosIntegrantesSearchDTO, HttpServletRequest request) { 
		DatosIntegrantesDTO response = tarjetaDatosIntegrantesService.searchIntegrantesData(numPagina, datosIntegrantesSearchDTO, request);
		return new ResponseEntity<DatosIntegrantesDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "tarjetaIntegrantes/tipoColegio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getActividadProfesional(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosRegistralesService.getActividadProfesional(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tarjetaIntegrantes/provincias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "tarjetaIntegrantes/cargos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCargos(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getCargos(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "tarjetaIntegrantes/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateMember(@RequestBody TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosIntegrantesService.updateMember(tarjetaIntegrantesUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "tarjetaIntegrantes/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> createMember(@RequestBody TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosIntegrantesService.createMember(tarjetaIntegrantesCreateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	
	
	
	
	
	
	

}
