package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;

public interface ITarjetaDatosDireccionesService {

	DatosDireccionesDTO datosDireccionesSearch(int numPagina, DatosDireccionesSearchDTO datosDireccionesSearchDTO,	HttpServletRequest request);

	



	
	
}


