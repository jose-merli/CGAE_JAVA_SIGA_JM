package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AreasItem;
import org.itcgae.siga.DTOs.scs.PartidasDTO;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.DTOs.scs.ZonasItem;

public interface IPartidasJudicialesService {
	
	public PartidasJudicialesDTO searchPartida(PartidasJudicialesItem partidasJudicialesItem, HttpServletRequest request);

	public UpdateResponseDTO deletePartidasJudi(PartidasJudicialesDTO partidasJudicialesDTO, HttpServletRequest request);
	
	public ComboDTO getPartidoJudicial(HttpServletRequest request);
	
	public InsertResponseDTO createPartidoJudi(PartidasJudicialesItem partidasJudicialesItem, HttpServletRequest request);
	
//	public UpdateResponseDTO updatePartidasPres(PartidasDTO partidasDTO, HttpServletRequest request);
//	
//	public InsertResponseDTO createPartidaPres(PartidasItem partidasItem, HttpServletRequest request);
}
