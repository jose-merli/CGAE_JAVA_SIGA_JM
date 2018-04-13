package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.ParametroDTO;
import org.itcgae.siga.DTOs.adm.ParametroDeleteDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IParametrosService {

	public ComboDTO getParameterModules();
	
	public ParametroDTO getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO, HttpServletRequest request);
	
	public ParametroDTO getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateParameter(ParametroUpdateDTO parametroUpdateDTO, HttpServletRequest request);
	
	public UpdateResponseDTO deleteParameter(ParametroDeleteDTO parametroDeleteDTO, HttpServletRequest request);
	
}
