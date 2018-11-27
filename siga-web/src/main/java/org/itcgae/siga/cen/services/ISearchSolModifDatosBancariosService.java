package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISearchSolModifDatosBancariosService {

	SolModificacionDTO searchSolModifDatosBancarios(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

	UpdateResponseDTO processSolModifDatosBancarios(
			SolModificacionItem solModificacionItem, HttpServletRequest request);

	UpdateResponseDTO denySolModifDatosBancarios(SolModificacionItem solModificacionItem,
			HttpServletRequest request); 

}
