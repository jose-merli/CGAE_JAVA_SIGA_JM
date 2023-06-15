package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;

public interface IGestionFundamentoResolucionService {
	
	public UpdateResponseDTO updateFundamentoResolucion(FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request);

	public InsertResponseDTO createFundamentoResolucion(FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request);

	public ComboDTO getResoluciones(HttpServletRequest request);


}
