package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.gen.ComboDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICombosServices {
    
    ComboDTO comboEstadoAbonos(HttpServletRequest request);

    ComboDTO comboFactEstados(HttpServletRequest request);

    ComboDTO comboFactConceptos(HttpServletRequest request);

    ComboDTO comboFactColegio(HttpServletRequest request);

    ComboDTO comboPagosColegio(HttpServletRequest request);

    ComboDTO comboColegiosProcuradores(HttpServletRequest request);

    ComboDTO comboPagoEstados(HttpServletRequest request);

    ComboDTO comboFacturaciones(HttpServletRequest request);

    ComboDTO getComboDestinatarios(HttpServletRequest request);

    ComboDTO getComboPagosRetenciones(HttpServletRequest request);
    
    ComboDTO getComboColegios(HttpServletRequest request);
    
    ComboDTO getComboGrupoFacturacionByColegios(List<String> idColegios, HttpServletRequest request);
 
    ComboDTO comboFactMovimientos(HttpServletRequest request);
	
	ComboDTO comboAplicadoEnPago(HttpServletRequest request);
	
	ComboDTO comboAgrupacionEnTurnos(HttpServletRequest request);

	ComboDTO comboTiposMovimientos(HttpServletRequest request);

	ComboDTO comboCertificacionSJCS(HttpServletRequest request);

    ComboDTO comboFactByPartidaPresu(String idpartidapresupuestaria, HttpServletRequest request);

    ComboDTO comboFactNull(HttpServletRequest request);

    ComboDTO comboFactBaremos(HttpServletRequest request);
    
    ComboDTO comboGrupoFacturacion(HttpServletRequest request);

	ComboDTO comboPagos(HttpServletRequest request);
}
