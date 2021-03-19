package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;

public interface ComboService {
	
	public ComboDTO comboTurnos(HttpServletRequest request);
	
	public ComboDTO comboAreas(HttpServletRequest request);
	
	public ComboDTO comboMaterias(HttpServletRequest request, String idArea,String filtro);
	
	public ComboDTO comboTiposTurno(HttpServletRequest request);

	public ComboDTO comboTiposGuardia(HttpServletRequest request);

	public ComboDTO getComboZonas(HttpServletRequest request);

	public ComboDTO getComboSubZonas(HttpServletRequest request, String idZona);

	public ComboDTO getJurisdicciones(HttpServletRequest request);

	public ComboDTO getComboGrupoFacturacion(HttpServletRequest request);

	public ComboDTO getComboPartidasPresupuestarias(HttpServletRequest request);

	public ComboDTO comboRequisitosGuardias(HttpServletRequest request);
	
	public ComboDTO comboGuardias(HttpServletRequest request, String idTurno);
	
	public ComboColaOrdenadaDTO ordenCola(HttpServletRequest request, String idordenacioncolas);

	public ComboDTO comboGuardiasUpdate(HttpServletRequest request, String idTurno);

	public ComboDTO comboEstadoEjg(HttpServletRequest request);

	public ComboDTO comboTipoEjg(HttpServletRequest request);
	
	public ComboDTO comboTipoEjgColegio(HttpServletRequest request);

	public ComboDTO comboTipoDesignacion(HttpServletRequest request);

	public ComboDTO comboTipoSOJ(HttpServletRequest request);

	public ComboDTO comboComisaria(HttpServletRequest request);

	public ComboDTO comboJuzgado(HttpServletRequest request);
	
	public ComboDTO comboJuzgadoDesignaciones(HttpServletRequest request);
}
