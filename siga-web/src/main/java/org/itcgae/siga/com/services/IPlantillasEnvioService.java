package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;


import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.FinalidadConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaDatosConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaRemitenteDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IPlantillasEnvioService {
	/**Combos**/
	public ComboDTO getComboConsultas(HttpServletRequest request, String filtro);
	
	/****/
	public PlantillasEnvioDTO PlantillasEnvioSearch(HttpServletRequest request, PlantillaEnvioSearchItem filtros);
	public Error borrarPlantillasEnvio(HttpServletRequest request, PlantillaEnvioItem[] plantillasEnvio);
	public Error guardarDatosGenerales(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public ConsultasDTO detalleConsultas(HttpServletRequest request, TarjetaConfiguracionDto consulta);
	public Error asociarConsulta(HttpServletRequest request, PlantillaDatosConsultaDTO consulta);
	public Error desAsociarConsulta(HttpServletRequest request, PlantillaDatosConsultaDTO[] consulta);
	public RemitenteDTO detalleRemitente(HttpServletRequest request, PlantillaDatosConsultaDTO consulta);
	public RemitenteDTO obtenerPersonaYdireccion(HttpServletRequest request, String idPersona);
	public Error guardarRemitente(HttpServletRequest request, TarjetaRemitenteDTO remitente);
	public FinalidadConsultaDTO obtenerFinalidad(HttpServletRequest request, String idConsulta);

	public String obtenerFinalidadByIdConsulta(Short idInstitucion, Long idConsulta);
	
}
