package org.itcgae.siga.cen.services.impl;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;

public interface IFichaColegialOtrasColegiacionesService {

	public ColegiadoDTO searchOtherCollegues(int numPagina, String idPersona, HttpServletRequest request);

}