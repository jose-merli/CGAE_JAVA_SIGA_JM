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
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFacturacionPySService {
	
	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request);
	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request);
	public ComboDTO comboCuentasBancarias(HttpServletRequest request);
	public ComboDTO comboSufijos(HttpServletRequest request);
	public ComboDTO comboSeriesFacturacion(HttpServletRequest request);
	public ComboDTO comboEtiquetas(HttpServletRequest request);
	public ComboDTO comboDestinatarios(HttpServletRequest request);
	public ComboDTO comboContadores(HttpServletRequest request);
	public ComboDTO comboContadoresRectificativas(HttpServletRequest request);
	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, HttpServletRequest request);
    public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request);
	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems, HttpServletRequest request);
    public ComboDTO comboPlanificacion(String idSerieFacturacion, HttpServletRequest request);
    public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion, HttpServletRequest request);
	public ComboDTO getEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request);
	public UpdateResponseDTO guardarEtiquetasSerieFacturacion(TarjetaPickListSerieDTO etiquetas, HttpServletRequest request);
	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request);
	public ComboDTO comboPlantillasEnvio(HttpServletRequest request);
	public ComboDTO getFormasPagosDisponiblesSeries(HttpServletRequest request);
	public ComboDTO getFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request);
	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request);
	public ComboDTO comboModelosComunicacion(HttpServletRequest request);
	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request);
	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request);
	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request);
    public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request);
    public ComboDTO comboTiposIVA(HttpServletRequest request);
}
 