package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaDireccionesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;

public interface ITarjetaDatosDireccionesService {

	DatosDireccionesDTO datosDireccionesSearch(int numPagina, DatosDireccionesSearchDTO datosDireccionesSearchDTO,	HttpServletRequest request);

	UpdateResponseDTO deleteDireccion(TarjetaDireccionesUpdateDTO[] tarjetaDireccionesUpdateDTO, HttpServletRequest request);

	ComboDTO getPais(HttpServletRequest request);

    ComboDTO getPoblacion(HttpServletRequest request, String idProvincia, String filtro);
    
    ComboItem getPoblacion(HttpServletRequest request, String idPoblacion);

	ComboDTO getTipoDireccion(HttpServletRequest request);

	UpdateResponseDTO updateDirection(DatosDireccionesItem datosDirecciones, HttpServletRequest request);

	InsertResponseDTO createDirection(DatosDireccionesItem datosDirecciones, HttpServletRequest request);
	
	InsertResponseDTO duplicateDirection(DatosDireccionesItem datosDirecciones, HttpServletRequest request);

	UpdateResponseDTO solicitudUpdateDirection(DatosDireccionesItem datosDirecciones, HttpServletRequest request);

	InsertResponseDTO solicitudCreateDirection(DatosDireccionesItem datosDirecciones, HttpServletRequest request);

	
}

