package org.itcgae.siga.cen.services;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDeleteDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ParametroColegioDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaPerJuridicaService {
	
	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboInstitucionDTO getLabel( HttpServletRequest request);
	
	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
	public BusquedaJuridicaDTO searchHistoricLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
	public DeleteResponseDTO deleteNotCollegiate(BusquedaJuridicaDeleteDTO busquedaJuridicaDeleteDTO, HttpServletRequest request);
	
	public ComboEtiquetasDTO getLabelPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO,HttpServletRequest request) throws ParseException;
		
	public ParametroColegioDTO searchParametroColegio(StringDTO stringDTO, HttpServletRequest request);
	
	public ParametroColegioDTO searchProperty(StringDTO stringDTO, HttpServletRequest request);
}
