package org.itcgae.siga.scs.service.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.FundamentoResolucionDTO;
import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IBusquedaFundamentoResolucionService {
	
	public FundamentoResolucionDTO searchFundamentosResolucion(FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request);
	
	public UpdateResponseDTO deleteFundamentosResolucion(FundamentoResolucionDTO fundamentoResolucionDTO, HttpServletRequest request);

	public UpdateResponseDTO activateFundamentosResolucion(FundamentoResolucionDTO fundamentoResolucionDTO, HttpServletRequest request);

}
