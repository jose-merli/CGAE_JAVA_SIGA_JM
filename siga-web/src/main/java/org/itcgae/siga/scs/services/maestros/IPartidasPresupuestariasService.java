package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IPartidasPresupuestariasService {
	
	public PartidasDTO searchPartida(PartidasItem partidasItem, HttpServletRequest request);

	public UpdateResponseDTO deletePartidasPres(PartidasDTO partidasDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updatePartidasPres(PartidasDTO partidasDTO, HttpServletRequest request);
	
	public InsertResponseDTO createPartidaPres(PartidasItem partidasItem, HttpServletRequest request);
}
