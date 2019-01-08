package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;

public interface IBusquedaCensoGeneralService {
	

	
	public BusquedaPerFisicaDTO searchByNif(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request);
	
	public BusquedaPerFisicaDTO searchByName(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request);
	
}
