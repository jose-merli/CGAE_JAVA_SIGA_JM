package org.itcgae.siga.form.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaCursosService {

	public ComboDTO getVisibilidadCursos(HttpServletRequest request);
	
	public ComboDTO getEstadosCursos(HttpServletRequest request);
	
	public ComboDTO getTemasCursos(HttpServletRequest request);
	
	public CursoDTO searchCurso(CursoItem cursoItem, HttpServletRequest request);
	
	public InsertResponseDTO saveCourses(List<CursoItem> listCursoItem, HttpServletRequest request);
	
	public InsertResponseDTO duplicateCourse(CursoItem cursoItem, HttpServletRequest request);
	
	public int archivarCursos(List<CursoItem> listCursoItem, HttpServletRequest request);
	
	public int desarchivarCursos(List<CursoItem> listCursoItem, HttpServletRequest request);
	
	
}
