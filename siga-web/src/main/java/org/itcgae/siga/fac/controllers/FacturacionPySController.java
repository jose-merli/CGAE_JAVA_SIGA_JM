package org.itcgae.siga.fac.controllers;

import org.itcgae.siga.DTO.fac.ComunicacionCobroDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.EstadosPagosDTO;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.DTO.fac.FacFacturacionEliminarItem;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaDTO;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTO.fac.FacPresentacionAdeudosDTO;
import org.itcgae.siga.DTO.fac.FacPresentacionAdeudosItem;
import org.itcgae.siga.DTO.fac.FacRegenerarPresentacionAdeudosDTO;
import org.itcgae.siga.DTO.fac.FacRegenerarPresentacionAdeudosItem;
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
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.itcgae.siga.db.entities.FacDisquetecargos;
import org.itcgae.siga.db.entities.FacDisquetedevoluciones;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/facturacionPyS")
public class FacturacionPySController {

	@Autowired
	private IFacturacionPySService facturacionService;

	@GetMapping(value = "/getCuentasBancarias")
	ResponseEntity<CuentasBancariasDTO> getCuentasBancarias(@RequestParam(required = false) String idCuenta, HttpServletRequest request) {
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
	ResponseEntity<CreateResponseDTO> nuevoDestinatariosSerie(@RequestBody DestinatariosSeriesItem destinatariosSeriesItem,
																  HttpServletRequest request) {
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
	ResponseEntity<DeleteResponseDTO> eliminaDestinatariosSerie(@RequestBody List<DestinatariosSeriesItem> destinatariosSeriesItems,
																  HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();

		try {
			response = facturacionService.eliminaDestinatariosSerie(destinatariosSeriesItems, request);
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
	ResponseEntity<InsertResponseDTO> guardarContadorSerie(@RequestBody ContadorSeriesItem contador, HttpServletRequest request) {
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
			response = facturacionService.getFicherosAdeudos(item, request);
			
			if(response.getFicherosAdeudosItems().size()==200) {
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
	ResponseEntity<FacFacturacionprogramadaDTO> getFacturacionesProgramadas(@RequestBody FacFacturacionprogramadaItem facturacionProgramadaItem,
															  HttpServletRequest request) {
		FacFacturacionprogramadaDTO response = new FacFacturacionprogramadaDTO();

		try {
			response = facturacionService.getFacturacionesProgramadas(facturacionProgramadaItem, request);

			if(response.getFacturacionprogramadaItems().size()==200) {
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
			response = facturacionService.getFicherosTransferencias(item, request);

			if(response.getFicherosAbonosItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FicherosAbonosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FicherosAbonosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/getFicherosDevoluciones")
	ResponseEntity<FicherosDevolucionesDTO> getFicherosDevoluciones(@RequestBody FicherosDevolucionesItem item,
														  HttpServletRequest request) {
		FicherosDevolucionesDTO response = new FicherosDevolucionesDTO();

		try {
			response = facturacionService.getFicherosDevoluciones(item, request);

			if(response.getFicherosDevolucionesItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FicherosDevolucionesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FicherosDevolucionesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/archivarFacturaciones")
	ResponseEntity<UpdateResponseDTO> archivarFacturaciones(@RequestBody List<FacFacturacionprogramadaItem> facturacionProgramadaItems,
																			HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();

		try {
			response = facturacionService.archivarFacturaciones(facturacionProgramadaItems, request);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/presentacionAdeudos")
	ResponseEntity<FacPresentacionAdeudosDTO> presentacionAdeudos(@RequestBody FacPresentacionAdeudosItem presentacionAdeudoItem,
																			HttpServletRequest request) {
		FacPresentacionAdeudosDTO response = new FacPresentacionAdeudosDTO();

		try {
			response = facturacionService.presentacionAdeudos(presentacionAdeudoItem, request);
			return new ResponseEntity<FacPresentacionAdeudosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacPresentacionAdeudosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/regenerarPresentacionAdeudos")
	ResponseEntity<FacRegenerarPresentacionAdeudosDTO> regenerarPresentacionAdeudos(@RequestBody FacRegenerarPresentacionAdeudosItem regenerarPresentacionAdeudoItem,
																			HttpServletRequest request) {
		FacRegenerarPresentacionAdeudosDTO response = new FacRegenerarPresentacionAdeudosDTO();

		try {
			response = facturacionService.regenerarPresentacionAdeudos(regenerarPresentacionAdeudoItem, request);
			return new ResponseEntity<FacRegenerarPresentacionAdeudosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacRegenerarPresentacionAdeudosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/insertarProgramacionFactura")
	 ResponseEntity<InsertResponseDTO>  insertarProgramacionFactura(@RequestBody FacFacturacionprogramadaItem facturacionProgramadaItem,
																			HttpServletRequest request) {
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
	ResponseEntity<UpdateResponseDTO>  actualizarProgramacionFactura(@RequestBody FacFacturacionprogramadaItem facturacionProgramadaItem,
																   HttpServletRequest request) {
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
	ResponseEntity<FacturaDTO> getFacturas(@RequestBody FacturaItem item,
										   HttpServletRequest request) {
		FacturaDTO response = new FacturaDTO();

		try {
			response = facturacionService.getFacturas(item, request);

			if(response.getFacturasItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacturaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getFactura")
	ResponseEntity<FacturaDTO> getFactura(@RequestParam String idFactura, @RequestParam String tipo,
										   HttpServletRequest request) {
		FacturaDTO response = new FacturaDTO();

		try {
			response = facturacionService.getFactura(idFactura, tipo, request);

			if(response.getFacturasItems().size()==200) {
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
	ResponseEntity<FacturaLineaDTO> getLineasFactura(@RequestParam String idFactura,
										   HttpServletRequest request) {
		FacturaLineaDTO response = new FacturaLineaDTO();

		try {
			response = facturacionService.getLineasFactura(idFactura, request);

			if(response.getFacturasLineasItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<FacturaLineaDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturaLineaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping(value = "/getLineasAbono")
    ResponseEntity<FacturaLineaDTO> getLineasAbono(@RequestParam String idAbono,
                                                     HttpServletRequest request) {
        FacturaLineaDTO response = new FacturaLineaDTO();

        try {
            response = facturacionService.getLineasAbono(idAbono, request);

            if(response.getFacturasLineasItems().size()==200) {
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

			if(response.getComunicacionCobroItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<ComunicacionCobroDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<ComunicacionCobroDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getEstadosPagos")
	ResponseEntity<EstadosPagosDTO> getEstadosPagos(@RequestParam String idFactura,
													HttpServletRequest request) {
		EstadosPagosDTO response = new EstadosPagosDTO();

		try {
			response = facturacionService.getEstadosPagos(idFactura, request);

			if(response.getEstadosPagosItems().size()==200) {
				response.setError(UtilidadesString.creaInfoResultados());
			}

			return new ResponseEntity<EstadosPagosDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<EstadosPagosDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/insertarEstadosPagos")
	ResponseEntity<InsertResponseDTO> insertarEstadosPagos(@RequestBody EstadosPagosItem item,
													HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();

		try {
			response = facturacionService.insertarEstadosPagos(item, request);
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

    @PostMapping(value = "/actualizarFicheroAdeudos")
    ResponseEntity<UpdateResponseDTO> actualizarFicheroAdeudos(@RequestBody FacDisquetecargos item,
                                                              HttpServletRequest request) {
        UpdateResponseDTO response = new UpdateResponseDTO();

        try {
            response = this.facturacionService.actualizarFicheroAdeudos(item, request);
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(UtilidadesString.creaError(e.getMessage()));
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/actualizarFicheroDevoluciones")
    ResponseEntity<UpdateResponseDTO> actualizarFicheroDevoluciones(@RequestBody FacDisquetedevoluciones item,
                                                               HttpServletRequest request) {
        UpdateResponseDTO response = new UpdateResponseDTO();

        try {
            response = this.facturacionService.actualizarFicheroDevoluciones(item, request);
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(UtilidadesString.creaError(e.getMessage()));
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/actualizarFicheroTranferencias")
    ResponseEntity<UpdateResponseDTO> actualizarFicheroTranferencias(@RequestBody FacDisqueteabonos item,
                                                               HttpServletRequest request) {
        UpdateResponseDTO response = new UpdateResponseDTO();

        try {
            response = this.facturacionService.actualizarFicheroTranferencias(item, request);
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(UtilidadesString.creaError(e.getMessage()));
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@GetMapping(value = "/getFacturasIncluidas")
	ResponseEntity<FacturasIncluidasDTO> getFacturasIncluidas(@RequestParam String idFichero, @RequestParam String tipoFichero, HttpServletRequest request) {
		FacturasIncluidasDTO response = new FacturasIncluidasDTO();

		try {
			response = facturacionService.getFacturasIncluidas(idFichero, tipoFichero, request);
			return new ResponseEntity<FacturasIncluidasDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setError(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<FacturasIncluidasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}