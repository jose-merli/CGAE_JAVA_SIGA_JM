package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISearchSolModifDatosUseFotoService {

	SolModificacionDTO searchSolModifDatosUseFoto(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

	UpdateResponseDTO processSolModifDatosUseFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request);
	
	UpdateResponseDTO denySolModifDatosUseFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request);
	
}
