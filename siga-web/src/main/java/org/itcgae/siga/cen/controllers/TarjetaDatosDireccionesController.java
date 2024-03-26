package org.itcgae.siga.cen.controllers;



import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaDireccionesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TarjetaDatosDireccionesController {


	@Autowired 
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	
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
    ResponseEntity<ComboDTO> getPoblacion( @RequestParam("idProvincia") String idProvincia, @RequestParam("filtro") String filtro, HttpServletRequest request) { 
        ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request, idProvincia, filtro);
        return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
    @RequestMapping(value = "tarjetaDirecciones/poblacion/{idPoblacion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboItem> getPoblacion( @PathVariable("idPoblacion") String idPoblacion, HttpServletRequest request) { 
        ComboItem response = tarjetaDatosDireccionesService.getPoblacion(request,  idPoblacion);
        return new ResponseEntity<ComboItem >(response, HttpStatus.OK);
    }
    
	@RequestMapping(value = "tarjetaDirecciones/tipoDireccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTipoDireccion(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getTipoDireccion(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}

	@RequestMapping(value = "tarjetaDirecciones/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDirection(@RequestBody DatosDireccionesItem datosDirecciones, HttpServletRequest request) { 
		UpdateResponseDTO response = tarjetaDatosDireccionesService.updateDirection(datosDirecciones, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "tarjetaDirecciones/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createDirection(@RequestBody DatosDireccionesItem datosDirecciones, HttpServletRequest request) { 
		InsertResponseDTO response = tarjetaDatosDireccionesService.createDirection(datosDirecciones, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "tarjetaDirecciones/duplicate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> duplicateDirection(@RequestBody DatosDireccionesItem datosDireccionesItem, HttpServletRequest request) { 
		InsertResponseDTO response = tarjetaDatosDireccionesService.duplicateDirection(datosDireccionesItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}