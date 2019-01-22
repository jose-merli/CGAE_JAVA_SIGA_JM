package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISearchSolModifDatosGeneralesService {

	SolModificacionDTO searchSolModifDatosGenerales(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

	UpdateResponseDTO processSolModifDatosGenerales(SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	UpdateResponseDTO denySolModifDatosGenerales(SolModificacionItem solModificacionItem,
			HttpServletRequest request); 

}
