package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolicitModifDatosBasicosDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public interface ISolicitudesModificacionEspecificaService {

	SolicitModifDatosBasicosDTO searchModificacionDatosBasicos(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);

}
