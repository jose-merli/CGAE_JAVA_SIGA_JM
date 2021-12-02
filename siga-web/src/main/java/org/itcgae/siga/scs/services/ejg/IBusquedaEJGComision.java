package org.itcgae.siga.scs.services.ejg;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.ActualizarAnioActaItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.commons.utils.SigaExceptions;

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

	ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request);
	
	UpdateResponseDTO editarActaAnio(List<EjgItem> ejgItem, HttpServletRequest request) throws SigaExceptions;

	UpdateResponseDTO editarResolucionFundamento(List<EjgItem>ejgItem, HttpServletRequest request) throws Exception;

	UpdateResponseDTO editarPonente(List<EjgItem>ejgItem, HttpServletRequest request) throws SigaExceptions;

	UpdateResponseDTO borrarResolucionFundamento(List<EjgItem>ejgItem, HttpServletRequest request) throws Exception;

	UpdateResponseDTO borrarPonente(List<EjgItem> ejgItem, HttpServletRequest request) throws SigaExceptions;

	UpdateResponseDTO borrarActaAnio(List<EjgItem> ejgItem, HttpServletRequest request) throws SigaExceptions;

	String obligatorioFundamento(HttpServletRequest request);

	EjgDTO busquedaEJGActaComision(EjgItem ejgItem, HttpServletRequest request);

	UpdateResponseDTO asociarEJGActa(EjgItem ejgItem, HttpServletRequest request) throws SigaExceptions;



	

	
}
