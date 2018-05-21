package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaPerJuridicaService {
	
	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboDTO getLabel( HttpServletRequest request);
	
	//public BusquedaJuridicaDto searchLegalPersons(int numPagina, BusquedaJuridicaSearchDto busquedaJuridicaSearchDto, HttpServletRequest request);
}
