package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;

public interface IGestionFichaCompraSuscripcionService {
	
	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem peticion);

	public InsertResponseDTO solicitarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;
	
	public InsertResponseDTO solicitarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;
}