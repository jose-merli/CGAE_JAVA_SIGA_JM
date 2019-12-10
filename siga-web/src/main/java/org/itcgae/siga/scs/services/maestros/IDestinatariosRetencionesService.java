package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.AreasItem;
import org.itcgae.siga.DTOs.scs.DestinatariosDTO;
import org.itcgae.siga.DTOs.scs.DestinatariosItem;
import org.itcgae.siga.DTOs.scs.PartidasDTO;
import org.itcgae.siga.DTOs.scs.PartidasItem;

public interface IDestinatariosRetencionesService {
	
	public DestinatariosDTO searchDestinatario(DestinatariosItem destinatariosItem, HttpServletRequest request);

	public UpdateResponseDTO deleteDestinatariosRetenc(DestinatariosDTO destinatariosDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateDestinatariosRet(DestinatariosDTO destinatariosDTO, HttpServletRequest request);
	
	public InsertResponseDTO createDestinatarioRetenc(DestinatariosItem destinatariosItem, HttpServletRequest request);
}
