package org.itcgae.siga.age.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IFichaCalendarioService {

	public ComboDTO getCalendarType(HttpServletRequest request);
	
	public UpdateResponseDTO updatePermissions(PermisosCalendarioDTO permisosCalendarioDTO, HttpServletRequest request);
	
	public InsertResponseDTO saveCalendar(CalendarItem calendarItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateCalendar(CalendarItem calendarItem, HttpServletRequest request);
	
	public CalendarDTO getCalendar(String idCalendario, HttpServletRequest request);
	
	public PermisosPerfilesCalendarDTO getProfilesPermissions(String idCalendario, HttpServletRequest request);
	
	public NotificacionEventoDTO getEventNotifications(String idCalendario, HttpServletRequest request);
	
}
