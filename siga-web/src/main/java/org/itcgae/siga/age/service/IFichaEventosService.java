package org.itcgae.siga.age.service;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.AsistenciaEventoDTO;
import org.itcgae.siga.DTOs.age.AsistenciaEventoItem;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.form.AgePersonaEventoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IFichaEventosService {
	
	public FormadorCursoDTO getTrainersLabels(String idCurso, HttpServletRequest request);
	
	public File createExcelAssistanceFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	
	public ResponseEntity<InputStreamResource> generateExcelAssistance(List<AsistenciaEventoItem> asistenciasCursoItem);

	public InsertResponseDTO saveEventCalendar(EventoItem eventoItem, HttpServletRequest request);
	
	public ComboDTO getTypeEvent(HttpServletRequest request);
	
	public ComboDTO getEventStates(HttpServletRequest request);
	
	public ComboDTO getRepeatEvery(HttpServletRequest request);

	public ComboDTO getDaysWeek(HttpServletRequest request);
	
	public ComboDTO getJudicialDistrict(HttpServletRequest request);
	
	public UpdateResponseDTO updateEventCalendar(EventoItem eventoItem, HttpServletRequest request);

	public UpdateResponseDTO deleteEventCalendar(EventoDTO eventoDTO, HttpServletRequest request);

	public NotificacionEventoDTO getEventNotifications(String idEvento, HttpServletRequest request);
	
	public NotificacionEventoDTO getHistoricEventNotifications(String idEvento, HttpServletRequest request);
	
	public EventoItem searchEvent(CursoItem cursoItem, HttpServletRequest request);

	public void generaEventosLaboral();
	
	public void insertarFestivosAuto();
	
	public AsistenciaEventoDTO getEntryListCourse(String idCurso, HttpServletRequest request);

	public InsertResponseDTO saveAssistancesCourse(AsistenciaEventoDTO asistenciaEventoDTO, HttpServletRequest request);
	
	public InsertResponseDTO saveFormadorEvent(AgePersonaEventoDTO agePersonaEventoDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateFormadorEvent(AgePersonaEventoDTO agePersonaEventoDTO, HttpServletRequest request);
	
	public FormadorCursoDTO getTrainersSession(String idEvento, HttpServletRequest request);
	
	public void updateEstadoEventoAuto();
	
	public void generateNotificationsAuto();
	
	public UpdateResponseDTO uploadFileExcel(int idEvento, MultipartHttpServletRequest request) throws IllegalStateException, IOException;

}
