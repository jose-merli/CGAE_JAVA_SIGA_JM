package org.itcgae.siga.scs.services.ejg;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;

public interface IBusquedaEJG {

	ComboDTO comboTipoEJG(HttpServletRequest request);

	ComboDTO comboTipoColegioEjg(HttpServletRequest request);

	ComboDTO comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen);

	ComboDTO comboResolucion(HttpServletRequest request);

	ComboDTO comboFundamentoImpug(HttpServletRequest request);

	ComboDTO comboPreceptivo(HttpServletRequest request);

	ComboDTO comboRenuncia(HttpServletRequest request);

	ComboDTO comboCreadoDesde(HttpServletRequest request);

	ComboDTO ComboFundamentoJurid(HttpServletRequest request, String resolucion);

	ComboDTO comboEstadoEJG(HttpServletRequest request, String resolucion);

	EjgDTO busquedaEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboImpugnacion(HttpServletRequest request);

	ComboDTO comboJuzgados(HttpServletRequest request);

	ComboDTO comboPonente(HttpServletRequest request);

	ComboDTO comboTurnosTipo(HttpServletRequest request, String idtipoturno);

	ColegiadosSJCSDTO busquedaColegiadoEJG(ColegiadosSJCSItem datos, HttpServletRequest request);
	
	ComboDTO comboTurnos(String pantalla, HttpServletRequest request);
}
