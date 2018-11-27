package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.StringDTO;

public interface ISolModifDatosGeneralesDetailService {

	StringDTO searchDatosGeneralesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

	StringDTO searchSolModifDatosGeneralesDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

}