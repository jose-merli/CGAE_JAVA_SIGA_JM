package org.itcgae.siga.fac.services;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

import java.util.List;

public interface IFacturacionPySService {
	
	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request);
	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias, HttpServletRequest request);
	public ComboDTO comboCuentasBancarias(HttpServletRequest request);
	public ComboDTO comboSufijos(HttpServletRequest request);
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
	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request);
	public ComboDTO comboPlantillasEnvio(HttpServletRequest request);

}
 