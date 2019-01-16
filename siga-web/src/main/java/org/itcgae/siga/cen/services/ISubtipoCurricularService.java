package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISubtipoCurricularService {
	public SubtipoCurricularDTO searchSubtipoCurricular(int numPagina, SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request); 
	public InsertResponseDTO createSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request) throws Exception;
	public UpdateResponseDTO updateSubtipoCurricular(SubtipoCurricularDTO subtipoCurricularDTO, HttpServletRequest request);
	public DeleteResponseDTO deleteSubtipoCurricular(SubtipoCurricularDTO subtipoCurricularDTO, HttpServletRequest request);
	public SubtipoCurricularDTO getHistory(SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request);
	public ComboDTO getComboSubtipoCurricular(int numPagina, SubtipoCurricularItem subtipoCurricularItem,
			HttpServletRequest request);
	
	public ComboDTO getCurricularSubtypeCombo(String idTipoCV, HttpServletRequest request);
}
