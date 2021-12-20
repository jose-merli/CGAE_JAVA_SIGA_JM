package org.itcgae.siga.cen.services;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaRetencionesDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionDTO;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.RetencionesDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITarjetaDatosRetencionesService {

    public MaestroRetencionDTO getRetentionTypes(HttpServletRequest request);

    public RetencionesDTO getRetenciones(int numPagina, PersonaSearchDTO personaSerachDTO, HttpServletRequest request);

    public UpdateResponseDTO updateRetenciones(List<EtiquetaRetencionesDTO> etiquetaRetencionesDTO, String idPersona, HttpServletRequest request);

    /*RetencionesDTO getRetencionesColegial(int numPagina, PersonaSearchDTO personaSearchDTO, HttpServletRequest request);

    RetencionesDTO selectRetencionesColegialYSociedades(int numPagina, PersonaSearchDTO personaSearchDTO, HttpServletRequest request);*/

	public RetencionesDTO getLiquidacionSJCS(PersonaSearchDTO personaSearchDTO, HttpServletRequest request);

}
