package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;

public interface IBusquedaNoColegiadosService {

	public NoColegiadoDTO searchNoColegiado( NoColegiadoItem noColegiadoItem, HttpServletRequest request);
	
	public NoColegiadoDTO searchHistoricNoColegiado(int numPagina, NoColegiadoItem noColegiadoItem, HttpServletRequest request);

	public DeleteResponseDTO deleteNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request);

}
