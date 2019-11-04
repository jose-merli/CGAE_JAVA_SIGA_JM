package org.itcgae.siga.scs.service.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ProcuradorDTO;
import org.itcgae.siga.DTO.scs.ProcuradorItem;

public interface BuscadorProcuradoresService {
	
	public ProcuradorDTO searchProcuradores(HttpServletRequest request, ProcuradorItem procuradorItem);

}
