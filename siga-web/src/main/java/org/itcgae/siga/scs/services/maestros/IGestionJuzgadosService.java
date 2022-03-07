package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoDTO;

public interface IGestionJuzgadosService {
	
	public ProcedimientoDTO searchProcess(HttpServletRequest request);
	
	public ProcedimientoDTO searchProcCourt(String idProc, String historico, HttpServletRequest request);
	
	public InsertResponseDTO createCourt(JuzgadoItem juzgadoItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateCourt(JuzgadoItem juzgadoItem, HttpServletRequest request);
	
	public UpdateResponseDTO associateProcess(ProcedimientoDTO procedimientoDTO, HttpServletRequest request);
	
	public UpdateResponseDTO asociarModulosAJuzgados(ProcedimientoDTO procedimientoDTO, HttpServletRequest request);

}
