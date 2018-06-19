package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDeleteDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaPerJuridicaService {
	
	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboDTO getLabel( HttpServletRequest request);
	
	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
	public BusquedaJuridicaDTO searchHistoricLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
	public DeleteResponseDTO deleteNotCollegiate(BusquedaJuridicaDeleteDTO busquedaJuridicaDeleteDTO, HttpServletRequest request);
	
	public ComboDTO getLabelPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO,HttpServletRequest request);
}
