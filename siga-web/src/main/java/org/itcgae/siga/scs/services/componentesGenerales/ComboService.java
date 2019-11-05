package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

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
	
}
