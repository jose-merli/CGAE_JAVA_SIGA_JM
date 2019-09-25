package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionFundamentoResolucionService {
	
	public UpdateResponseDTO updateFundamentoResolucion(FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request);

	public InsertResponseDTO createFundamentoResolucion(FundamentoResolucionItem fundamentoResolucionItem, HttpServletRequest request);

	public ComboDTO getResoluciones(HttpServletRequest request);


}
