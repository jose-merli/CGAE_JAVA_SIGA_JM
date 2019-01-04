package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;

public interface IFichaColegialRegTelService {

	public BusquedaJuridicaDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception;
	
}
