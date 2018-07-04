package org.itcgae.siga.cen.controllers;



import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaDireccionesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
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
public class TarjetaDatosDireccionesController {


	@Autowired 
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	@Autowired 
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;

	
	
	@RequestMapping(value = "busquedaPerJuridica/datosDireccionesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosDireccionesDTO> searchIntegrantesData(@RequestParam("numPagina") int numPagina, @RequestBody DatosDireccionesSearchDTO datosDireccionesSearchDTO, HttpServletRequest request) { 
		DatosDireccionesDTO response = tarjetaDatosDireccionesService.datosDireccionesSearch(numPagina, datosDireccionesSearchDTO, request);
		return new ResponseEntity<DatosDireccionesDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tarjetaDirecciones/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteMember(@RequestBody TarjetaDireccionesUpdateDTO[] tarjetaDireccionesUpdateDTO, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosDireccionesService.deleteDireccion(tarjetaDireccionesUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	

	
	@RequestMapping(value = "tarjetaDirecciones/pais", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPais(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPais(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tarjetaDirecciones/poblacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPoblacion(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	@RequestMapping(value = "tarjetaDirecciones/tipoDireccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTipoDireccion(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getTipoDireccion(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}


}
