package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ITipoCurricularService {
	public ComboDTO getComboCategoriaCurricular(HttpServletRequest request);
	public TipoCurricularDTO search(int numPagina, TipoCurricularItem tipoCurricularItem, HttpServletRequest request); 
	public InsertResponseDTO createTipoCurricular(TipoCurricularItem tipoCurricularItem, HttpServletRequest request) throws Exception;
}
