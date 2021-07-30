package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;

public interface IServiciosService {
	
	public ListaServiciosDTO searchListadoServicios(HttpServletRequest request, FiltroServicioItem filtroServicioItem);

}
