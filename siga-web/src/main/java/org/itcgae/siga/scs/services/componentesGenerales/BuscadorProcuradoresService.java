package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;

public interface BuscadorProcuradoresService {
	
	public ProcuradorDTO searchProcuradores(HttpServletRequest request, ProcuradorItem procuradorItem);

}
