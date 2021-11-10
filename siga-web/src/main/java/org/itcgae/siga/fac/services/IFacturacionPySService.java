package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface IFacturacionPySService {

	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request);

	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request);

	public UpdateResponseDTO guardarCuentaBancaria(CuentasBancariasItem cuentaBancaria,
											HttpServletRequest request);

	public UpdateResponseDTO insertaActualizaSerie(List<UsosSufijosItem> usosSufijosItems,
												   HttpServletRequest request);

	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem,
													 HttpServletRequest request);

	public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request);

	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request);

	public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion, HttpServletRequest request);

	public UpdateResponseDTO guardarEtiquetasSerieFacturacion(TarjetaPickListSerieDTO etiquetas,
			HttpServletRequest request);

	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request);

	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request);

	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request);

	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request);

	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request);

	public UpdateResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request);

	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request);
}
