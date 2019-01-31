package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISearchSolModifDatosCurricularesService {

	SolModificacionDTO searchSolModifDatosCurriculares(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

	UpdateResponseDTO denySolModifDatosCurriculares(SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	UpdateResponseDTO processSolModifDatosCurriculares(
			SolModifDatosCurricularesItem solModifDatosCurricularesItem, HttpServletRequest request); 

}