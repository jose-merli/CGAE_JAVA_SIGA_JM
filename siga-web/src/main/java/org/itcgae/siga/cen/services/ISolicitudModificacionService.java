package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISolicitudModificacionService {
	public ComboDTO getComboTipoModificacion(HttpServletRequest request);
	public ComboDTO getComboEstado(HttpServletRequest request);
	public SolModificacionDTO searchModificationRequest(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request); 
}
