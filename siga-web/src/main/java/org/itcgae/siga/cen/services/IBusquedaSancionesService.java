package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaSancionesService {

	ComboDTO getComboTipoSancion(HttpServletRequest request);
	
	BusquedaSancionesDTO searchBusquedaSanciones(int numPagina, BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request);

	BusquedaSancionesDTO searchBusquedaSancionesBBDD(int numPagina, BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, HttpServletRequest request);

	InsertResponseDTO insertSanction(BusquedaSancionesItem busquedaSancionesItem, HttpServletRequest request);

	UpdateResponseDTO updateSanction(BusquedaSancionesItem busquedaSancionesItem, HttpServletRequest request);
	
	void archivarSancionesRehabilitado();

}
