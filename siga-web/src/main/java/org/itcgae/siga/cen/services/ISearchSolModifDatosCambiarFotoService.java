package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;

public interface ISearchSolModifDatosCambiarFotoService {

	SolModificacionDTO searchSolModifDatosCambiarFoto(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

	ComboItem searchSolModifDatosCambiarFotoDetail(SolModificacionItem solModificacionItem,
			HttpServletRequest request,  HttpServletResponse response);

	UpdateResponseDTO processSolModifDatosCambiarFoto(SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	UpdateResponseDTO denySolModifDatosCambiarFoto(SolModificacionItem solModificacionItem, HttpServletRequest request);

//	UpdateResponseDTO processSolModifDatosUseFoto(SolModificacionItem solModificacionItem,
//			HttpServletRequest request);
//	
//	UpdateResponseDTO denySolModifDatosUseFoto(SolModificacionItem solModificacionItem,
//			HttpServletRequest request);
	
}
