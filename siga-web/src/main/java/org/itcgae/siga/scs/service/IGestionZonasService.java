package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ZonasDTO;
import org.itcgae.siga.DTO.scs.ZonasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IGestionZonasService {
	
	public ZonasDTO searchZones(ZonasItem zonasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteGroupZones(ZonasDTO zonasDTO, HttpServletRequest request);
	
	public UpdateResponseDTO activateGroupZones(ZonasDTO zonasDTO, HttpServletRequest request);


}
