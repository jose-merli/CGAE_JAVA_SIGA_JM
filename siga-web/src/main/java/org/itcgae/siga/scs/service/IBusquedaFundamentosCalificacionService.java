package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.FundamentosCalificacionDTO;
import org.itcgae.siga.DTO.scs.FundamentosCalificacionItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;


public interface IBusquedaFundamentosCalificacionService {

	public ComboDTO comboDictamen(HttpServletRequest request);
	public FundamentosCalificacionDTO searchFundamentos(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request);
	public UpdateResponseDTO deleteFundamentos(FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request);
	public UpdateResponseDTO activateFundamentos(FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request);
	public InsertResponseDTO insertFundamentos(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request);
	public UpdateResponseDTO updateFundamentosCalificacion(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request);
	
}
