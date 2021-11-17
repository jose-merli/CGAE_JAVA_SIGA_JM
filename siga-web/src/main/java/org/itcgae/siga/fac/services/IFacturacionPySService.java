package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface IFacturacionPySService {

	public CuentasBancariasDTO getCuentasBancarias(String idCuenta, HttpServletRequest request) throws Exception;

	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) throws Exception;

	public CuentasBancariasDTO insertaCuentaBancaria(CuentasBancariasItem cuentaBancaria,
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

	public UpdateResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request) throws Exception;

	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) throws Exception;

}
