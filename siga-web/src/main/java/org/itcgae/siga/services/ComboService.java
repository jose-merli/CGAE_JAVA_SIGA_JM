package org.itcgae.siga.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;

public interface ComboService {
	
	public ComboDTO comboTurnos(HttpServletRequest request);
	
	public ComboDTO comboAreas(HttpServletRequest request);
	
	public ComboDTO comboMaterias(HttpServletRequest request, String idArea,String filtro);
	
	public ComboDTO comboTiposTurno(HttpServletRequest request,String idLenguaje);

	public ComboDTO comboTiposGuardia(HttpServletRequest request,String idLenguaje);

	
}
