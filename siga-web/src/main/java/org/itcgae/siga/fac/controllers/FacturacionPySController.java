package org.itcgae.siga.fac.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.utils.UtilidadesString;
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

@RestController
@RequestMapping(value = "/facturacionPyS")
public class FacturacionPySController {

	@Autowired
	private IFacturacionPySService facturacionService;

	@GetMapping(value = "/getCuentasBancarias")
	ResponseEntity<CuentasBancariasDTO> getCuentasBancarias(HttpServletRequest request) {
		CuentasBancariasDTO response = new CuentasBancariasDTO();

		try {
			response = facturacionService.getCuentasBancarias(request);
			return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getSeriesFacturacion")
	ResponseEntity<SeriesFacturacionDTO> getSeriesFacturacion(@RequestBody SerieFacturacionItem serieFacturacionItem,
			HttpServletRequest request) {
		SeriesFacturacionDTO response = new SeriesFacturacionDTO();

		try {
			response = facturacionService.getSeriesFacturacion(serieFacturacionItem, request);
			return new ResponseEntity<SeriesFacturacionDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<SeriesFacturacionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/borrarCuentasBancarias")
	ResponseEntity<DeleteResponseDTO> borrarCuentasBancarias(@RequestBody List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = this.facturacionService.borrarCuentasBancarias(cuentasBancarias, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/insertaActualizaSerie")
	ResponseEntity<UpdateResponseDTO> insertaActualizaSerie(@RequestBody List<UsosSufijosItem> usosSufijos,
															  HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.insertaActualizaSerie(usosSufijos, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminaSerieFacturacion")
	ResponseEntity<DeleteResponseDTO> eliminaSerieFacturacion(
			@RequestBody List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionService.eliminaSerieFacturacion(serieFacturacionItems, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/reactivarSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> reactivarSerieFacturacion(
			@RequestBody List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.reactivarSerieFacturacion(serieFacturacionItems, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> guardarSerieFacturacion(@RequestBody SerieFacturacionItem serieFacturacion,
			HttpServletRequest request) {

		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.guardarSerieFacturacion(serieFacturacion, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getDestinatariosSeries")
	ResponseEntity<DestinatariosSeriesDTO> getDestinatariosSeries(@RequestParam String idSerieFacturacion,
			HttpServletRequest request) {
		DestinatariosSeriesDTO response = new DestinatariosSeriesDTO();

		try {
			response = facturacionService.getDestinatariosSeries(idSerieFacturacion, request);
			return new ResponseEntity<DestinatariosSeriesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<DestinatariosSeriesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getContadoresSerie")
	ResponseEntity<ContadorSeriesDTO> getContadoresSerie(HttpServletRequest request) {
		ContadorSeriesDTO response = new ContadorSeriesDTO();

		try {
			response = facturacionService.getContadoresSerie(request);
			return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getContadoresRectificativasSerie")
	ResponseEntity<ContadorSeriesDTO> getContadoresRectificativasSerie(HttpServletRequest request) {
		ContadorSeriesDTO response = new ContadorSeriesDTO();

		try {
			response = facturacionService.getContadoresRectificativasSerie(request);
			return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarEtiquetasSerieFacturacion")
	ResponseEntity<UpdateResponseDTO> guardarEtiquetasSerieFacturacion(@RequestBody TarjetaPickListSerieDTO etiquetas,
			HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.guardarEtiquetasSerieFacturacion(etiquetas, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarFormasPagosSerie")
	ResponseEntity<UpdateResponseDTO> guardarFormasPagosSerie(@RequestBody TarjetaPickListSerieDTO formasPagos,
			HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.guardarFormasPagosSerie(formasPagos, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarContadorSerie")
	ResponseEntity<UpdateResponseDTO> guardarContadorSerie(@RequestBody ContadorSeriesItem contador, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			 response = facturacionService.guardarContadorSerie(contador, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFicherosAdeudos")
	ResponseEntity<FicherosAdeudosDTO> getFicherosAdeudos(@RequestBody FicherosAdeudosItem item,
			HttpServletRequest request) {
		FicherosAdeudosDTO response = new FicherosAdeudosDTO();

		try {
			response = facturacionService.getFicherosAdeudos(item, request);
			
			if(response.getFicherosAdeudosItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}
			
			return new ResponseEntity<FicherosAdeudosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<FicherosAdeudosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getUsosSufijos")
	ResponseEntity<UsosSufijosDTO> getUsosSufijos(@RequestParam String codBanco, HttpServletRequest request) {
		UsosSufijosDTO response = new UsosSufijosDTO();

		try {
			response = facturacionService.getUsosSufijos(codBanco, request);
			return new ResponseEntity<UsosSufijosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<UsosSufijosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}