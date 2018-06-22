package org.itcgae.siga.cen.controllers;



import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosInsertDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosBancariosService;
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
public class TarjetaDatosBancariosController {

	@Autowired 
	private ITarjetaDatosBancariosService tarjetaDatosBancariosService;
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosBancariosDTO> searchBanksData(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request) { 
		DatosBancariosDTO response = tarjetaDatosBancariosService.searchBanksData(numPagina, datosBancariosSearchDTO, request);
		return new ResponseEntity<DatosBancariosDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/busquedaPerJuridica/datosBancariosDelete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO>deleteBanksData(@RequestBody DatosBancariosDeleteDTO datosBancariosDeleteDTO, HttpServletRequest request) { 
		DeleteResponseDTO response = tarjetaDatosBancariosService.deleteBanksData(datosBancariosDeleteDTO, request);
		
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosGeneralSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosBancariosDTO> searchGeneralData(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request) { 
		DatosBancariosDTO response = tarjetaDatosBancariosService.searchGeneralData(numPagina, datosBancariosSearchDTO, request);
		return new ResponseEntity<DatosBancariosDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/MandatosSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MandatosDTO> searchMandatos(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request) { 
		MandatosDTO response = tarjetaDatosBancariosService.searchMandatos(numPagina, datosBancariosSearchDTO, request);
		return new ResponseEntity<MandatosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosInsert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> insertBanksData(@RequestBody DatosBancariosInsertDTO datosBancariosInsertDTO, HttpServletRequest request) throws IOException, NamingException, SQLException { 
		UpdateResponseDTO response = tarjetaDatosBancariosService.insertBanksData(datosBancariosInsertDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/comboEsquema",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelColegios(HttpServletRequest request) {
		ComboDTO response = tarjetaDatosBancariosService.getLabelEsquema(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/mandatosInsert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateMandatos(@RequestBody MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) throws IOException, NamingException, SQLException { 
		UpdateResponseDTO response = tarjetaDatosBancariosService.updateMandatos(mandatosUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	

}
