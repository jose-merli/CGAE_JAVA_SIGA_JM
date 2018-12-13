package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IPlantillasEnvioService {
	/**Combos**/
	public ComboDTO getComboTipoEnvio(HttpServletRequest request);
	public ComboDTO getComboConsultas(HttpServletRequest request);
	
	/****/
	public PlantillasEnvioDTO PlantillasEnvioSearch(HttpServletRequest request, PlantillaEnvioSearchItem filtros);
	public Error borrarPlantillasEnvio(HttpServletRequest request, PlantillasEnvioDTO plantillasEnvio);
	public Error guardarDatosGenerales(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public ConsultasDTO detalleConsultas(HttpServletRequest request, ConsultasDTO consultas);
	public Error asociarConsulta(HttpServletRequest request, ConsultaItem consulta);
	public Error borrarConsulta(HttpServletRequest request, ConsultaItem consulta);
	public RemitenteDTO detalleRemitente(HttpServletRequest request, String idConsulta);
	public Error guardarREmitente(HttpServletRequest request, RemitenteDTO remitente);
	
}
