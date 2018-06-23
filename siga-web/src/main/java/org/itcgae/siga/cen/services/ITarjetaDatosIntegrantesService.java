package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;

public interface ITarjetaDatosIntegrantesService {



	DatosIntegrantesDTO searchIntegrantesData(int numPagina, DatosIntegrantesSearchDTO datosIntegrantesSearchDTO,	HttpServletRequest request);
	
	
	
	
}


