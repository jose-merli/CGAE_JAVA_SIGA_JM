package org.itcgae.siga.fac.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ComunicacionCobroDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.EstadosAbonosDTO;
import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
import org.itcgae.siga.DTO.fac.EstadosPagosDTO;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.DTO.fac.FacDisqueteDevolucionesNuevoItem;
import org.itcgae.siga.DTO.fac.FacFacturacionEliminarItem;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaDTO;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.DTO.fac.FacRegistroFichContaDTO;
import org.itcgae.siga.DTO.fac.FacturaDTO;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FacturaLineaDTO;
import org.itcgae.siga.DTO.fac.FacturaLineaItem;
import org.itcgae.siga.DTO.fac.FacturasIncluidasDTO;
import org.itcgae.siga.DTO.fac.FicherosAbonosDTO;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesDTO;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.DTO.fac.InformeFacturacionDTO;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.ConsultaDestinatarioItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.FinalidadConsultaDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IContabilidadExportacionService;
import org.itcgae.siga.fac.services.IFacturacionPySExportacionesService;
import org.itcgae.siga.fac.services.IFacturacionPySFacturasService;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/facturacionPyS")
public class FacturacionPySController {

	@Autowired
	private IFacturacionPySService facturacionService;

	@Autowired
	private IFacturacionPySExportacionesService facturacionPySExportacionesService;
	
	@Autowired
	private IContabilidadExportacionService contabilidadExportacionService;

	@Autowired
	private IFacturacionPySFacturasService facturacionPySFacturasService;

	@GetMapping(value = "/getCuentasBancarias")
	ResponseEntity<CuentasBancariasDTO> getCuentasBancarias(@RequestParam(required = false) String idCuenta,
			HttpServletRequest request) {
		CuentasBancariasDTO response = new CuentasBancariasDTO();

		try {
			response = facturacionService.getCuentasBancarias(idCuenta, request);
			return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/reactivarCuentasBancarias")
	ResponseEntity<UpdateResponseDTO> reactivarCuentasBancarias(
			@RequestBody List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = this.facturacionService.reactivarCuentasBancarias(cuentasBancarias, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/validarIBANCuentaBancaria")
	ResponseEntity<CuentasBancariasDTO> validarIBANCuentaBancaria(@RequestBody CuentasBancariasItem cuentaBancaria,
			HttpServletRequest request) {
		CuentasBancariasDTO response = new CuentasBancariasDTO();

		try {
			response = facturacionService.validarIBANCuentaBancaria(cuentaBancaria, request);
			return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<CuentasBancariasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/insertaCuentaBancaria")
	ResponseEntity<InsertResponseDTO> insertaCuentaBancaria(@RequestBody CuentasBancariasItem cuentaBancaria,
			HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = this.facturacionService.insertaCuentaBancaria(cuentaBancaria, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/actualizaCuentaBancaria")
	ResponseEntity<UpdateResponseDTO> actualizaCuentaBancaria(@RequestBody CuentasBancariasItem cuentaBancaria,
			HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = this.facturacionService.actualizaCuentaBancaria(cuentaBancaria, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DestinatariosSeriesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/nuevoDestinatariosSerie")
	ResponseEntity<CreateResponseDTO> nuevoDestinatariosSerie(
			@RequestBody DestinatariosSeriesItem destinatariosSeriesItem, HttpServletRequest request) {
		CreateResponseDTO response = new CreateResponseDTO();

		try {
			response = facturacionService.nuevoDestinatariosSerie(destinatariosSeriesItem, request);
			return new ResponseEntity<CreateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<CreateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminaDestinatariosSerie")
	ResponseEntity<DeleteResponseDTO> eliminaDestinatariosSerie(
			@RequestBody List<DestinatariosSeriesItem> destinatariosSeriesItems, HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionService.eliminaDestinatariosSerie(destinatariosSeriesItems, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getConsultasSerie")
	ResponseEntity<ConsultasDTO> getConsultasSerie(@RequestParam String idSerieFacturacion,
			HttpServletRequest request) {
		ConsultasDTO response = new ConsultasDTO();

		try {
			response = facturacionService.getConsultasSerie(idSerieFacturacion, request);
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFinalidadConsultasSerie")
	ResponseEntity<FinalidadConsultaDTO> getFinalidadConsultasSerie(@RequestBody ConsultaDestinatarioItem consulta,
			HttpServletRequest request) {
		FinalidadConsultaDTO response = new FinalidadConsultaDTO();

		try {
			response = facturacionService.getFinalidadConsultasSerie(consulta, request);
			return new ResponseEntity<FinalidadConsultaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FinalidadConsultaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/nuevaConsultaSerie")
	ResponseEntity<CreateResponseDTO> nuevaConsultaSerieSerie(@RequestBody ConsultaDestinatarioItem consulta,
			HttpServletRequest request) {
		CreateResponseDTO response = new CreateResponseDTO();

		try {
			response = facturacionService.nuevaConsultaSerie(consulta, request);
			return new ResponseEntity<CreateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<CreateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminaConsultasSerie")
	ResponseEntity<DeleteResponseDTO> eliminaConsultasSerie(@RequestBody List<ConsultaDestinatarioItem> consultas,
			HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionService.eliminaConsultasSerie(consultas, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getContadoresSerie")
	ResponseEntity<ContadorSeriesDTO> getContadoresSerie(HttpServletRequest request) {
		ContadorSeriesDTO response = new ContadorSeriesDTO();

		try {
			response = facturacionService.getContadoresSerie(request);
			return new ResponseEntity<ContadorSeriesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarContadorSerie")
	ResponseEntity<InsertResponseDTO> guardarContadorSerie(@RequestBody ContadorSeriesItem contador,
			HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionService.guardarContadorSerie(contador, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFicherosAdeudos")
	ResponseEntity<FicherosAdeudosDTO> getFicherosAdeudos(@RequestBody FicherosAdeudosItem item,
			HttpServletRequest request) {
		FicherosAdeudosDTO response = new FicherosAdeudosDTO();

		try {
			response = facturacionPySExportacionesService.getFicherosAdeudos(item, request);

			if (response.getFicherosAdeudosItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FicherosAdeudosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
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
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UsosSufijosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFacturacionesProgramadas")
	ResponseEntity<FacFacturacionprogramadaDTO> getFacturacionesProgramadas(
			@RequestBody FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) {
		FacFacturacionprogramadaDTO response = new FacFacturacionprogramadaDTO();

		try {
			response = facturacionService.getFacturacionesProgramadas(facturacionProgramadaItem, request);

			if (response.getFacturacionprogramadaItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacFacturacionprogramadaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacFacturacionprogramadaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminarFacturacion")
	ResponseEntity<DeleteResponseDTO> eliminarFacturacion(@RequestBody FacFacturacionEliminarItem facturacionEliminar,
			HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = this.facturacionService.eliminarFacturacion(facturacionEliminar, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFicherosTransferencias")
	ResponseEntity<FicherosAbonosDTO> getFicherosTransferencias(@RequestBody FicherosAbonosItem item,
			HttpServletRequest request) {
		FicherosAbonosDTO response = new FicherosAbonosDTO();

		try {
			response = facturacionPySExportacionesService.getFicherosTransferencias(item, request);

			if (response.getFicherosAbonosItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FicherosAbonosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FicherosAbonosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/nuevoFicheroTransferencias")
	ResponseEntity<InsertResponseDTO> nuevoFicheroTransferencias(@RequestBody List<FacturaItem> abonoItems,
														   HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySExportacionesService.nuevoFicheroTransferencias(abonoItems, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/nuevoFicheroTransferenciasSjcs")
	ResponseEntity<InsertResponseDTO> nuevoFicheroTransferenciasSjcs(@RequestBody List<FacturaItem> abonoItems,
																 HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySExportacionesService.nuevoFicheroTransferenciasSjcs(abonoItems, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminarFicheroTransferencias")
	ResponseEntity<DeleteResponseDTO> eliminarFicheroTransferencias(@RequestBody FicherosAbonosItem ficherosAbonosItem,
														  HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionPySExportacionesService.eliminarFicheroTransferencias(ficherosAbonosItem, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFicherosDevoluciones")
	ResponseEntity<FicherosDevolucionesDTO> getFicherosDevoluciones(@RequestBody FicherosDevolucionesItem item,
			HttpServletRequest request) {
		FicherosDevolucionesDTO response = new FicherosDevolucionesDTO();

		try {
			response = facturacionPySExportacionesService.getFicherosDevoluciones(item, request);

			if (response.getFicherosDevolucionesItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FicherosDevolucionesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FicherosDevolucionesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/archivarFacturaciones")
	ResponseEntity<UpdateResponseDTO> archivarFacturaciones(
			@RequestBody List<FacFacturacionprogramadaItem> facturacionProgramadaItems, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.archivarFacturaciones(facturacionProgramadaItems, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/insertarProgramacionFactura")
	ResponseEntity<InsertResponseDTO> insertarProgramacionFactura(
			@RequestBody FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionService.insertarProgramacionFactura(facturacionProgramadaItem, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/actualizarProgramacionFactura")
	ResponseEntity<UpdateResponseDTO> actualizarProgramacionFactura(
			@RequestBody FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.actualizarProgramacionFactura(facturacionProgramadaItem, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFacturas")
	ResponseEntity<FacturaDTO> getFacturas(@RequestBody FacturaItem item, HttpServletRequest request) {
		FacturaDTO response = new FacturaDTO();

		try {
			response = facturacionService.getFacturas(item, request);

			if (response.getFacturasItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacturaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getFactura")
	ResponseEntity<FacturaDTO> getFactura(@RequestParam String idFactura, @RequestParam String idAbono, @RequestParam String tipo,
			HttpServletRequest request) {
		FacturaDTO response = new FacturaDTO();

		try {
			response = facturacionService.getFactura(idFactura, idAbono, tipo, request);

			if (response.getFacturasItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacturaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardaDatosFactura")
	ResponseEntity<UpdateResponseDTO> guardaDatosFactura(@RequestBody FacturaItem item, HttpServletRequest request) {

		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.guardaDatosFactura(item, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getLineasFactura")
	ResponseEntity<FacturaLineaDTO> getLineasFactura(@RequestParam String idFactura, HttpServletRequest request) {
		FacturaLineaDTO response = new FacturaLineaDTO();

		try {
			response = facturacionService.getLineasFactura(idFactura, request);

			if (response.getFacturasLineasItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacturaLineaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturaLineaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getLineasAbono")
	ResponseEntity<FacturaLineaDTO> getLineasAbono(@RequestParam String idAbono, HttpServletRequest request) {
		FacturaLineaDTO response = new FacturaLineaDTO();

		try {
			response = facturacionService.getLineasAbono(idAbono, request);

			if (response.getFacturasLineasItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacturaLineaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturaLineaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarLineasFactura")
	ResponseEntity<UpdateResponseDTO> guardarLineasFactura(@RequestBody FacturaLineaItem item,
			HttpServletRequest request) {

		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.guardarLineasFactura(item, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarLineasAbono")
	ResponseEntity<UpdateResponseDTO> guardarLineasAbono(@RequestBody FacturaLineaItem item,
			HttpServletRequest request) {

		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.guardarLineasAbono(item, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getComunicacionCobro")
	ResponseEntity<ComunicacionCobroDTO> getComunicacionCobro(@RequestParam String idFactura,
			HttpServletRequest request) {
		ComunicacionCobroDTO response = new ComunicacionCobroDTO();

		try {
			response = facturacionService.getComunicacionCobro(idFactura, request);

			if (response.getComunicacionCobroItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<ComunicacionCobroDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComunicacionCobroDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getEstadosPagos")
	ResponseEntity<EstadosPagosDTO> getEstadosPagos(@RequestParam String idFactura, HttpServletRequest request) {
		EstadosPagosDTO response = new EstadosPagosDTO();

		try {
			response = facturacionService.getEstadosPagos(idFactura, request);

			if (response.getEstadosPagosItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<EstadosPagosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<EstadosPagosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getEstadosAbonos")
	ResponseEntity<EstadosAbonosDTO> getEstadosAbonos(@RequestParam String idAbono, HttpServletRequest request) {
		EstadosAbonosDTO response = new EstadosAbonosDTO();

		try {
			response = facturacionPySFacturasService.getEstadosAbonos(idAbono, request);

			if (response.getEstadosAbonosItems().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<EstadosAbonosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<EstadosAbonosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/insertarEstadosPagos")
	ResponseEntity<InsertResponseDTO> insertarEstadosPagos(@RequestBody EstadosPagosItem item,
			HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.insertarEstadosPagos(item, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminarEstadosPagos")
	ResponseEntity<DeleteResponseDTO> borrarCuentasBancarias(@RequestBody EstadosPagosItem item,
			HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionService.eliminarEstadosPagos(item, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/compensarAbono")
	ResponseEntity<InsertResponseDTO> compensarAbono(@RequestBody EstadosAbonosItem item,
															 HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.compensarAbono(item, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/compensarAbonoVarios")
	ResponseEntity<InsertResponseDTO> compensarAbonoVarios(@RequestBody List<EstadosAbonosItem> items,
													 HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.compensarAbonoVarios(items, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/pagarPorCajaAbono")
	ResponseEntity<InsertResponseDTO> pagarPorCajaAbono(@RequestBody EstadosAbonosItem item,
														 HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.pagarPorCajaAbono(item, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/pagarPorCajaAbonoVarios")
	ResponseEntity<InsertResponseDTO> pagarPorCajaAbonoVarios(@RequestBody List<EstadosAbonosItem> items,
														   HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.pagarPorCajaAbonoVarios(items, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminarPagoPorCajaAbono")
	ResponseEntity<DeleteResponseDTO> eliminarPagoPorCajaAbono(@RequestBody EstadosAbonosItem item,
															HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionPySFacturasService.eliminarPagoPorCajaAbono(item, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/renegociarAbono")
	ResponseEntity<InsertResponseDTO> renegociarAbono(@RequestBody EstadosAbonosItem item,
															HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.renegociarAbono(item, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/renegociarAbonoVarios")
	ResponseEntity<InsertResponseDTO> renegociarAbonoVarios(@RequestBody List<EstadosAbonosItem> items,
															  HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySFacturasService.renegociarAbonoVarios(items, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/nuevoFicheroAdeudos")
	ResponseEntity<InsertResponseDTO> nuevoFicheroAdeudos(@RequestBody FicherosAdeudosItem item,
															   HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySExportacionesService.nuevoFicheroAdeudos(item, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/nuevoFicheroDevoluciones")
	ResponseEntity<InsertResponseDTO> nuevoFicheroDevoluciones(@ModelAttribute FacDisqueteDevolucionesNuevoItem item,
														  HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionPySExportacionesService.nuevoFicheroDevoluciones(item, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminarFicheroDevoluciones")
	ResponseEntity<DeleteResponseDTO> eliminarFicheroDevoluciones(@RequestBody FicherosDevolucionesItem item,
																  HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionPySExportacionesService.eliminarFicheroDevoluciones(item, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/actualizarFicheroAdeudos")
	ResponseEntity<UpdateResponseDTO> actualizarFicheroAdeudos(@RequestBody FicherosAdeudosItem item,
			HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionPySExportacionesService.actualizarFicheroAdeudos(item, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/eliminarFicheroAdeudos")
	ResponseEntity<DeleteResponseDTO> eliminarFicheroAdeudos(@RequestBody FicherosAdeudosItem item,
																	HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionPySExportacionesService.eliminarFicheroAdeudos(item, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (BusinessException e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/actualizarFicheroTranferencias")
	ResponseEntity<UpdateResponseDTO> actualizarFicheroTranferencias(@RequestBody FacDisqueteabonos item,
			HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionPySExportacionesService.actualizarFicheroTranferencias(item, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getFacturasIncluidas")
	ResponseEntity<FacturasIncluidasDTO> getFacturasIncluidas(@RequestParam String idFichero,
			@RequestParam String tipoFichero, HttpServletRequest request) {
		FacturasIncluidasDTO response = new FacturasIncluidasDTO();

		try {
			response = facturacionService.getFacturasIncluidas(idFichero, tipoFichero, request);
			return new ResponseEntity<FacturasIncluidasDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturasIncluidasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/searchExportacionContabilidad")
	ResponseEntity<FacRegistroFichContaDTO> search(@RequestBody FacRegistroFichConta facRegistroFichConta,
			HttpServletRequest request) {
		FacRegistroFichContaDTO response = new FacRegistroFichContaDTO();
		try {
			response = contabilidadExportacionService.search(facRegistroFichConta, request);
			return new ResponseEntity<FacRegistroFichContaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacRegistroFichContaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/maxIdContabilidad")
	ResponseEntity<FacRegistroFichContaDTO> maxIdContabilidad(HttpServletRequest request) {
		FacRegistroFichContaDTO response = new FacRegistroFichContaDTO();
		try {
			response = contabilidadExportacionService.maxIdContabilidad(request);
			return new ResponseEntity<FacRegistroFichContaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacRegistroFichContaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/guardarRegistroFichConta")
	ResponseEntity<UpdateResponseDTO> guardarRegistroFichConta(@RequestBody FacRegistroFichConta facRegistroFichConta,
			HttpServletRequest request) throws Exception{

		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = contabilidadExportacionService.guardarRegistroFichConta(facRegistroFichConta, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/desactivarReactivarRegistroFichConta")
	ResponseEntity<DeleteResponseDTO> desactivarReactivarRegistroFichConta(@RequestBody List <FacRegistroFichConta> facRegistrosFichConta,
			HttpServletRequest request) throws Exception{

		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = contabilidadExportacionService.desactivarReactivarRegistroFichConta(facRegistrosFichConta, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getInformeFacturacion")
	ResponseEntity<InformeFacturacionDTO> getInformeFacturacion(@RequestParam String idSerieFacturacion, @RequestParam String idProgramacion, HttpServletRequest request) {
		InformeFacturacionDTO response = new InformeFacturacionDTO();

		try {
			response = facturacionService.getInformeFacturacion(idSerieFacturacion, idProgramacion, request);

			if (response.getInformeFacturacion().size() == 200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<InformeFacturacionDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InformeFacturacionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/descargarFicheroAdeudos", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> descargarFicheroAdeudos(@RequestBody List<FicherosAdeudosItem> disqueteItems, HttpServletRequest request) throws Exception {
		return facturacionPySExportacionesService.descargarFicheroAdeudos(disqueteItems, request);
	}

	@RequestMapping(value = "/descargarFicheroTransferencias", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> descargarFicheroTransferencias(@RequestBody List<FicherosAbonosItem> disqueteItems, HttpServletRequest request) throws Exception {
		return facturacionPySExportacionesService.descargarFicheroTransferencias(disqueteItems, request);
	}

	@RequestMapping(value = "/descargarFicheroDevoluciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> descargarFicheroDevoluciones(@RequestBody List<FicherosDevolucionesItem> disqueteItems, HttpServletRequest request) throws Exception {
		return facturacionPySExportacionesService.descargarFicheroDevoluciones(disqueteItems, request);
	}

	@RequestMapping(value = "/descargarFichaFacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> descargarFichaFacturacion(@RequestBody List<FacFacturacionprogramadaItem> facturacionItems, HttpServletRequest request) throws Exception {
		return facturacionService.descargarFichaFacturacion(facturacionItems, request);

	}

	@PostMapping(value = "/eliminarAbonoSJCSCaja")
	ResponseEntity<DeleteResponseDTO> eliminarAbonoSJCSCaja(@RequestBody EstadosPagosItem item,
														   HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionService.eliminarAbonoSJCSCaja(item, request);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/generarExcel", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> generateExcel(@RequestBody TarjetaPickListSerieDTO etiquetas, HttpServletRequest request) throws Exception {
	
		ResponseFileDTO response = facturacionService.generateExcel(etiquetas, request);

		File file = response.getFile();		
		HttpHeaders headers = null;
		InputStreamResource resource = null;
		
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		if(response.isResultados()){
			try {
				resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			System.out.println("The length of the file is : "+file.length());
			return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.OK);
		}else{
			if(response.getError() != null && response.getError().getCode() == 400) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SinArchivo\"");
				return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.BAD_REQUEST);
			}else{
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SinArchivo\"");
				return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.NO_CONTENT);
			}			
		}
	}
	
	@RequestMapping(value = "/generarExcelAbonos", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<InputStreamResource> generateExcelAbonos(
			@RequestBody FacAbonoItem facAbonosItem, 
			HttpServletRequest request) throws Exception {
	
		ResponseFileDTO response = facturacionService.generateExcelAbonos(facAbonosItem, request);

		File file = response.getFile();		
		HttpHeaders headers = null;
		InputStreamResource resource = null;
		
		headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		if(response.isResultados()){
			try {
				resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			System.out.println("The length of the file is : "+file.length());
			return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.OK);
		}else{
			if(response.getError() != null && response.getError().getCode() == 400) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SinArchivo\"");
				return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.BAD_REQUEST);
			}else{
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SinArchivo\"");
				return new ResponseEntity<InputStreamResource>(resource,headers, HttpStatus.NO_CONTENT);
			}			
		}

	}
	
	@RequestMapping(value = "/descargarFicherosContabilidad", method = RequestMethod.POST, produces = "application/zip")
	ResponseEntity<InputStreamResource> descargarFicherosContabilidad(@RequestBody List <FacRegistroFichConta> facRegistrosFichConta, HttpServletRequest request) {
		return contabilidadExportacionService.descargarFicherosContabilidad(facRegistrosFichConta, request);		
	}
	
}