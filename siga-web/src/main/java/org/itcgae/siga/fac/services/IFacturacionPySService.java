package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IFacturacionPySService {

	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request) throws Exception;

	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
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

	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request)
			throws Exception;

	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request) throws Exception;

	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request) throws Exception;

	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request) throws Exception;

	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) throws Exception;
}
