package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.ZonasDTO;
import org.itcgae.siga.DTOs.scs.ZonasItem;

public interface IGestionZonasService {
	
	public ZonasDTO searchZones(ZonasItem zonasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteGroupZones(ZonasDTO zonasDTO, HttpServletRequest request);
	
	public UpdateResponseDTO activateGroupZones(ZonasDTO zonasDTO, HttpServletRequest request);


}
