package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTOs.gen.ComboDTO;

import javax.servlet.http.HttpServletRequest;

public interface IFacturacionPySGeneralService {

	public ComboDTO comboCuentasBancarias(HttpServletRequest request) throws Exception;

	public ComboDTO comboSufijos(HttpServletRequest request) throws Exception;

	public ComboDTO comboTiposProductos(HttpServletRequest request) throws Exception;

	public ComboDTO comboTiposServicios(HttpServletRequest request) throws Exception;

	public ComboDTO comboSeriesFacturacion(HttpServletRequest request) throws Exception;

	public ComboDTO comboEtiquetas(HttpServletRequest request) throws Exception;

	public ComboDTO comboDestinatarios(HttpServletRequest request) throws Exception;

	public ComboDTO comboContadores(HttpServletRequest request) throws Exception;

	public ComboDTO comboContadoresRectificativas(HttpServletRequest request) throws Exception;

	public ComboDTO comboPlanificacion(String idSerieFacturacion, HttpServletRequest request) throws Exception;

	public ComboDTO comboPlantillasEnvio(HttpServletRequest request) throws Exception;

	public ComboDTO comboFormasPagoFactura(HttpServletRequest request) throws Exception;

	public ComboDTO comboFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception;

	public ComboDTO comboModelosComunicacion(HttpServletRequest request) throws Exception;

	public ComboDTO comboTiposIVA(HttpServletRequest request) throws Exception;

	public ComboDTO comboEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception;

    public ComboDTO comboEstadosFact(String tipo, HttpServletRequest request) throws Exception;

    public ComboDTO comboEstadosFacturas(HttpServletRequest request) throws Exception;

    public ComboDTO comboFacturaciones(HttpServletRequest request) throws Exception;

	public ComboDTO parametrosSEPA(String idInstitucion, HttpServletRequest request) throws Exception;

    public ComboDTO parametrosCONTROL(String idInstitucion, HttpServletRequest request) throws Exception;

}
