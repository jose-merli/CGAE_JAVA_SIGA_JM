package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.ComisariaDTO;
import org.itcgae.siga.DTOs.scs.ComisariaItem;

public interface IComisariasService {
	
	
	public InsertResponseDTO createComisaria(ComisariaItem comisariaItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateComisaria(ComisariaItem comisariaItem, HttpServletRequest request);
	
	public ComisariaDTO searchComisarias(ComisariaItem comisariaItem, HttpServletRequest request);

	public UpdateResponseDTO deleteComisarias(ComisariaDTO comisariaDTO, HttpServletRequest request);

	public UpdateResponseDTO activateComisarias(ComisariaDTO comisariaDTO, HttpServletRequest request);

}
