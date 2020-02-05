package org.itcgae.siga.com.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.CampoDinamicoItem;
import org.itcgae.siga.DTOs.com.CamposDinamicosDTO;
import org.itcgae.siga.DTOs.com.ConsultaDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;

public interface IConsultasService {

	/** Combos **/
	public ComboDTO modulo(HttpServletRequest request);

	public ComboDTO objetivo(HttpServletRequest request);

	public ComboDTO claseComunicacion(HttpServletRequest request);
	
	public ComboDTO claseComunicacionByModulo(String idModulo, HttpServletRequest request);

	/****/
	//TODO: REVISAR DTO de ENTRADA Y SALIDA
	public ConsultasDTO consultasSearch(HttpServletRequest request, ConsultasSearch filtros);

	public ConsultaDTO duplicarConsulta(HttpServletRequest request, ConsultaItem consulta);

	public Error borrarConsulta(HttpServletRequest request, ConsultaItem[] consultas);

	public Error guardarDatosGenerales(HttpServletRequest request, ConsultaItem consulta);

	public ConsultaListadoModelosDTO obtenerModelosComunicacion(HttpServletRequest request, ConsultaItem idConsulta);

	public ConsultaListadoPlantillasDTO obtenerPlantillasEnvio(HttpServletRequest request, ConsultaItem consulta);

	public Error guardarConsulta(HttpServletRequest request, ConsultaItem consulta);

	public ResponseFileDTO ejecutarConsulta(HttpServletRequest request, ConsultaItem consulta);

	public Map<String, String> obtenerMapaConsulta(String consulta);

	public CamposDinamicosDTO obtenerCamposConsulta(HttpServletRequest request, String idClaseComunicacion, String consulta);

	public String procesarEjecutarConsulta(AdmUsuarios usuario, String sentencia, List<CampoDinamicoItem> listaCampos,
			boolean sustituyeInstitucion) throws ParseException;

	public ArrayList<CampoDinamicoItem> obtenerCamposDinamicos(AdmUsuarios usuario, boolean comunicar, String consulta) throws Exception;

	public List<Map<String, Object>> ejecutarConsultaConClaves(String sentencia) throws ParseException, SigaExceptions, Exception;

	public String quitarEtiquetas(String sentencia);

	public List<Map<String, Object>> ejecutarConsultaConClavesLog(String sentencia, AdmUsuarios usuario,ModelosComunicacionItem modelosComunicacionItem, ConsultaItem consulta) throws Exception;
}
