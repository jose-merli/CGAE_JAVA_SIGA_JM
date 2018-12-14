package org.itcgae.siga.form.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaCursosService {
	
	public static final Short DESIGNAR_TUTOR = 0; 
	
	public static final Short ASIGNAR_TUTOR = 1; 
	
	public static final long ANUNCIADO_CURSO = 1; 
	
	public static final long ABIERTO_CURSO = 0; 
	
	public void updateEstadoCursoAuto();
	
	public FormadorCursoDTO getTrainersCourse(String idCurso, HttpServletRequest request);
	
	public ComboDTO getRolesTrainers(HttpServletRequest request);
	
	public ComboDTO getTypeCostTrainers(HttpServletRequest request);

	public UpdateResponseDTO updateTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);

	public InsertResponseDTO saveTrainersCourse(FormadorCursoItem formadorCursoItem, HttpServletRequest request);

	public UpdateResponseDTO deleteTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);
	
	public InsertResponseDTO saveCourse(CursoItem cursoItem, HttpServletRequest request);

	public UpdateResponseDTO updateCourse(CursoItem cursoItem, HttpServletRequest request);
	
	public UpdateResponseDTO releaseOrAnnounceCourse(CursoDTO cursoDTO , HttpServletRequest request);

	public CursoItem searchCourse(String idCurso, HttpServletRequest request);
	
	public EventoDTO getSessionsCourse(CursoItem cursoItem, HttpServletRequest request);


}
