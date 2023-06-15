package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboDictamenDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionItem;
import org.itcgae.siga.scs.services.maestros.IBusquedaFundamentosCalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;


@RestController
@RequestMapping(value = "/maestros")
public class FundamentosCalificacionController {

	@Autowired
	private IBusquedaFundamentosCalificacionService busquedaFundamentosCalificacionService;
	
	
	@GetMapping(value = "/busquedaFundamentosCalificacion/comboDictamen" ,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDictamenDTO> comboDictamen(HttpServletRequest request) {
		ComboDictamenDTO response = busquedaFundamentosCalificacionService.comboDictamen(request);
		return new ResponseEntity<ComboDictamenDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaFundamentosCalificacion/searchFundamentos", produces =  MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FundamentosCalificacionDTO> searchFundamentos(@RequestBody FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request ){
		FundamentosCalificacionDTO response = busquedaFundamentosCalificacionService.searchFundamentos(fundamentosCalificacionItem,request);
		return new ResponseEntity<FundamentosCalificacionDTO> (response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/busquedaFundamentosCalificacion/deleteFundamentos", produces =  MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteFundamentos(@RequestBody FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request){
		UpdateResponseDTO response = busquedaFundamentosCalificacionService.deleteFundamentos(fundamentosCalificacionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
	}

	@PostMapping(value = "/busquedaFundamentosCalificacion/activateFundamentos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> activateFundamentos(@RequestBody FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request){
		UpdateResponseDTO response = busquedaFundamentosCalificacionService.activateFundamentos(fundamentosCalificacionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/busquedaFundamentosCalificacion/createFundamento", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> insertFundamento(@RequestBody FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request){
		InsertResponseDTO response = busquedaFundamentosCalificacionService.insertFundamentos(fundamentosCalificacionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);	}
	
	@PostMapping(value = "/busquedaFundamentosCalificacion/updateFundamentoCalificacion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> updateFundamentoCalificacion(@RequestBody FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request){
		UpdateResponseDTO response = busquedaFundamentosCalificacionService.updateFundamentosCalificacion(fundamentosCalificacionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
