package org.itcgae.siga.cen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaRetencionesDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionDTO;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.RetencionesDTO;

public interface ITarjetaDatosRetencionesService {

	public MaestroRetencionDTO getRetentionTypes(HttpServletRequest request);
	public RetencionesDTO getRetenciones(int numPagina,PersonaSearchDTO personaSerachDTO, HttpServletRequest request);
	public UpdateResponseDTO updateRetenciones(List<EtiquetaRetencionesDTO> etiquetaRetencionesDTO, String idPersona, HttpServletRequest request);
}
