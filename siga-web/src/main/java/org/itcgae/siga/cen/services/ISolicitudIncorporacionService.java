package org.itcgae.siga.cen.services;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISolicitudIncorporacionService {
	
	
	public ComboDTO getTipoSolicitud(HttpServletRequest request);
	public ComboDTO getEstadoSolicitud(HttpServletRequest request);
	public ComboDTO getTratamiento(HttpServletRequest request);
	public ComboDTO getEstadoCivil(HttpServletRequest request);
	public SolIncorporacionDTO datosSolicitudSearch (int numPagina, SolicitudIncorporacionSearchDTO datosSolicitudSearchDTO, HttpServletRequest request);
}
