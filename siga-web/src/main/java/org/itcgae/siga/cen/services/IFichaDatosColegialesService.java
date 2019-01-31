package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaDatosColegialesService {
	
	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboDTO getTratamiento(HttpServletRequest request);

	public ComboDTO getLabel( HttpServletRequest request);
	
	public ComboDTO getTypeInsurances(HttpServletRequest request);

	public ColegiadoDTO datosColegialesSearch(int numPagina, ColegiadoItem colegiadoItem,
			HttpServletRequest request);

	public UpdateResponseDTO datosColegialesUpdate(ColegiadoItem colegiadoItem,
			HttpServletRequest request);

	
//	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
}