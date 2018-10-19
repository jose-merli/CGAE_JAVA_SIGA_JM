package org.itcgae.siga.cen.services;


import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;

public interface IMutualidadService {
	
	public MutualidadResponseDTO getEstadoSolicitud();
	public MutualidadResponseDTO getEstadoMutualista();
	public MutualidadResponseDTO getEnums();
	public MutualidadResponseDTO MGASolicitudPolizaAccuGratuitos();
	public MutualidadResponseDTO MGASolicitudPolizaProfesional();
	public MutualidadResponseDTO ObtenerCuotaYCapObjetivo();
	
}
