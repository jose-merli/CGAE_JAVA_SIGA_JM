package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JuzgadoItem;
import org.itcgae.siga.DTO.scs.ProcedimientoDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IGestionJuzgadosService {
	
	public ProcedimientoDTO searchProcess(HttpServletRequest request);
	
	public ProcedimientoDTO searchProcJudged(String idProc, HttpServletRequest request);
	
	public InsertResponseDTO createJudged(JuzgadoItem juzgadoItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateJudged(JuzgadoItem juzgadoItem, HttpServletRequest request);
	
	public UpdateResponseDTO associateProcess(ProcedimientoDTO procedimientoDTO, HttpServletRequest request);
	

}
