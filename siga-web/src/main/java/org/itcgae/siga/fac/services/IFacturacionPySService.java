package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ComunicacionCobroDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.FacFacturacionEliminarItem;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaDTO;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTO.fac.FacturaDTO;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FacturaLineaDTO;
import org.itcgae.siga.DTO.fac.FacturaLineaItem;
import org.itcgae.siga.DTO.fac.FacturasIncluidasDTO;
import org.itcgae.siga.DTO.fac.FaseFacturacionProgramadaDTO;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IFacturacionPySService {

	public CuentasBancariasDTO getCuentasBancarias(String idCuenta, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request) throws Exception;
	
	public DeleteResponseDTO borrarCuentasBancariasIndividual(CuentasBancariasItem cuentaBancaria, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO reactivarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request) throws Exception;

	public CuentasBancariasDTO validarIBANCuentaBancaria(CuentasBancariasItem cuentaBancaria, HttpServletRequest request) throws Exception;

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

	public ConsultasDTO getConsultasSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception;

	public FinalidadConsultaDTO getFinalidadConsultasSerie(ConsultaDestinatarioItem consulta, HttpServletRequest request) throws Exception;

	public CreateResponseDTO nuevaConsultaSerie(ConsultaDestinatarioItem consulta, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminaConsultasSerie(List<ConsultaDestinatarioItem> consultas, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request) throws Exception;

	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request) throws Exception;

	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request) throws Exception;

	public InsertResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request) throws Exception;

	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) throws Exception;

	public FacFacturacionprogramadaDTO getFacturacionesProgramadas(FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO eliminarFacturacion(List<FacFacturacionEliminarItem> fac, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO archivarFacturaciones(List<FacFacturacionprogramadaItem> facturacionProgramadaItems, HttpServletRequest request) throws Exception;

	public FacturaDTO getFacturas(FacturaItem item, HttpServletRequest request) throws Exception;

	public FacturaDTO getFactura(String idFactura, String idAbono, String tipo, HttpServletRequest request) throws Exception;

	public InsertResponseDTO insertarProgramacionFactura(FacFacturacionprogramadaItem facturacionProg, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardaDatosFactura(FacturaItem item, HttpServletRequest request) throws Exception;

	public FacturaLineaDTO getLineasFactura(String idFactura, HttpServletRequest request) throws Exception;

	public FacturaLineaDTO getLineasAbono(String idAbono, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarLineasFactura(FacturaLineaItem item, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO guardarLineasAbono(FacturaLineaItem item, HttpServletRequest request) throws Exception;

	public UpdateResponseDTO actualizarProgramacionFactura(FacFacturacionprogramadaItem facItem, HttpServletRequest request) throws Exception;

	public ComunicacionCobroDTO getComunicacionCobro(String idFactura, HttpServletRequest request) throws Exception;

	public FacturasIncluidasDTO getFacturasIncluidas(String idFichero, String tipoFichero, HttpServletRequest request) throws Exception;

	public InformeFacturacionDTO getInformeFacturacion(String idSerieFacturacion, String idProgramacion, HttpServletRequest request) throws Exception;

	public ResponseEntity<InputStreamResource> descargarFichaFacturacion(List<FacFacturacionprogramadaItem> facturacionItems, HttpServletRequest request) throws Exception;

	public ResponseFileDTO generateExcel(TarjetaPickListSerieDTO etiquetas, HttpServletRequest request)  throws Exception;

	public ResponseFileDTO generateExcelAbonos(FacAbonoItem facAbonosItem, HttpServletRequest request) throws Exception;

	FaseFacturacionProgramadaDTO getFasesFacturacionProgramada(HttpServletRequest request, String idInstitucion, String idSerieFacturacion, String idProgramacion) throws Exception;

	public ResponseEntity<InputStreamResource> descargarLogFacturacion(List<FacFacturacionprogramadaItem> facturacionItems, HttpServletRequest request) throws Exception;
}
