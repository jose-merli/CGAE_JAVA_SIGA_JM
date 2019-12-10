package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;

public interface BusquedaGeneralSJCSService {
	
	public ColegiadosSJCSDTO searchColegiadosSJCS(HttpServletRequest request, ColegiadosSJCSItem colegiadosSJCSItem);

}
