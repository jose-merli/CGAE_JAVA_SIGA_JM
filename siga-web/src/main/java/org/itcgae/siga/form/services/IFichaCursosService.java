package org.itcgae.siga.form.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.form.CargaMasivaInscripcionesDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IFichaCursosService {
	
	public static final Short DESIGNAR_TUTOR = 0; 
	
	public static final Short ASIGNAR_TUTOR = 1; 
	
	public static final long ANUNCIADO_CURSO = 1; 
	
	public static final long ABIERTO_CURSO = 0; 
	
	public static final long INSCRIPCION_PENDIENTE = 1; 
	public static final long INSCRIPCION_CANCELADA = 4; 
	public static final long INSCRIPCION_APROBADA = 3; 
	public static final long INSCRIPCION_RECHAZADA = 2; 

	
	public static final short ID_TIPO_SERVICIOS_FORMACION = 5;
	public static final short PERIOCIDAD_1MES = 1;

	
	public static final String CODIGO_CURSO = "CÓDIGO CURSO";
	public static final String FORMA_PAGO = "FORMA PAGO";
	public static final String NIF = "NIF";
	public static final String NOMBRE_PERSONA = "NOMBRE_PERSONA";
	public static final String NOMBRE_CURSO = "NOMBRE_CURSO";
	public static final String ESTADO = "ESTADO";
	public static final String ERRORES = "ERRORES";
	
	public static final List<String> CAMPOSPLANTILLA = Arrays.asList(CODIGO_CURSO, FORMA_PAGO, NIF);
	public static final List<String> CAMPOSPLOG = Arrays.asList(CODIGO_CURSO, NOMBRE_CURSO, FORMA_PAGO, NIF, NOMBRE_PERSONA, ERRORES);
	
	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx = "xlsx";
	public static final String nombreFicheroEjemplo = "PlantillaMasivaInscripciones";
	public static final String nombreFicheroError = "LogErrorCargaMasivaInscripciones";


	public void updateEstadoCursoAuto();
	
	public FormadorCursoDTO getTrainersCourse(String idCurso, HttpServletRequest request);
	
	public ComboDTO getRolesTrainers(HttpServletRequest request);
	
	public ComboDTO getTypeCostTrainers(HttpServletRequest request);

	public UpdateResponseDTO updateTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);

	public InsertResponseDTO saveTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);
	
	public InsertResponseDTO saveCourse(CursoItem cursoItem, HttpServletRequest request);

	public UpdateResponseDTO updateCourse(CursoItem cursoItem, HttpServletRequest request);
	
	public UpdateResponseDTO releaseOrAnnounceCourse(CursoDTO cursoDTO , HttpServletRequest request);

	public CursoItem searchCourse(String idCurso, HttpServletRequest request);
	
	public EventoDTO getSessionsCourse(CursoItem cursoItem, HttpServletRequest request);

	public ComboDTO getServicesCourse(HttpServletRequest request);
	
	public ComboDTO getServicesSpecificCourse(HttpServletRequest request, String idCurso);
	
	public InscripcionItem getCountIncriptions(HttpServletRequest request, String idCurso);
	
	public File createExcelInscriptionsFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	
	public ResponseEntity<InputStreamResource> generateExcelInscriptions(CursoItem cursoItem, HttpServletRequest request);

	public UpdateResponseDTO uploadFileExcel(int idCurso, MultipartHttpServletRequest request) throws IllegalStateException, IOException;
	
	public CargaMasivaInscripcionesDTO getMassiveLoadInscriptions(HttpServletRequest request, String idCurso);

	public UpdateResponseDTO deleteInscriptionsCourse(CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request);
	
	public UpdateResponseDTO autovalidateInscriptionsCourse(CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request);

	

}
