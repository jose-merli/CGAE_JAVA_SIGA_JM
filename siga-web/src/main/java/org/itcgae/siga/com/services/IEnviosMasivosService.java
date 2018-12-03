package org.itcgae.siga.com.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IEnviosMasivosService {
	
	public ComboDTO estadoEnvios(HttpServletRequest request);
	public ComboDTO tipoEnvio(HttpServletRequest request);
	public EnviosMasivosDTO enviosMasivosSearch(HttpServletRequest request, EnviosMasivosSearch filtros);
	public Error programarEnvio (HttpServletRequest request, List<EnvioProgramadoDto> envioProgramarDto);
	public Error cancelarEnvios (HttpServletRequest request, EnvioProgramadoDto[] enviosProgramadosDto);
	public Error enviar(HttpServletRequest request, List<EnvioProgramadoDto> envios);
	public Error guardarConfiguracion(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);

}
