package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISearchSolModifService {

	SolModificacionDTO searchSolModif(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			HttpServletRequest request);

	UpdateResponseDTO processSolModif(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	UpdateResponseDTO denySolModif(int numPagina, SolModificacionItem solModificacionItem, HttpServletRequest request);

}
