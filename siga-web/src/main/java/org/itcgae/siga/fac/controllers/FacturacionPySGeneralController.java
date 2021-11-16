package org.itcgae.siga.fac.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
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
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboCuentasBancarias(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboSufijos")
	ResponseEntity<ComboDTO> comboSufijos(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboSufijos(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboSeriesFacturacion")
	ResponseEntity<ComboDTO> comboSeriesFacturacion(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.comboSeriesFacturacion(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEtiquetas")
	ResponseEntity<ComboDTO> comboEtiquetas(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.comboEtiquetas(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboDestinatarios")
	ResponseEntity<ComboDTO> comboDestinatarios(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.comboDestinatarios(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboContadores")
	ResponseEntity<ComboDTO> comboContadores(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboContadores(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboContadoresRectificativas")
	ResponseEntity<ComboDTO> comboContadoresRectificativas(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboContadoresRectificativas(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboPlanificacion")
	ResponseEntity<ComboDTO> comboPlanificacion(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboPlanificacion(idSerieFacturacion, request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEtiquetasSerie")
	ResponseEntity<ComboDTO> comboEtiquetasSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboEtiquetasSerie(idSerieFacturacion, request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboPlantillasEnvio")
	ResponseEntity<ComboDTO> comboPlantillasEnvio(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboPlantillasEnvio(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getFormasPagosDisponiblesSeries")
	ResponseEntity<ComboDTO> getFormasPagosDisponiblesSeries(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.getFormasPagosDisponiblesSeries(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getFormasPagosSerie")
	ResponseEntity<ComboDTO> getFormasPagosSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.getFormasPagosSerie(idSerieFacturacion, request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboModelosComunicacion")
	ResponseEntity<ComboDTO> comboModelosComunicacion(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.comboModelosComunicacion(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboTiposIVA")
	ResponseEntity<ComboDTO> comboTiposIVA(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.comboTiposIVA(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/parametrosSEPA")
	ResponseEntity<ComboDTO> parametrosSEPA(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.parametrosSEPA(idInstitucion, request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
