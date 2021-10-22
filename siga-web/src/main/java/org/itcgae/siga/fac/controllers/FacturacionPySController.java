package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/facturacionPyS")
public class FacturacionPySController {
	
	@Autowired 
	private IFacturacionPySService facturacionService;
	
	@GetMapping(value = "/getCuentasBancarias")
	ResponseEntity<CuentasBancariasDTO> getCuentasBancarias(HttpServletRequest request) { 
		CuentasBancariasDTO response = facturacionService.getCuentasBancarias(request);
		return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comboCuentasBancarias")
	ResponseEntity<ComboDTO> comboCuentasBancarias(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboCuentasBancarias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comboSufijos")
	ResponseEntity<ComboDTO> comboSufijos(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboSufijos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comboEtiquetas")
	ResponseEntity<ComboDTO> comboEtiquetas(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboEtiquetas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comboDestinatarios")
	ResponseEntity<ComboDTO> comboDestinatarios(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboDestinatarios(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comboContadores")
	ResponseEntity<ComboDTO> comboContadores(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboContadores(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comboContadoresRectificativas")
	ResponseEntity<ComboDTO> comboContadoresRectificativas(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboContadoresRectificativas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getSeriesFacturacion")
	ResponseEntity<SeriesFacturacionDTO> getSeriesFacturacion(@RequestBody SerieFacturacionItem serieFacturacionItem, HttpServletRequest request) {
		SeriesFacturacionDTO response = facturacionService.getSeriesFacturacion(serieFacturacionItem, request);
		return new ResponseEntity<SeriesFacturacionDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/borrarCuentasBancarias")
	ResponseEntity<DeleteResponseDTO> borrarCuentasBancarias(@RequestBody List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request) {
		DeleteResponseDTO response = this.facturacionService.borrarCuentasBancarias(cuentasBancarias, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/eliminaSerieFacturacion")
	ResponseEntity<DeleteResponseDTO> getSeriesFacturacion(@RequestBody List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		DeleteResponseDTO response = facturacionService.eliminaSerieFacturacion(serieFacturacionItems, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/reactivarSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> reactivarSerieFacturacion(@RequestBody List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.reactivarSerieFacturacion(serieFacturacionItems, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
}
