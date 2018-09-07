package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaColegiadosService {

	public ComboDTO getCivilStatus(HttpServletRequest request);
	
	public ComboDTO getSituacion(HttpServletRequest request);
	
	public ComboDTO getCVCategory(HttpServletRequest request);
	
}
