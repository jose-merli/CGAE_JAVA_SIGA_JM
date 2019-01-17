package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultaPlantillaDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaDTO;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDTO;
import org.itcgae.siga.DTOs.com.PlantillasModeloDTO;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDTO;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
import org.itcgae.siga.DTOs.com.TarjetaPlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IModelosYcomunicacionesService {

	/****/
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros, boolean historico);
	public Error duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion);
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem[] modeloComunicacion);

	public ComboDTO obtenerPerfilesModelo(HttpServletRequest request, String idInstitucion, String idModeloComunicacion);
	
	public PlantillasDocumentosDTO obtenerInformes(HttpServletRequest request, String idInstitucion, String idModeloComuncacion);
	public Error guardarPerfilesModelo(HttpServletRequest request, TarjetaPerfilesDTO perfilesDTO);
	Error guardarDatosGenerales(HttpServletRequest request, TarjetaModeloConfiguracionDTO datosTarjeta);
	public Error guardarModPlantillaDocumento(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);
	public ConsultasDTO obtenerConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc, boolean historico);
	public ComboDTO obtenerFormatoSalida(HttpServletRequest request);
	public ComboDTO obtenerSufijos(HttpServletRequest request);
	public ComboDTO obtenerConsultasDisponibles(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);
	public Error guardarPlantillaEnviosModelo(HttpServletRequest request, String idModelo, String idPlantillaEnvios);
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearch(HttpServletRequest request, String idModelo);
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearchHist(HttpServletRequest request, String idModelo);
	public Error borrarPlantillaEnviosModelo(HttpServletRequest request, PlantillaModeloBorrarDTO[] plantillas);
	public Error guardarConsultaPlantilla(HttpServletRequest request, ConsultaPlantillaDTO consultaPlantilla);
	public PlantillasDocumentosDTO guardarInformes(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);
	public ResponseDocumentoDTO uploadFile(MultipartHttpServletRequest request);
	public ResponseDocumentoDTO guardarPlantillaDocumento(HttpServletRequest request, DocumentoPlantillaDTO documento);
	
}
