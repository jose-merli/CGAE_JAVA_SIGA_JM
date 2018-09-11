package org.itcgae.siga.cen.services;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISolicitudIncorporacionService {
	
	
	public ComboDTO getTipoSolicitud(HttpServletRequest request);
	public ComboDTO getEstadoSolicitud(HttpServletRequest request);
}
