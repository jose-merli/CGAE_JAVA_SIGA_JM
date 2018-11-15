package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaSancionesService {

	ComboDTO getComboTipoSancion(HttpServletRequest request);
	
	BusquedaSancionesDTO searchBusquedaSanciones(int numPagina, BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request); 

}
