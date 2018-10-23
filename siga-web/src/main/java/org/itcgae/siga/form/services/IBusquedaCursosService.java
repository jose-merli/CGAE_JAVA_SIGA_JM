package org.itcgae.siga.form.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaCursosService {

	public ComboDTO getVisibilidadCursos(HttpServletRequest request);
	
	public ComboDTO getEstadosCursos(HttpServletRequest request);
	
	public ComboDTO getTemasCursos(HttpServletRequest request);
	
	public CursoDTO searchCurso(CursoItem cursoItem, HttpServletRequest request);
	
	
}
