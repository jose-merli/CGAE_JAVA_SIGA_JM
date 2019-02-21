package org.itcgae.siga.com.services;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ByteResponseDto;
import org.itcgae.siga.DTOs.com.ClaseComunicacionesDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.KeysDTO;
import org.itcgae.siga.DTOs.com.ModeloDialogoItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDateDTO;
import org.itcgae.siga.DTOs.com.TipoEnvioDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IDialogoComunicacionService {
	
	public ComboDTO obtenerClaseComunicaciones(HttpServletRequest request, String rutaClaseComunicacion);
	public ClaseComunicacionesDTO obtenerClaseComunicacionesUnica(HttpServletRequest request, String rutaClaseComunicacion);
	public ModelosComunicacionSearch obtenerModelos(HttpServletRequest request, ModeloDialogoItem modeloDTO);
	public TipoEnvioDTO obtenertipoEnvioPlantilla(HttpServletRequest request, String idPlantilla);
	public ByteResponseDto descargarComunicacion(HttpServletRequest request, DialogoComunicacionItem dialogo);
	public KeysDTO obtenerKeysClaseComunicacion(HttpServletRequest request, String idClaseComunicacion);
	public ConsultasDTO obtenerCamposModelo(HttpServletRequest request, DialogoComunicacionItem dialogo);
	public Error enviarTest(HttpServletRequest request);
	public ResponseDateDTO obtenerFechaProgramada(HttpServletRequest request);
	public ResponseDataDTO obtenerNumMaximoModelos(HttpServletRequest request);
	public Error generarEnvios(HttpServletRequest request, DialogoComunicacionItem dialogo);
	public String obtenerNombreFicheroSalida(String idModeloComunicacion, PlantillaModeloDocumentoDTO plantilla, HashMap<String, Object> hDatosGenerales, String idLenguaje, int numFichero, String pathFicheroSalida, String campoSufijo);
}
