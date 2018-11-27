package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;

public interface ISolModifDatosBancariosDetailService {

	SolModifDatosBancariosItem searchDatosBancariosDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	SolModifDatosBancariosItem searchSolModifDatosBancariosDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request);

}
