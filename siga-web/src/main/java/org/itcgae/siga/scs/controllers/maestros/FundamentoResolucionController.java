package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.scs.services.maestros.IBusquedaFundamentoResolucionService;
import org.itcgae.siga.scs.services.maestros.IGestionFundamentoResolucionService;
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
public class FundamentoResolucionController {

	@Autowired
	private IBusquedaFundamentoResolucionService busquedaFundamentoResolucionService;
	
	@Autowired
	private IGestionFundamentoResolucionService gestionFundamentoResolucionService;
	
	@RequestMapping(value = "/gestionFundamentosResolucion/searchFundamentosResolucion", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FundamentoResolucionDTO> searchFundamentosResolucion(@RequestBody FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request) {
		FundamentoResolucionDTO response = busquedaFundamentoResolucionService.searchFundamentosResolucion(fundamentoResolucionItem, request);
		return new ResponseEntity<FundamentoResolucionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionFundamentosResolucion/deleteFundamentosResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteFundamentos(@RequestBody FundamentoResolucionDTO fundamentoResolucionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaFundamentoResolucionService.deleteFundamentosResolucion(fundamentoResolucionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionFundamentosResolucion/activateFundamentosResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateFundamentos(@RequestBody FundamentoResolucionDTO fundamentoResolucionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaFundamentoResolucionService.activateFundamentosResolucion(fundamentoResolucionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionFundamentosResolucion/getResoluciones", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getResoluciones(HttpServletRequest request) {
		ComboDTO response = gestionFundamentoResolucionService.getResoluciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionFundamentosResolucion/updateFundamentoResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateFundamentoResolucion(@RequestBody FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionFundamentoResolucionService.updateFundamentoResolucion(fundamentoResolucionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionFundamentosResolucion/createFundamentoResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createFundamentoResolucion(@RequestBody FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionFundamentoResolucionService.createFundamentoResolucion(fundamentoResolucionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	

	
}
