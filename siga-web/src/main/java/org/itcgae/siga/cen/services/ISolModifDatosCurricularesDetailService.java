package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.SolModifDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;

public interface ISolModifDatosCurricularesDetailService {

	SolModifDatosCurricularesItem searchDatosCurricularesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	SolModifDatosCurricularesItem searchSolModifDatosCurricularesDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request);

}
