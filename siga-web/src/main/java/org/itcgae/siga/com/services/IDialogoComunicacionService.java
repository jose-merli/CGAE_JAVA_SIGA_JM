package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IDialogoComunicacionService {
	
	public ComboDTO obtenerClaseComunicaciones(HttpServletRequest request, String rutaClaseComunicacion);
	public ModelosComunicacionSearch obtenerModelos(HttpServletRequest request, String[] idClaseComunicacion);
	public ComboDTO obtenertipoEnvioPlantilla(HttpServletRequest request, String idPlantilla);
	public ComboDTO descargarComunicacion(HttpServletRequest request, DialogoComunicacionItem dialogo);
	
}
