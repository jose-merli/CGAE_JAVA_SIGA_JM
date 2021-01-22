package org.itcgae.siga.com.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDTO;
import org.itcgae.siga.DTOs.com.PlantillasModeloDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDTO;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IModelosYcomunicacionesService {

	
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros, boolean historico);
	public String duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion);
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem[] modeloComunicacion);
	public ComboDTO obtenerPerfilesModelo(HttpServletRequest request, String idInstitucion, String idModeloComunicacion);
	public ComboDTO colegiosModelo(HttpServletRequest request);
	public PlantillasDocumentosDTO obtenerInformes(HttpServletRequest request, String idInstitucion, String idModeloComuncacion);
	public Error guardarPerfilesModelo(HttpServletRequest request, TarjetaPerfilesDTO perfilesDTO);

	public ResponseDataDTO guardarDatosGenerales(HttpServletRequest request, TarjetaModeloConfiguracionDTO datosTarjeta);
	
	public Error guardarPlantillaEnviosModelo(HttpServletRequest request, List<PlantillaModeloBorrarDTO> plantilla);
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearch(HttpServletRequest request, String idModelo);
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearchHist(HttpServletRequest request, String idModelo);
	public Error borrarPlantillaEnviosModelo(HttpServletRequest request, PlantillaModeloBorrarDTO[] plantillas);
	public Error borrarInformes(HttpServletRequest request, PlantillaDocumentoBorrarDTO[] plantillasDoc);
	public ComboDTO obtenerPlantillasComunicacion(String idClaseComunicacion, HttpServletRequest request);
	public PlantillaEnvioItem obtenerTipoEnvioPlantilla(HttpServletRequest request, String idPlantilla);
	
	public Error rehabilitarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem[] modeloComunicacion);
	public ModelosComunicacionItem modeloYComunicacionesSearchModelo(HttpServletRequest request, String idModelo);
	
	public Boolean comprobarNombreModeloComunicacion(HttpServletRequest request, TarjetaModeloConfiguracionDTO datosTarjeta);
	public PlantillasModeloDTO obtenerPlantillaPorDefecto(HttpServletRequest request, String idModelo);
	
}
