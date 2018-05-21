package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.controllers.BusquedaJuridicaDto;
import org.itcgae.siga.cen.controllers.BusquedaJuridicaSearchDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IBusquedaPerJuridicaService {
	
	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboDTO getLabel( HttpServletRequest request);
	
	public BusquedaJuridicaDto searchLegalPersons(int numPagina, BusquedaJuridicaSearchDto busquedaJuridicaSearchDto, HttpServletRequest request);
}
