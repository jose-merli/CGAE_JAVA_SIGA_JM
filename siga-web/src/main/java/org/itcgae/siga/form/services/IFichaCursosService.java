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
	
	public static final String CODIGO_CURSO = "CÓDIGO CURSO";
	public static final String FORMA_PAGO = "FORMA PAGO";
	public static final String NIF = "NIF";
	
	public static final List<String> CAMPOSPLANTILLA = Arrays.asList(CODIGO_CURSO, FORMA_PAGO, NIF);

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
	
	public ResponseEntity<InputStreamResource> generateExcelInscriptions(CursoItem cursoItem);

	public UpdateResponseDTO uploadFileExcel(MultipartHttpServletRequest request) throws IllegalStateException, IOException;

	


}
