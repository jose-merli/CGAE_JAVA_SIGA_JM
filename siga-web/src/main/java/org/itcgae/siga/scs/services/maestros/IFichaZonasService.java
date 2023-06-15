package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ZonasDTO;
import org.itcgae.siga.DTOs.scs.ZonasItem;

public interface IFichaZonasService {
	
	public ComboDTO getPartidoJudicial(HttpServletRequest request);
	
	public ZonasDTO searchSubzones(String idZona,String idSubZona, HttpServletRequest request);
	
	public ZonasItem searchGroupZone(String idZona, HttpServletRequest request);
	
	public InsertResponseDTO createGroupZone(ZonasItem zonasItem, HttpServletRequest request);

	public UpdateResponseDTO updateGroupZone(ZonasItem zonaItem, HttpServletRequest request);
	
	public InsertResponseDTO createZone(ZonasItem zonasItem, HttpServletRequest request);

	public UpdateResponseDTO updateZones(ZonasDTO zonaItem, HttpServletRequest request);

	public UpdateResponseDTO deleteZones(ZonasDTO zonasDTO, HttpServletRequest request);

	

}
