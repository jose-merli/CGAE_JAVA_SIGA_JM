package org.itcgae.siga.scs.services.ejg;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
//import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;

public interface IBusquedaEJGComision {

	ComboDTO comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen);

	ComboDTO getLabelColegiosCol(HttpServletRequest request);

	ComboDTO comboPonente(HttpServletRequest request);

	ComboDTO comboFundamentoJurid(HttpServletRequest request, String resolucion);

	EjgDTO busquedaEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboDictamen(HttpServletRequest request);

	ComboDTO obligatoriedadResolucion(HttpServletRequest request);

	ComboDTO comboTipoColegioEjg(HttpServletRequest request);

	ComboDTO comboEstadoEJG(HttpServletRequest request, String resolucion);

	ComboDTO comboResolucion(HttpServletRequest request);

	ComboDTO comboAnioActa(HttpServletRequest request);

	ComboDTO comboJuzgados(HttpServletRequest request);

	ComboDTO comboTurnosTipo(HttpServletRequest request, String idTurno);

	ComboDTO comboGuardias(HttpServletRequest request, String idTurno);

	ComboDTO comboPresidente(HttpServletRequest request);

	ComboDTO comboSecretario(HttpServletRequest request);

//	ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request);

	

	
}
