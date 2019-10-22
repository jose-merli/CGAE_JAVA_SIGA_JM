package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.PretensionDTO;
import org.itcgae.siga.DTO.scs.PretensionItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IPretensionesService {

	public InsertResponseDTO createPretension(PretensionItem pretensionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updatePretension(PretensionDTO pretensionDTO, HttpServletRequest request);
	
	public PretensionDTO searchPretensiones(PretensionItem pretensionItem, HttpServletRequest request);

	public UpdateResponseDTO deletePretensiones(PretensionDTO pretensionDTO, HttpServletRequest request);

	public UpdateResponseDTO activatePretensiones(PretensionDTO pretensionDTO, HttpServletRequest request);

	public ComboDTO getProcedimientos(HttpServletRequest request);
	}
