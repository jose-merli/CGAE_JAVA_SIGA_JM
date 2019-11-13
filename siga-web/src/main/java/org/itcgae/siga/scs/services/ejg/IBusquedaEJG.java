package org.itcgae.siga.scs.services.ejg;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoejgExtendsMapper;

public interface IBusquedaEJG {

	ComboDTO comboTipoEJG(HttpServletRequest request);

	ComboDTO comboTipoColegioEjg(HttpServletRequest request);

	ComboDTO comboFundamentoCalificacion(HttpServletRequest request);

	ComboDTO comboResolucion(HttpServletRequest request);

	ComboDTO comboFundamentoImpug(HttpServletRequest request);

	ComboDTO comboPreceptivo(HttpServletRequest request);

	ComboDTO comboRenuncia(HttpServletRequest request);

	ComboDTO comboCreadoDesde(HttpServletRequest request);

	ComboDTO ComboFundamentoJurid(HttpServletRequest request, String resolucion);

}
