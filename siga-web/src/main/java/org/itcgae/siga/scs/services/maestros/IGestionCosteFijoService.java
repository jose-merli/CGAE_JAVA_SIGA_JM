package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoItem;

public interface IGestionCosteFijoService {
	
	public CosteFijoDTO searchCostesFijos(boolean historico, HttpServletRequest request);

	public ComboDTO getComboCosteFijos(HttpServletRequest request);
	
	public ComboDTO getComboActuacion(String idTipoAsistencia, HttpServletRequest request);
	
	public ComboDTO getComboAsistencia(HttpServletRequest request);

	public InsertResponseDTO createCosteFijo(CosteFijoItem costeFijoItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateCosteFijo(CosteFijoDTO costeFijoDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteCosteFijo(CosteFijoDTO costeFijoDTO, HttpServletRequest request);

	public UpdateResponseDTO activateCosteFijo(CosteFijoDTO costeFijoDTO, HttpServletRequest request);

	
}
