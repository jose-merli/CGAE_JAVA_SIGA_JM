package org.itcgae.siga.cen.services;

import org.itcgae.siga.DTOs.cen.AlterMutuaResponseDTO;
import org.itcgae.siga.DTOs.cen.EstadoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.PropuestasDTO;
import org.itcgae.siga.DTOs.cen.SolicitudDTO;


public interface IAlterMutuaService {
	
	public AlterMutuaResponseDTO getEstadoSolicitud (EstadoSolicitudDTO estadosolicitudDTO);
	public AlterMutuaResponseDTO getEstadoColegiado (EstadoColegiadoDTO estadoColegiadoDTO);
	public AlterMutuaResponseDTO getPropuestas (PropuestasDTO PropuestasDTO);
	public AlterMutuaResponseDTO getTarifaSolicitud(SolicitudDTO solicitud);
	public AlterMutuaResponseDTO setSolicitudAlter(SolicitudDTO solicitud);
	

}
