package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltrosSuscripcionesItem;
import org.itcgae.siga.DTO.fac.ListaSuscripcionesDTO;
import org.itcgae.siga.DTO.fac.RevisionAutLetradoItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;

public interface ISuscripcionServiciosService {

	public ListaSuscripcionesDTO getListaSuscripciones(HttpServletRequest request, FiltrosSuscripcionesItem peticion);
	
	public void ejecutaSuscripcionesAutomaticas();

	public void actualizacionSuscripcionesPersona(HttpServletRequest request, RevisionAutLetradoItem peticion);

	void ejecutaRevisionAutomatica();

	InsertResponseDTO actualizacionColaSuscripcionesPersona(HttpServletRequest request, RevisionAutLetradoItem peticion);

}
