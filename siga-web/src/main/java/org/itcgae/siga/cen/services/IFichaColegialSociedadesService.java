package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;

public interface IFichaColegialSociedadesService {

	public BusquedaJuridicaDTO searchSocieties(int numPagina, String idPersona, HttpServletRequest request);
}
