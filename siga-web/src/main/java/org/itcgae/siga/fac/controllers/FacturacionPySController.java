package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping(value = "/comboPlanificacion")
	ResponseEntity<ComboDTO> comboPlanificacion(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = facturacionService.comboPlanificacion(idSerieFacturacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> guardarSerieFacturacion(@RequestBody SerieFacturacionItem serieFacturacion, HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.guardarSerieFacturacion(serieFacturacion, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/getEtiquetasSerie")
	ResponseEntity<ComboDTO> getEtiquetasSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = facturacionService.getEtiquetasSerie(idSerieFacturacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getDestinatariosSeries")
	ResponseEntity<DestinatariosSeriesDTO> getDestinatariosSeries(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		DestinatariosSeriesDTO response = facturacionService.getDestinatariosSeries(idSerieFacturacion, request);
		return new ResponseEntity<DestinatariosSeriesDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboPlantillasEnvio")
	ResponseEntity<ComboDTO> getDestinatariosSeries(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboPlantillasEnvio(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getFormasPagosDisponiblesSeries")
	ResponseEntity<ComboDTO> getFormasPagosDisponiblesSeries(HttpServletRequest request) {
		ComboDTO response = facturacionService.getFormasPagosDisponiblesSeries(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getFormasPagosSerie")
	ResponseEntity<ComboDTO> getFormasPagosSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = facturacionService.getFormasPagosSerie(idSerieFacturacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboModelosComunicacion")
	ResponseEntity<ComboDTO> comboModelosComunicacion(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboModelosComunicacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
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
	ResponseEntity<UpdateResponseDTO> guardarEtiquetasSerieFacturacion(@RequestBody TarjetaPickListSerieDTO etiquetas, HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.guardarEtiquetasSerieFacturacion(etiquetas, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarFormasPagosSerie")
	ResponseEntity<UpdateResponseDTO> guardarFormasPagosSerie(@RequestBody TarjetaPickListSerieDTO formasPagos, HttpServletRequest request) {
		UpdateResponseDTO response = facturacionService.guardarFormasPagosSerie(formasPagos, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getUsosSufijos")
	ResponseEntity<UsosSufijosDTO> getUsosSufijos(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		UsosSufijosDTO response = facturacionService.getUsosSufijos(idSerieFacturacion, request);
		return new ResponseEntity<UsosSufijosDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboTiposIVA")
	ResponseEntity<ComboDTO> comboTiposIVA(HttpServletRequest request) {
		ComboDTO response = facturacionService.comboTiposIVA(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}


}
