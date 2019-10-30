package org.itcgae.siga.scs.service.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ProcuradorDTO;
import org.itcgae.siga.DTO.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IProcuradoresService {
	
	
	public InsertResponseDTO createProcurador(ProcuradorItem procuradorItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateProcurador(ProcuradorItem procuradorItem, HttpServletRequest request);
	
	public ProcuradorDTO searchProcuradores(ProcuradorItem procuradoresItem, HttpServletRequest request);

	public UpdateResponseDTO deleteProcuradores(ProcuradorDTO procuradorDTO, HttpServletRequest request);

	public UpdateResponseDTO activateProcuradores(ProcuradorDTO procuradorDTO, HttpServletRequest request);

}
