package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IComunicacionesService {

	/**Combos**/
	public ComboDTO estadoEnvios(HttpServletRequest request);
	public ComboDTO tipoEnvio(HttpServletRequest request);
	public ComboDTO claseComunicacion(HttpServletRequest request);
	
	/****/
	public EnviosMasivosDTO comunicacionesSearch(HttpServletRequest request, EnviosMasivosSearch filtros);
	public Error programarEnvio (HttpServletRequest request, EnviosMasivosItem[] comunicacionesProgramarDto);
	public Error cancelarEnvios (HttpServletRequest request, EnvioProgramadoDto[] comunicacionesProgramadosDto);
	public DocumentosEnvioDTO obtenerDocumentosEnvio(HttpServletRequest request, String idEnvio);

	public Error reenviar(HttpServletRequest request, EnviosMasivosItem[] envio);
	public EnviosMasivosDTO detalleConfiguracion(HttpServletRequest request,String idEnvio);
	public EnviosMasivosDTO detalleDestinatarios(HttpServletRequest request,String idEnvio);
	
}
