package org.itcgae.siga.age.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IDatosNotificacionesService {
	
	public ComboDTO getTypeNotifications(HttpServletRequest request);
	
	public ComboDTO getMeasuredUnit(HttpServletRequest request);
	
	public ComboDTO getTypeWhere (HttpServletRequest request);
	
	public InsertResponseDTO saveNotification(NotificacionEventoItem notificacionItem, HttpServletRequest request);
	
	public ComboDTO getTemplates (HttpServletRequest request);
	
	public ComboDTO getTypeSend (String idPlantillaEnvio, HttpServletRequest request);
	
	public UpdateResponseDTO updateNotification(NotificacionEventoItem notificacionItem, HttpServletRequest request);
}
