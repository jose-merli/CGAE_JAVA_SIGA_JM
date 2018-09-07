package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;

public interface IBusquedaNoColegiadosService {

	public NoColegiadoDTO searchNoColegiado( NoColegiadoItem noColegiadoItem, HttpServletRequest request);
	
}
