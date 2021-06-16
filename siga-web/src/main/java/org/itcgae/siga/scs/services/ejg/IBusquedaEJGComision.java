package org.itcgae.siga.scs.services.ejg;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;

public interface IBusquedaEJGComision {

	ComboDTO comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen);

	ComboDTO getLabelColegiosCol(HttpServletRequest request);

	ComboDTO comboPonente(HttpServletRequest request);

	ComboDTO comboFundamentoJurid(HttpServletRequest request, String resolucion);

	EjgDTO busquedaEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboTipoEJG(HttpServletRequest request);

	ComboDTO comboEstadoEJG(HttpServletRequest request, String resolucion);

	ComboDTO comboResolucion(HttpServletRequest request);

	ComboDTO comboJuzgados(HttpServletRequest request);

	ComboDTO comboTurnosTipo(HttpServletRequest request, String idTurno);

	
}
