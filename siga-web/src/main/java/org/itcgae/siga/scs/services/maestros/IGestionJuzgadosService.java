package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JuzgadoItem;
import org.itcgae.siga.DTO.scs.ProcedimientoDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IGestionJuzgadosService {
	
	public ProcedimientoDTO searchProcess(HttpServletRequest request);
	
	public ProcedimientoDTO searchProcCourt(String idProc, HttpServletRequest request);
	
	public InsertResponseDTO createCourt(JuzgadoItem juzgadoItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateCourt(JuzgadoItem juzgadoItem, HttpServletRequest request);
	
	public UpdateResponseDTO associateProcess(ProcedimientoDTO procedimientoDTO, HttpServletRequest request);
	

}
