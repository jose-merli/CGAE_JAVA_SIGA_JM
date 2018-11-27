package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModifDatosBasicosItem;

public interface ISolModifDatosGeneralesDetailService {

	SoliModifDatosBasicosItem searchDatosGeneralesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	SoliModifDatosBasicosItem searchSolModifDatosGeneralesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

}