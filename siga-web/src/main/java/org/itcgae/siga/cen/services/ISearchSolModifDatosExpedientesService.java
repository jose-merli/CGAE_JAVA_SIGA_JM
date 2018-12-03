package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISearchSolModifDatosExpedientesService {

	SolModificacionDTO searchSolModifDatosExpedientes(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

	UpdateResponseDTO processSolModifDatosExpedientes(SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	UpdateResponseDTO denySolModifDatosExpedientes(SolModificacionItem solModificacionItem,
			HttpServletRequest request);

}
