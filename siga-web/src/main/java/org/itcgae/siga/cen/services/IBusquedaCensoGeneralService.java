package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;

public interface IBusquedaCensoGeneralService {
	

	
	public BusquedaPerFisicaDTO search(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request);
	
	public BusquedaPerFisicaDTO searchExact(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request);

	public NoColegiadoDTO searchCliente( NoColegiadoItem noColegiadoItem, HttpServletRequest request);

	public ColegiadoDTO searchColegiado( ColegiadoItem colegiadoItem, HttpServletRequest request);

}
