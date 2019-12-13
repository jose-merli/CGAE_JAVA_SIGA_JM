package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;

public interface IProcuradoresService {
	
	
	public InsertResponseDTO createProcurador(ProcuradorItem procuradorItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateProcurador(ProcuradorItem procuradorItem, HttpServletRequest request);
	
	public ProcuradorDTO searchProcuradores(ProcuradorItem procuradoresItem, HttpServletRequest request);

	public UpdateResponseDTO deleteProcuradores(ProcuradorDTO procuradorDTO, HttpServletRequest request);

	public UpdateResponseDTO activateProcuradores(ProcuradorDTO procuradorDTO, HttpServletRequest request);

}
