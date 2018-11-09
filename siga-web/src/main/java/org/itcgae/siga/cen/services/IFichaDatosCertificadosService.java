package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.CertificadoDTO;
import org.itcgae.siga.DTOs.cen.CertificadoItem;

public interface IFichaDatosCertificadosService {

//	public CertificadoDTO datosCertificadosSearch(int numPagina, CertificadoItem certificadoItem, HttpServletRequest request);

	public CertificadoDTO datosCertificadosSearch(int numPagina, String idPersona, HttpServletRequest request);
	
//	public ComboDTO getSocietyTypes(HttpServletRequest request);
//	
//	public ComboDTO getTratamiento(HttpServletRequest request);
//
//	public ComboDTO getLabel( HttpServletRequest request);
//	
//	public ComboDTO getTypeInsurances(HttpServletRequest request);
//
//	public ColegiadoDTO datosColegialesSearch(int numPagina, ColegiadoItem colegiadoItem,
//			HttpServletRequest request);

	
//	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
}
