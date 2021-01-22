package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ComboColegiadoDTO;

public interface IFichaColegialOtrasColegiacionesService {
	ColegiadoDTO searchOtherCollegues(int numPagina, String nif, HttpServletRequest request);
	ComboColegiadoDTO getLabelColegios(String idPersona, HttpServletRequest request);
//	ColegiadoDTO searchOtherCollegesWS(int numPagina, String nif,
//			HttpServletRequest request);

}
