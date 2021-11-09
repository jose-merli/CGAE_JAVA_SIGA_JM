package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.fac.services.IFacturacionPySGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/facturacionPyS/general")
public class FacturacionPySGeneralController {

	@Autowired
	private IFacturacionPySGeneralService facturacionGeneralService;

	@GetMapping(value = "/comboCuentasBancarias")
	ResponseEntity<ComboDTO> comboCuentasBancarias(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboCuentasBancarias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboSufijos")
	ResponseEntity<ComboDTO> comboSufijos(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboSufijos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboSeriesFacturacion")
	ResponseEntity<ComboDTO> comboSeriesFacturacion(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboSeriesFacturacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboEtiquetas")
	ResponseEntity<ComboDTO> comboEtiquetas(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboEtiquetas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboDestinatarios")
	ResponseEntity<ComboDTO> comboDestinatarios(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboDestinatarios(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboContadores")
	ResponseEntity<ComboDTO> comboContadores(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboContadores(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboContadoresRectificativas")
	ResponseEntity<ComboDTO> comboContadoresRectificativas(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboContadoresRectificativas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboPlanificacion")
	ResponseEntity<ComboDTO> comboPlanificacion(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboPlanificacion(idSerieFacturacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboEtiquetasSerie")
	ResponseEntity<ComboDTO> comboEtiquetasSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboEtiquetasSerie(idSerieFacturacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboPlantillasEnvio")
	ResponseEntity<ComboDTO> getDestinatariosSeries(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboPlantillasEnvio(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getFormasPagosDisponiblesSeries")
	ResponseEntity<ComboDTO> getFormasPagosDisponiblesSeries(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.getFormasPagosDisponiblesSeries(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getFormasPagosSerie")
	ResponseEntity<ComboDTO> getFormasPagosSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.getFormasPagosSerie(idSerieFacturacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboModelosComunicacion")
	ResponseEntity<ComboDTO> comboModelosComunicacion(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboModelosComunicacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboTiposIVA")
	ResponseEntity<ComboDTO> comboTiposIVA(HttpServletRequest request) {
		ComboDTO response = facturacionGeneralService.comboTiposIVA(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
}
