package org.itcgae.siga.fac.services;

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
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.itcgae.siga.db.entities.FacDisquetecargos;
import org.itcgae.siga.db.entities.FacDisquetedevoluciones;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFacturacionPySService {

	public CuentasBancariasDTO getCuentasBancarias(String idCuenta, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request) throws Exception;

    CuentasBancariasDTO validarIBANCuentaBancaria(CuentasBancariasItem cuentaBancaria, HttpServletRequest request) throws Exception;

    public InsertResponseDTO insertaCuentaBancaria(CuentasBancariasItem cuentaBancaria, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizaCuentaBancaria(CuentasBancariasItem cuentaBancaria, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO insertaActualizaSerie(List<UsosSufijosItem> usosSufijosItems, HttpServletRequest request) throws Exception;

	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarEtiquetasSerieFacturacion(TarjetaPickListSerieDTO etiquetas, HttpServletRequest request) throws Exception;

	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request) throws Exception;

    public CreateResponseDTO nuevoDestinatariosSerie(DestinatariosSeriesItem destinatariosSeriesItem, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminaDestinatariosSerie(List<DestinatariosSeriesItem> destinatariosSeriesItems, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request) throws Exception;

	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request) throws Exception;

	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request) throws Exception;

	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request) throws Exception;

	public InsertResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request) throws Exception;

	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) throws Exception;

    public FacFacturacionprogramadaDTO getFacturacionesProgramadas(FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminarFacturacion(FacFacturacionEliminarItem fac, HttpServletRequest request) throws Exception;

	public FicherosAbonosDTO getFicherosTransferencias(FicherosAbonosItem item, HttpServletRequest request) throws Exception;

	public FicherosDevolucionesDTO getFicherosDevoluciones(FicherosDevolucionesItem item, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO archivarFacturaciones(List<FacFacturacionprogramadaItem> facturacionProgramadaItems, HttpServletRequest request) throws Exception;

	public FacturaDTO getFacturas(FacturaItem item, HttpServletRequest request) throws Exception;

	public FacturaDTO getFactura(String idFactura, String tipo, HttpServletRequest request) throws Exception;

	public FacPresentacionAdeudosDTO presentacionAdeudos(FacPresentacionAdeudosItem presentacionAdeudoItem,	HttpServletRequest request) throws Exception;

	public FacRegenerarPresentacionAdeudosDTO regenerarPresentacionAdeudos(FacRegenerarPresentacionAdeudosItem regenerarPresentacionAdeudoItem,	HttpServletRequest request) throws Exception;

	public InsertResponseDTO insertarProgramacionFactura(FacFacturacionprogramadaItem facturacionProg, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardaDatosFactura(FacturaItem item, HttpServletRequest request) throws Exception;

	public FacturaLineaDTO getLineasFactura(String idFactura, HttpServletRequest request) throws Exception;

	public FacturaLineaDTO getLineasAbono(String idAbono, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarLineasFactura(FacturaLineaItem item, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarLineasAbono(FacturaLineaItem item, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizarProgramacionFactura(FacFacturacionprogramadaItem facItem, HttpServletRequest request) throws Exception;

	public ComunicacionCobroDTO getComunicacionCobro(String idFactura, HttpServletRequest request) throws Exception;

	public EstadosPagosDTO getEstadosPagos(String idFactura, HttpServletRequest request) throws Exception;

	public InsertResponseDTO insertarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizarFicheroAdeudos(FacDisquetecargos updateItem, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizarFicheroDevoluciones(FacDisquetedevoluciones updateItem, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizarFicheroTranferencias(FacDisqueteabonos updateItem, HttpServletRequest request) throws Exception;

	public FacturasIncluidasDTO getFacturasIncluidas(String idFichero, String tipoFichero, HttpServletRequest request) throws Exception;

}
