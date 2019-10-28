package org.itcgae.siga.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ComboService {

	public ComboDTO getComboZonas(HttpServletRequest request);

	public ComboDTO getComboSubZonas(HttpServletRequest request, String idZona);

	public ComboDTO getJurisdicciones(HttpServletRequest request);

	public ComboDTO getComboGrupoFacturacion(HttpServletRequest request);

	public ComboDTO getComboPartidasPresupuestarias(HttpServletRequest request);
}
