package org.itcgae.siga.age.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;

public interface IDatosNotificacionesService {
	
	public ComboDTO getTypeNotifications(HttpServletRequest request);
	
	public ComboDTO getMeasuredUnit(HttpServletRequest request);
	
	public ComboDTO getTypeWhere(HttpServletRequest request);
	
	public InsertResponseDTO saveNotification(NotificacionEventoItem notificacionItem, HttpServletRequest request);
	
	public ComboPlantillaEnvioDTO getTemplates (HttpServletRequest request);
	
	public ComboDTO getTypeSend (String idPlantillaEnvio, String idTipoEnvio, HttpServletRequest request);
	
	public UpdateResponseDTO updateNotification(NotificacionEventoDTO notificacionEventoDTO, HttpServletRequest request);
	
	public NotificacionEventoDTO getCalendarNotifications(String idCalendario, HttpServletRequest request);
	
	public NotificacionEventoDTO getHistoricCalendarNotifications(String idCalendario, HttpServletRequest request);
	
	public UpdateResponseDTO deleteNotification(NotificacionEventoDTO notificacionDTO, HttpServletRequest request);
	
	public int checkGenerationDateNotification(AgeNotificacionesevento notification, AdmUsuarios usuario);
	
	public Date generateNotificationDate(AgeNotificacionesevento ageNotificacionEventoInsert);
	
	public ComboDTO getPlantillas(HttpServletRequest request);
	
	public ComboDTO getNotificationTypeCalendarTraining(HttpServletRequest request);


}
