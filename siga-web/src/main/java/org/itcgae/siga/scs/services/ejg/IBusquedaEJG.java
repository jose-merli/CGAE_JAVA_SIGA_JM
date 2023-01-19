package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;

public interface IBusquedaEJG {

	ComboDTO comboTipoEJG(HttpServletRequest request);

	ComboDTO comboTipoColegioEjg(HttpServletRequest request);
	
	ComboDTO comboFundamentoCalificacion(HttpServletRequest request, String[] list_dictamen);

	ComboDTO comboResolucion(HttpServletRequest request, String origen);

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

	ComboDTO comboRemesa(HttpServletRequest request);

	InsertResponseDTO anadirExpedienteARemesa(List<EjgItem> datos, HttpServletRequest request) throws Exception;
}
