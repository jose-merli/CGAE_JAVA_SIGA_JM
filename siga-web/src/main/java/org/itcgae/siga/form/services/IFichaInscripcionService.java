package org.itcgae.siga.form.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaInscripcionService {

	public CursoItem searchCourse(String idCurso, HttpServletRequest request);
	
	public ComboDTO saveInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request);
	
	public InsertResponseDTO guardarPersona(AsociarPersonaDTO asociarPersona, HttpServletRequest request);
	
	public InsertResponseDTO generarSolicitudCertificados(InscripcionItem inscripcionItem, HttpServletRequest request);
	
	public String compruebaMinimaAsistencia(InscripcionItem inscripcionItem, HttpServletRequest request);
	
	public ComboDTO getPaymentMode(HttpServletRequest request);
	
}
