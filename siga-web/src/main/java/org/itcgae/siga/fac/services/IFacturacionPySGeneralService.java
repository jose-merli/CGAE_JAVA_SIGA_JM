package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFacturacionPySGeneralService {

	public ComboDTO comboCuentasBancarias(HttpServletRequest request);

	public ComboDTO comboSufijos(HttpServletRequest request);

	public ComboDTO comboSeriesFacturacion(HttpServletRequest request);

	public ComboDTO comboEtiquetas(HttpServletRequest request);

	public ComboDTO comboDestinatarios(HttpServletRequest request);

	public ComboDTO comboContadores(HttpServletRequest request);

	public ComboDTO comboContadoresRectificativas(HttpServletRequest request);

	public ComboDTO comboPlanificacion(String idSerieFacturacion, HttpServletRequest request);

	public ComboDTO comboPlantillasEnvio(HttpServletRequest request);

	public ComboDTO getFormasPagosDisponiblesSeries(HttpServletRequest request);

	public ComboDTO getFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request);

	public ComboDTO comboModelosComunicacion(HttpServletRequest request);

	public ComboDTO comboTiposIVA(HttpServletRequest request);

	public ComboDTO comboEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request);
}
