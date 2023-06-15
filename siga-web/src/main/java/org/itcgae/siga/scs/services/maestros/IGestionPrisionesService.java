package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.PrisionItem;

public interface IGestionPrisionesService {
	
	
	public InsertResponseDTO createPrision(PrisionItem prisionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updatePrision(PrisionItem prisionItem, HttpServletRequest request);
	
	

}
