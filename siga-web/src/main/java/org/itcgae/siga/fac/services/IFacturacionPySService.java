package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.FacFacturacionEliminarItem;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaDTO;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTO.fac.FacPresentacionAdeudosDTO;
import org.itcgae.siga.DTO.fac.FacPresentacionAdeudosItem;
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

public interface IFacturacionPySService {

	public CuentasBancariasDTO getCuentasBancarias(String idCuenta, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) throws Exception;

	public InsertResponseDTO insertaCuentaBancaria(CuentasBancariasItem cuentaBancaria,
												   HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizaCuentaBancaria(CuentasBancariasItem cuentaBancaria,
											  HttpServletRequest request) throws Exception;

	public UpdateResponseDTO insertaActualizaSerie(List<UsosSufijosItem> usosSufijosItems,
												   HttpServletRequest request) throws Exception;

	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem,
													 HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request) throws Exception;

	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion, HttpServletRequest request)
			throws Exception;

	public UpdateResponseDTO guardarEtiquetasSerieFacturacion(TarjetaPickListSerieDTO etiquetas,
			HttpServletRequest request) throws Exception;

	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request)
			throws Exception;

    public CreateResponseDTO nuevoDestinatariosSerie(DestinatariosSeriesItem destinatariosSeriesItem,
                                              HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminaDestinatariosSerie(List<DestinatariosSeriesItem> destinatariosSeriesItems,
												HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request)
			throws Exception;

	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request) throws Exception;

	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request) throws Exception;

	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request) throws Exception;

	public InsertResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request) throws Exception;

	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) throws Exception;

    public FacFacturacionprogramadaDTO getFacturacionesProgramadas(FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) throws Exception;
    
	public DeleteResponseDTO eliminarFacturacion(FacFacturacionEliminarItem fac,
			HttpServletRequest request) throws Exception;

	public FicherosAbonosDTO getFicherosTransferencias(FicherosAbonosItem item, HttpServletRequest request) throws Exception;

	public FicherosDevolucionesDTO getFicherosDevoluciones(FicherosDevolucionesItem item, HttpServletRequest request) throws Exception;
	
	public UpdateResponseDTO archivarFacturaciones(List<FacFacturacionprogramadaItem> facturacionProgramadaItems, HttpServletRequest request) throws Exception;

	public FacPresentacionAdeudosDTO presentacionAdeudos(FacPresentacionAdeudosItem presentacionAdeudoItem,	HttpServletRequest request) throws Exception;

}
