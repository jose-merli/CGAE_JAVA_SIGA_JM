package org.itcgae.siga.age.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.ComboPlantillaEnvioDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IDatosNotificacionesService {
	
	public ComboDTO getTypeNotifications(HttpServletRequest request);
	
	public ComboDTO getMeasuredUnit(HttpServletRequest request);
	
	public ComboDTO getTypeWhere(HttpServletRequest request);
	
	public InsertResponseDTO saveNotification(NotificacionEventoItem notificacionItem, HttpServletRequest request);
	
	public ComboPlantillaEnvioDTO getTemplates (HttpServletRequest request);
	
	public ComboDTO getTypeSend (String idPlantillaEnvio, String idTipoEnvio, HttpServletRequest request);
	
	public UpdateResponseDTO updateNotification(NotificacionEventoItem notificacionItem, HttpServletRequest request);
	
	public NotificacionEventoDTO getEventNotifications(String idCalendario, HttpServletRequest request);
	
	public NotificacionEventoDTO getHistoricEventNotifications(String idCalendario, HttpServletRequest request);
	
	public UpdateResponseDTO deleteNotification(NotificacionEventoDTO notificacionDTO, HttpServletRequest request);
}
