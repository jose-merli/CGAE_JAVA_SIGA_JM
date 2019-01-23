package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IDialogoComunicacionService {
	
	public ComboDTO obtenerClaseComunicaciones(HttpServletRequest request, String rutaClaseComunicacion);

}
