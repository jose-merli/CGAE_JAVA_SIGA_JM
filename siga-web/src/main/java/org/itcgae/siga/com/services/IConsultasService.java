package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IConsultasService {

	/** Combos **/
	public ComboDTO modulo(HttpServletRequest request);

	public ComboDTO objetivo(HttpServletRequest request);

	public ComboDTO claseComunicacion(HttpServletRequest request);

	/****/
	//TODO: REVISAR DTO de ENTRADA Y SALIDA
	public ConsultasDTO consultasSearch(HttpServletRequest request, ConsultasSearch filtros);

	public Error duplicarConsulta(HttpServletRequest request, ConsultaItem consultaItem);

	public Error borrarConsulta(HttpServletRequest request, String[] idConsulta);

	public Error guardarDatosGenerales(HttpServletRequest request, ConsultasSearch filtros);

	public ConsultaListadoModelosDTO obtenerModelosComunicacion(HttpServletRequest request, String idConsulta);

	public ConsultaListadoPlantillasDTO obtenerPlantillasEnvio(HttpServletRequest request, String idConsulta);

	public Error guardarConsulta(HttpServletRequest request, ConsultasSearch filtros);

	public Error ejecutarConsulta(HttpServletRequest request, String consulta);
}
