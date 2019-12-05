package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;

public interface IBusquedaFundamentoResolucionService {
	
	public FundamentoResolucionDTO searchFundamentosResolucion(FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request);
	
	public UpdateResponseDTO deleteFundamentosResolucion(FundamentoResolucionDTO fundamentoResolucionDTO, HttpServletRequest request);

	public UpdateResponseDTO activateFundamentosResolucion(FundamentoResolucionDTO fundamentoResolucionDTO, HttpServletRequest request);

}
