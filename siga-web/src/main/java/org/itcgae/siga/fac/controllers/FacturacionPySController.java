package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping(value = "/getSeriesFacturacion")
	ResponseEntity<SeriesFacturacionDTO> getSeriesFacturacion(@RequestBody SerieFacturacionItem serieFacturacionItem,
			HttpServletRequest request) {
		SeriesFacturacionDTO response = facturacionService.getSeriesFacturacion(serieFacturacionItem, request);
		return new ResponseEntity<SeriesFacturacionDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/borrarCuentasBancarias")
	ResponseEntity<DeleteResponseDTO> borrarCuentasBancarias(@RequestBody List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) {
		DeleteResponseDTO response = this.facturacionService.borrarCuentasBancarias(cuentasBancarias, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/eliminaSerieFacturacion")
	ResponseEntity<DeleteResponseDTO> getSeriesFacturacion(
			@RequestBody List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		DeleteResponseDTO response = facturacionService.eliminaSerieFacturacion(serieFacturacionItems, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/reactivarSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> reactivarSerieFacturacion(
			@RequestBody List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.reactivarSerieFacturacion(serieFacturacionItems, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> guardarSerieFacturacion(@RequestBody SerieFacturacionItem serieFacturacion,
			HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.guardarSerieFacturacion(serieFacturacion, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/getDestinatariosSeries")
	ResponseEntity<DestinatariosSeriesDTO> getDestinatariosSeries(@RequestParam String idSerieFacturacion,
			HttpServletRequest request) {
		DestinatariosSeriesDTO response = facturacionService.getDestinatariosSeries(idSerieFacturacion, request);
		return new ResponseEntity<DestinatariosSeriesDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getContadoresSerie")
	ResponseEntity<ContadorSeriesDTO> getContadoresSerie(HttpServletRequest request) {
		ContadorSeriesDTO response = facturacionService.getContadoresSerie(request);
		return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getContadoresRectificativasSerie")
	ResponseEntity<ContadorSeriesDTO> getContadoresRectificativasSerie(HttpServletRequest request) {
		ContadorSeriesDTO response = facturacionService.getContadoresRectificativasSerie(request);
		return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarEtiquetasSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> guardarEtiquetasSerieFacturacion(@RequestBody TarjetaPickListSerieDTO etiquetas,
			HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.guardarEtiquetasSerieFacturacion(etiquetas, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarFormasPagosSerie")
	ResponseEntity<UpdateResponseDTO> guardarFormasPagosSerie(@RequestBody TarjetaPickListSerieDTO formasPagos,
			HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.guardarFormasPagosSerie(formasPagos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/getFicherosAdeudos")
	ResponseEntity<FicherosAdeudosDTO> getFicherosAdeudos(@RequestBody FicherosAdeudosItem item,
			HttpServletRequest request) {
		FicherosAdeudosDTO response = facturacionService.getFicherosAdeudos(item, request);
		return new ResponseEntity<FicherosAdeudosDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getUsosSufijos")
	ResponseEntity<UsosSufijosDTO> getUsosSufijos(@RequestParam String codBanco, HttpServletRequest request) {
		UsosSufijosDTO response = facturacionService.getUsosSufijos(codBanco, request);
		return new ResponseEntity<UsosSufijosDTO>(response, HttpStatus.OK);
	}
}