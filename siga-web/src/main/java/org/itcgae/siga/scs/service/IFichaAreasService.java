package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AreasDTO;
import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaAreasService {
	
	public ComboDTO getPartidoJudicial(HttpServletRequest request);

	public AreasDTO searchAreas(AreasItem areasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteAreas(AreasDTO areasDTO, HttpServletRequest request);

}
