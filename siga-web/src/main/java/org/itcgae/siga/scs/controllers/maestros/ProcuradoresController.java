package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.scs.services.maestros.IProcuradoresService;
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
public class ProcuradoresController {

	@Autowired
	private IProcuradoresService procuradoresService;
	
	@Autowired
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	@Autowired
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;
	
	@RequestMapping(value = "/gestionProcuradores/busquedaProcuradores",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> searchProcuradores(@RequestBody ProcuradorItem procuradorItem, HttpServletRequest request) {
		ProcuradorDTO response = procuradoresService.searchProcuradores(procuradorItem, request);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionProcuradores/provinces", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionProcuradores/colegios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getColegios(HttpServletRequest request) {
		ComboDTO response = tarjetaDatosIntegrantesService.getColegios(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionProcuradores/population", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPopulation(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request,idProvincia, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionProcuradores/deleteProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteProcuradores(@RequestBody ProcuradorDTO procuradorDTO, HttpServletRequest request) {

		UpdateResponseDTO response = procuradoresService.deleteProcuradores(procuradorDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionProcuradores/activateProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateProcuradores(@RequestBody ProcuradorDTO procuradorDTO, HttpServletRequest request) {

		UpdateResponseDTO response = procuradoresService.activateProcuradores(procuradorDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionProcuradores/updateProcurador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateProcurador(@RequestBody ProcuradorItem procuradorItem, HttpServletRequest request) {

		UpdateResponseDTO response = procuradoresService.updateProcurador(procuradorItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionProcuradores/createProcurador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createProcurador(@RequestBody ProcuradorItem procuradorItem, HttpServletRequest request) {

		InsertResponseDTO response = procuradoresService.createProcurador(procuradorItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	
}