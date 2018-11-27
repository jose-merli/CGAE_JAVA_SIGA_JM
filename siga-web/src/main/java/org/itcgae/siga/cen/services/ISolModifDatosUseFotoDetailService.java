package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.StringDTO;

public interface ISolModifDatosUseFotoDetailService {

	StringDTO searchDatosUseFotoDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	StringDTO searchSolModifDatosUseFotoDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

}
