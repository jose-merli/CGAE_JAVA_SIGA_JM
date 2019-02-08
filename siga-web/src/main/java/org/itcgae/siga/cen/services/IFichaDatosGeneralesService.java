package org.itcgae.siga.cen.services;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IFichaDatosGeneralesService {
	
//	public ComboDTO getSocietyTypes(HttpServletRequest request);
	
	public ComboDTO getTratamiento(HttpServletRequest request);

	public ComboDTO getEstadoCivil(HttpServletRequest request);

//	public ComboDTO getLabel( HttpServletRequest request);

	public UpdateResponseDTO updateColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException;

	public InsertResponseDTO createNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request) throws ParseException;

	public DatosDireccionesDTO partidoJudicialSearch(ColegiadoItem colegiadoItem, HttpServletRequest request);

	public ComboEtiquetasDTO getLabelPerson(ColegiadoItem colegiadoItem,
			HttpServletRequest request) throws ParseException;

	public InsertResponseDTO solicitudModificacion(NoColegiadoItem noColegiadoItem, HttpServletRequest request);

	public InsertResponseDTO solicitudUploadPhotography(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException;

//	DatosDireccionesItem partidoJudicialSearch(ColegiadoItem colegiadoItem, HttpServletRequest request);
//	public ColegiadoItem partidoJudicialSearch(ColegiadoItem colegiadoItem, HttpServletRequest request);
//	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, HttpServletRequest request);
	
	public ComboDTO getTopicsSpecificPerson(HttpServletRequest request, String idPersona);
	
}
