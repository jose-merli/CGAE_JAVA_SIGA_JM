package org.itcgae.siga.cen.services;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ComboColegiadoDTO;

public interface IFichaColegialOtrasColegiacionesService {
	ColegiadoDTO searchOtherCollegues(int numPagina, String idPersona, HttpServletRequest request);
	ComboColegiadoDTO getLabelColegios(String idPersona, HttpServletRequest request);

}
