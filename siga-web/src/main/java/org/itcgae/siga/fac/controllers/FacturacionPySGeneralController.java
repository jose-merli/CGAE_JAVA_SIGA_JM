package org.itcgae.siga.fac.controllers;

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

import javax.servlet.http.HttpServletRequest;

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

	@GetMapping(value = "/comboTiposProductos")
	ResponseEntity<ComboDTO> comboTiposProductos(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboTiposProductos(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboTiposServicios")
	ResponseEntity<ComboDTO> comboTiposServicios(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboTiposServicios(request);

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

	@GetMapping(value = "/comboFormasPagoFactura")
	ResponseEntity<ComboDTO> comboFormasPagoFactura(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboFormasPagoFactura(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboFormasPagosSerie")
	ResponseEntity<ComboDTO> comboFormasPagosSerie(@RequestParam String idSerieFacturacion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();
		
		try {
			response = facturacionGeneralService.comboFormasPagosSerie(idSerieFacturacion, request);

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
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/parametrosCONTROL")
	ResponseEntity<ComboDTO> parametrosCONTROL(@RequestParam String idInstitucion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.parametrosCONTROL(idInstitucion, request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEstadosFacturacion")
	ResponseEntity<ComboDTO> comboEstadosFacturacion(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboEstadosFact("C", request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEstadosFicheros")
	ResponseEntity<ComboDTO> comboEstadosFicheros(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboEstadosFact("P", request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEstadosEnvios")
	ResponseEntity<ComboDTO> comboEstadosEnvios(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboEstadosFact("E", request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEstadosTraspasos")
	ResponseEntity<ComboDTO> comboEstadosTraspasos(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboEstadosFact("T", request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboEstadosFacturas")
	ResponseEntity<ComboDTO> comboEstadosFacturas(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboEstadosFacturas(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/comboFacturaciones")
	ResponseEntity<ComboDTO> comboFacturaciones(HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.comboFacturaciones(request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {

			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/parametrosLINEAS")
	ResponseEntity<ComboDTO> parametrosLINEAS(@RequestParam(required = false) String idInstitucion, HttpServletRequest request) {
		ComboDTO response = new ComboDTO();

		try {
			response = facturacionGeneralService.parametrosLINEAS(idInstitucion, request);

			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
