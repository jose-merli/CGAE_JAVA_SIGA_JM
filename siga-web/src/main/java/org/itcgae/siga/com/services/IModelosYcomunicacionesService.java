package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IModelosYcomunicacionesService {
	
	/**Combos**/
	public ComboDTO claseComunicacion(HttpServletRequest request);
	public ComboDTO tipoEnvio(HttpServletRequest request);
	
	
	/****/
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros);
	public DatosModelosComunicacionesDTO modeloYComunicacionesHistoricoSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros);
	public Error duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion);
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion);

	
}
