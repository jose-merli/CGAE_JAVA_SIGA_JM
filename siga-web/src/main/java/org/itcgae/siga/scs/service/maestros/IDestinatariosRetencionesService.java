package org.itcgae.siga.scs.service.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.DTO.scs.DestinatariosDTO;
import org.itcgae.siga.DTO.scs.DestinatariosItem;
import org.itcgae.siga.DTO.scs.PartidasDTO;
import org.itcgae.siga.DTO.scs.PartidasItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IDestinatariosRetencionesService {
	
	public DestinatariosDTO searchDestinatario(DestinatariosItem destinatariosItem, HttpServletRequest request);

	public UpdateResponseDTO deleteDestinatariosRetenc(DestinatariosDTO destinatariosDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateDestinatariosRet(DestinatariosDTO destinatariosDTO, HttpServletRequest request);
	
	public InsertResponseDTO createDestinatarioRetenc(DestinatariosItem destinatariosItem, HttpServletRequest request);
}
