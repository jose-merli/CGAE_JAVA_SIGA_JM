package org.itcgae.siga.age.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.form.FormadorCursoDTO;

public interface IFichaEventosService {
	
	public FormadorCursoDTO getTrainers(String idCurso, HttpServletRequest request);
	
}
