package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.DTOs.cen.StringDTO;

public interface ISolModifDatosDireccionesDetailService {

	SoliModiDireccionesItem searchSolModifDatosDireccionesDetail(int numPagina, StringDTO idSolicitud,
			HttpServletRequest request);

	DatosDireccionesDTO searchDatosDirecciones(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request);

}
