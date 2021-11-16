package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem2;

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

	public ComboDTO getFormasPagosDisponiblesSeries(HttpServletRequest request) throws Exception;

	public ComboDTO getFormasPagosSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception;

	public ComboDTO comboModelosComunicacion(HttpServletRequest request) throws Exception;

	public ComboDTO comboTiposIVA(HttpServletRequest request) throws Exception;

	public ComboDTO comboEtiquetasSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception;
	
	public ComboDTO parametrosSEPA(String idInstitucion, HttpServletRequest request) throws Exception;
}
