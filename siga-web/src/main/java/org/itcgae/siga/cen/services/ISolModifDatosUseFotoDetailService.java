package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModifFotoItem;

public interface ISolModifDatosUseFotoDetailService {

	SoliModifFotoItem searchDatosUseFotoDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	SoliModifFotoItem searchSolModifDatosUseFotoDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

}
