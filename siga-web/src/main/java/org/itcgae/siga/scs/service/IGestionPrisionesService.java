package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.PrisionItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IGestionPrisionesService {
	
	
	public InsertResponseDTO createPrision(PrisionItem prisionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updatePrision(PrisionItem prisionItem, HttpServletRequest request);
	
	

}
