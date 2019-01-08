package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;

public interface IBusquedaCensoGeneralService {
	

	
	public BusquedaPerFisicaDTO search(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request);
	
}
