package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboDictamenDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionItem;


public interface IBusquedaFundamentosCalificacionService {

	public ComboDictamenDTO comboDictamen(HttpServletRequest request);
	public FundamentosCalificacionDTO searchFundamentos(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request);
	public UpdateResponseDTO deleteFundamentos(FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request);
	public UpdateResponseDTO activateFundamentos(FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request);
	public InsertResponseDTO insertFundamentos(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request);
	public UpdateResponseDTO updateFundamentosCalificacion(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request);
	
}
