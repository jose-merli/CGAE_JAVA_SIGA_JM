package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;

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
	
	public ComboDTO comboGuardiasNoGrupo(HttpServletRequest request, String idTurno);
	
	public ComboDTO comboGuardiasGrupo(HttpServletRequest request, String idTurno);
	
	public ComboColaOrdenadaDTO ordenCola(HttpServletRequest request, String idordenacioncolas);

	public ComboDTO comboGuardiasUpdate(HttpServletRequest request, String idTurno);

	public ComboDTO comboEstadoEjg(HttpServletRequest request);

	public ComboDTO comboTipoEjg(HttpServletRequest request);
	
	public ComboDTO comboTipoEjgColegio(HttpServletRequest request);

	public ComboDTO comboTipoDesignacion(HttpServletRequest request);

	public ComboDTO comboTipoSOJ(HttpServletRequest request);

	public ComboDTO comboComisaria(HttpServletRequest request);

	public ComboDTO comboJuzgado(HttpServletRequest request);
	
	public ComboDTO comboComisariaCdgoExt(HttpServletRequest request);

	public ComboDTO comboJuzgadoCdgoExt(HttpServletRequest request);

	public ComboDTO comboListasGuardias(HttpServletRequest request, String idTurno);

	public ComboDTO comboEstados(HttpServletRequest request);

	public ComboDTO comboConjuntoGuardias(HttpServletRequest request);
	
	public ComboDTO comboJuzgadoDesignaciones(HttpServletRequest request);
	
	public ComboDTO comboJuzgadoPorInstitucion(String idInstitucion, HttpServletRequest request);
	
	public ComboDTO comboModulo(HttpServletRequest request);
	
	public ComboDTO comboDelitos(DesignaItem designaItem, HttpServletRequest request);
	
	public ComboDTO comboProcedimientos(HttpServletRequest request);
	
	public ComboDTO comboProcedimientosConJuzgado(HttpServletRequest request, String idJuzgado);
	
	public ComboDTO comboModulosConJuzgado(HttpServletRequest request, String idJuzgado);
	
	public ComboDTO comboAllModulos(HttpServletRequest request);
	
	public ComboDTO comboModulosConProcedimientos(HttpServletRequest request, String idPretension);
	
	public ComboDTO comboProcedimientosConModulo(HttpServletRequest request, String idModulo);
	
	public ComboDTO comboAcreditacionesPorModulo(HttpServletRequest request, String idModulo, String idTurno);
	
	public ComboDTO comboTipoDocumentacionDesigna(HttpServletRequest request);
	
	public ComboDTO comboTurnosInscritoLetrado(HttpServletRequest request, String idPersona);
	
	public ComboDTO comboGuardiasInscritoLetrado(HttpServletRequest request, String idPersona, String idTurno);
}
