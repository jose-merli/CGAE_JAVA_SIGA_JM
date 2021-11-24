package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ICombosServices {
	
	public ComboDTO comboFactEstados(HttpServletRequest request);
	
	public ComboDTO comboFactConceptos(HttpServletRequest request);
	
	public ComboDTO comboFactColegio(HttpServletRequest request);
	
	public ComboDTO comboPagosColegio(HttpServletRequest request);
	public ComboDTO comboColegiosProcuradores(HttpServletRequest request);

	public ComboDTO comboPagoEstados(HttpServletRequest request);
	
	public ComboDTO comboFacturaciones(HttpServletRequest request);	
	
	public ComboDTO comboFactMovimientos(HttpServletRequest request);
	
	public ComboDTO comboAplicadoEnPago(HttpServletRequest request);
	
	public ComboDTO comboAgrupacionEnTurnos(HttpServletRequest request);

	public ComboDTO comboTiposMovimientos(HttpServletRequest request);
}
