package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDto;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
import org.itcgae.siga.DTOs.com.PlantillasModeloDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IModelosYcomunicacionesService {

	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros);
	public DatosModelosComunicacionesDTO modeloYComunicacionesHistoricoSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros);
	public Error duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion);
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion);
	public ComboDTO obtenerPerfilesModelo(HttpServletRequest request, String idModeloComunicacion);
	public PlantillasModeloDTO obtenerPlantillasModelo(HttpServletRequest request, String idModelo);
	public PlantillasModeloDTO obtenerPlantillasModeloHist(HttpServletRequest request, String idModelo);
	public PlantillasDocumentosDto obtenerInformes(HttpServletRequest request, String idInstitucion, String idModeloComuncacion);
	public Error guardarPerfilesModelo(HttpServletRequest request, TarjetaPerfilesDTO perfilesDTO);
	public Error guardarDatosGenerales(HttpServletRequest request, TarjetaModeloConfiguracionDto datosTarjeta);
	public Error borrarPlantillaModelo(HttpServletRequest request, PlantillaModeloBorrarDTO[] plantillas);
	public Error guardarPlantillaModelo(HttpServletRequest request, String idModelo, String idPlantillaEnvios);
}
