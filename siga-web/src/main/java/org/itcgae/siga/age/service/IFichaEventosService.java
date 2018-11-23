package org.itcgae.siga.age.service;

import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.form.AsistenciaCursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IFichaEventosService {
	
	public static final String NOMBRE = "NOMBRE";
	public static final String ASISTENCIA = "ASISTENCIA";
	
	public static final List<String> CAMPOSPLANTILLA = Arrays.asList(IFichaEventosService.NOMBRE, IFichaEventosService.ASISTENCIA);
	
	public FormadorCursoDTO getTrainersLabels(String idCurso, HttpServletRequest request);
	
	public File createExcelAssistanceFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	
	public ResponseEntity<InputStreamResource> generateExcelAssistance(List<AsistenciaCursoItem> asistenciasCursoItem);

	public InsertResponseDTO saveEventCalendar(EventoItem eventoItem, HttpServletRequest request);
	
	public ComboDTO getTypeEvent(HttpServletRequest request);
	
	public ComboDTO getEventStates(HttpServletRequest request);
	
	public ComboDTO getRepeatEvery(HttpServletRequest request);

	public ComboDTO getDaysWeek(HttpServletRequest request);
	
}
