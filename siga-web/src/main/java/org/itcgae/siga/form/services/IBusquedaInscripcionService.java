package org.itcgae.siga.form.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.form.InscripcionDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaInscripcionService {

	public ComboDTO getEstadosInscripcion(HttpServletRequest request);
	
	public InscripcionDTO searchInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request);
	
	public ComboDTO getCalificacionesEmitidas(HttpServletRequest request);	
	
	public Object updateEstado(List<InscripcionItem> listInscripcionItem, HttpServletRequest request);
	
	public int updateCalificacion(InscripcionItem inscripcionItem, HttpServletRequest request);
	
}
