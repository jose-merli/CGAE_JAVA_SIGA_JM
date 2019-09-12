package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ZonasDTO;
import org.itcgae.siga.DTO.scs.ZonasItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaZonasService {
	
	public ComboDTO getPartidoJudicial(HttpServletRequest request);
	
	public ZonasDTO searchSubzones(String idZona, HttpServletRequest request);
	
	public ZonasItem searchGroupZone(String idZona, HttpServletRequest request);
	
	public InsertResponseDTO createGroupZone(ZonasItem zonasItem, HttpServletRequest request);

	public UpdateResponseDTO updateGroupZone(ZonasItem zonaItem, HttpServletRequest request);
	
	public InsertResponseDTO createZone(ZonasItem zonasItem, HttpServletRequest request);

	public UpdateResponseDTO updateZones(ZonasDTO zonaItem, HttpServletRequest request);

	public UpdateResponseDTO deleteZones(ZonasDTO zonasDTO, HttpServletRequest request);

	

}
