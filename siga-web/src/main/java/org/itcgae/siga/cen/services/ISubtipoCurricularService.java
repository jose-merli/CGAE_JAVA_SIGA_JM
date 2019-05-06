package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ComboSubtiposCVDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;

public interface ISubtipoCurricularService {
	public SubtipoCurricularDTO searchSubtipoCurricular(int numPagina, SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request); 
	public InsertResponseDTO createSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request) throws Exception;
	public UpdateResponseDTO updateSubtipoCurricular(SubtipoCurricularDTO subtipoCurricularDTO, HttpServletRequest request);
	public DeleteResponseDTO deleteSubtipoCurricular(SubtipoCurricularDTO subtipoCurricularDTO, HttpServletRequest request);
	public SubtipoCurricularDTO getHistory(SubtipoCurricularItem subtipoCurricularItem, HttpServletRequest request);

	public ComboSubtiposCVDTO getCurricularSubtypeCombo(String idTipoCV, boolean historico, HttpServletRequest request);
}
