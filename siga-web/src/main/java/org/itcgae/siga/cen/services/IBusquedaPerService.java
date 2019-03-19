package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoGeneralDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaPerService {
	
	public ComboDTO getLabelColegios( HttpServletRequest request);
	
	public BusquedaPerJuridicaDTO searchPerJuridica(int numPagina, BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO, HttpServletRequest request);
	
	public BusquedaPerFisicaDTO searchPerFisica(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, HttpServletRequest request);
	
	public ColegiadoGeneralDTO searchPerByIdPersona(String persona, HttpServletRequest request);

	public ColegiadoGeneralDTO searchPerByIdPersonaIdInstitucion(String idPersona, String idInstitucionPersona, HttpServletRequest request);	
	
}
