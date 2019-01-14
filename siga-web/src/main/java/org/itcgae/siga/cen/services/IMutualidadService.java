package org.itcgae.siga.cen.services;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.CuotaYCapObjetivoDTO;
import org.itcgae.siga.DTOs.cen.CuotaYCapitalObjetivoResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosSolicitudGratuitaDTO;
import org.itcgae.siga.DTOs.cen.EstadoMutualistaDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.MutualidadCombosDTO;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;

public interface IMutualidadService {
	
	public MutualidadResponseDTO getEstadoSolicitud(EstadoSolicitudDTO estadoSolicitud);
	public MutualidadResponseDTO getEstadoMutualista(EstadoMutualistaDTO estadoMutualistaDTO);
	public MutualidadCombosDTO getEnums();
	public MutualidadResponseDTO MGASolicitudPolizaAccuGratuitos(DatosSolicitudGratuitaDTO datosSolicitud, HttpServletRequest request);
	public MutualidadResponseDTO MGASolicitudPolizaProfesional(DatosSolicitudGratuitaDTO datosSolicitud, HttpServletRequest request);
	public CuotaYCapitalObjetivoResponseDTO ObtenerCuotaYCapObjetivo(CuotaYCapObjetivoDTO datosCuota);
	
}
