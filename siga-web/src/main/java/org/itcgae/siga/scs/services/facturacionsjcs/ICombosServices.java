package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.gen.ComboDTO;

import javax.servlet.http.HttpServletRequest;

public interface ICombosServices {

    ComboDTO comboFactEstados(HttpServletRequest request);

    ComboDTO comboFactConceptos(HttpServletRequest request);

    ComboDTO comboFactColegio(HttpServletRequest request);

    ComboDTO comboPagosColegio(HttpServletRequest request);

    ComboDTO comboColegiosProcuradores(HttpServletRequest request);

    ComboDTO comboPagoEstados(HttpServletRequest request);

    ComboDTO comboFacturaciones(HttpServletRequest request);

    ComboDTO getComboDestinatarios(HttpServletRequest request);

    ComboDTO getComboPagosRetenciones(HttpServletRequest request);
}
