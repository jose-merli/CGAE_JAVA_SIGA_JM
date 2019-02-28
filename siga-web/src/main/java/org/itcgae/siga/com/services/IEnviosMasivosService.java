package org.itcgae.siga.com.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DocumentosEnvioDTO;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaEtiquetasDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IEnviosMasivosService {
	
	public ComboDTO estadoEnvios(HttpServletRequest request);
	public ComboDTO tipoEnvio(HttpServletRequest request);
	public ComboDTO nombrePlantillas(HttpServletRequest request, String idTipoEnvio);
	public ComboDTO obtenerEtiquetas(HttpServletRequest request);
	public ComboDTO obtenerEtiquetasEnvio(HttpServletRequest request, String idEnvio);
	public EnviosMasivosDTO enviosMasivosSearch(HttpServletRequest request, EnviosMasivosSearch filtros);
	public Error programarEnvio (HttpServletRequest request, EnviosMasivosItem[] envioProgramarDto);
	public Error cancelarEnvios (HttpServletRequest request, EnvioProgramadoDto[] enviosProgramadosDto);
	public Error enviar(HttpServletRequest request, EnvioProgramadoDto[] envios);
	public Error guardarConfiguracion(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public EnviosMasivosDTO duplicarEnvio(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public Error guardarEtiquetasEnvio(HttpServletRequest request, TarjetaEtiquetasDTO etiquetasDTO);
	public Error guardarDocumentoEnvio(HttpServletRequest request, ResponseDocumentoDTO documentoDTO);
	public DocumentosEnvioDTO obtenerDocumentosEnvio(HttpServletRequest request, String idEnvio);
	public ResponseDocumentoDTO uploadFile(MultipartHttpServletRequest request) throws IOException;
	public Error borrarDocumento(HttpServletRequest request, ResponseDocumentoDTO[] documentoDTO);
	public PlantillaEnvioItem obtenerAsuntoYcuerpo (HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);

}
