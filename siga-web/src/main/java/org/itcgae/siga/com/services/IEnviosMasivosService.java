package org.itcgae.siga.com.services;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.com.ComboConsultaInstitucionDTO;
import org.itcgae.siga.DTOs.com.ConsultaDestinatarioItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DestinatarioIndvEnvioMasivoItem;
import org.itcgae.siga.DTOs.com.DestinatariosDTO;
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
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ENVIOS_MASIVOS_LOG_EXTENSION;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IEnviosMasivosService {
	
	public ComboDTO estadoEnvios(HttpServletRequest request);
	public ComboDTO tipoEnvio(HttpServletRequest request);
	public ComboDTO nombrePlantillas(HttpServletRequest request, String idTipoEnvio);
	public ComboInstitucionDTO obtenerEtiquetas(HttpServletRequest request);
	public ComboInstitucionDTO obtenerEtiquetasEnvio(HttpServletRequest request, String idEnvio);
	public EnviosMasivosDTO enviosMasivosSearch(HttpServletRequest request, EnviosMasivosSearch filtros);
	public Error programarEnvio (HttpServletRequest request, EnviosMasivosItem[] envioProgramarDto);
	public Error cancelarEnvios (HttpServletRequest request, EnvioProgramadoDto[] enviosProgramadosDto);
	public Error enviar(HttpServletRequest request, EnvioProgramadoDto[] envios);
	public Error guardarConfiguracion(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public EnviosMasivosDTO duplicarEnvio(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public Error guardarEtiquetasEnvio(HttpServletRequest request, TarjetaEtiquetasDTO etiquetasDTO);
	public Error guardarDocumentoEnvio(HttpServletRequest request, ResponseDocumentoDTO documentoDTO);
	public DocumentosEnvioDTO obtenerDocumentosEnvio(HttpServletRequest request, String idEnvio);
	public ResponseDocumentoDTO uploadFile(Long idEnvio, MultipartHttpServletRequest request) throws IOException;
	public Error borrarDocumento(HttpServletRequest request, ResponseDocumentoDTO[] documentoDTO);
	public PlantillaEnvioItem obtenerAsuntoYcuerpo (HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta);
	public ComboConsultaInstitucionDTO obtenerconsultasDestinatarios(HttpServletRequest request);
	public ConsultasDTO consultasDestAsociadas(HttpServletRequest request, String idEnvio);
	public Error asociarConsulta (HttpServletRequest request, ConsultaDestinatarioItem consulta);
	public Error desAsociarConsulta(HttpServletRequest request, ConsultaDestinatarioItem[] consulta);
	public ComboConsultaInstitucionDTO getComboConsultas(HttpServletRequest request, String filtro);
	public DestinatariosDTO obtenerDestinatariosIndv(HttpServletRequest request, String idEnvio);
	public Error asociarDestinatario(HttpServletRequest request, DestinatarioIndvEnvioMasivoItem destinatario);
	public Error desAsociarDestinatarios(HttpServletRequest request, DestinatarioIndvEnvioMasivoItem[] destinatario);
	public DatosDireccionesDTO obtenerDireccionesDisp(HttpServletRequest request, String nif);
	public Resource recuperaPdfBuroSMS(Short idInstitucion, Long parseInt, Integer idDocumento);
	public File[] getFicherosLOGEnvioMasivo(Short idInstitucion, Long idEnvio);
	public File getPathFicheroLOGEnvioMasivo(Short idInstitucion, Long idEnvio);
	public EnviosMasivosDTO busquedaEnvioMasivoSearch(HttpServletRequest request, EnviosMasivosSearch filtros);
	public EnviosMasivosDTO obtenerDestinatarios(HttpServletRequest request, EnviosMasivosDTO enviosMasivosDTO);
	public String getPathFicheroEnvioMasivo(Short idInstitucion, Long idEnvio, EnvEnvios envioInfoAux);

}

