package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTO.scs.ColegiadosSJCSItem;

public interface BusquedaGeneralSJCSService {
	
	public ColegiadosSJCSDTO searchColegiadosSJCS(HttpServletRequest request, ColegiadosSJCSItem colegiadosSJCSItem);

}
