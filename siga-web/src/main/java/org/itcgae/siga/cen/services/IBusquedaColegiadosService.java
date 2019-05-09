package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaColegiadosService {

	public ComboDTO getCivilStatus(HttpServletRequest request);
	
	public ComboDTO getSituacion(HttpServletRequest request);
	
	public ComboDTO getCVCategory(HttpServletRequest request);
	
	public ColegiadoDTO searchColegiado( ColegiadoItem colegiadoItem, HttpServletRequest request);
	
	public ComboInstitucionDTO getLabel(HttpServletRequest request);

	
}
