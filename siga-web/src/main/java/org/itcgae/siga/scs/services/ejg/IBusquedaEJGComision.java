package org.itcgae.siga.scs.services.ejg;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;

public interface IBusquedaEJGComision {

	ComboDTO comboFundamentoCalificacion(String idInstitucion, HttpServletRequest request, String[] list_dictamen);

	ComboDTO getLabelColegiosCol(HttpServletRequest request);

	ComboDTO comboPonente(HttpServletRequest request);

	ComboDTO comboFundamentoJurid(HttpServletRequest request, String resolucion);

	EjgDTO busquedaEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboDictamen(String idInstitucion, HttpServletRequest request);

	ComboDTO comboEstadoEJG(String idInstitucion, HttpServletRequest request, String resolucion);

	ComboDTO comboJuzgados(String idInstitucion, HttpServletRequest request);

	ComboDTO comboTurnosTipo(String idInstitucion, HttpServletRequest request, String idTurno);

	ComboDTO comboGuardias(String idInstitucion, HttpServletRequest request, String idTurno);

	ComboDTO comboTipoColegioEjg(String idInstitucion, HttpServletRequest request);

	ComboDTO comboAnioActa(String idInstitucion, HttpServletRequest request);

	ComboDTO comboResolucion(String idInstitucion, HttpServletRequest request);
	

	
}
