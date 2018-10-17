package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISolicitudModificacionService {
	public ComboDTO getComboTipoModificacion(HttpServletRequest request);
	public ComboDTO getComboEstado(HttpServletRequest request);
}
