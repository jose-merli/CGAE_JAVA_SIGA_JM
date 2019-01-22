package org.itcgae.siga.age.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoDTO;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.age.service.IDatosNotificacionesService;
import org.itcgae.siga.age.service.IFichaCalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaCalendarioController {
	
	@Autowired
	private IFichaCalendarioService fichaCalendarioService;
	
	@Autowired
	private IDatosNotificacionesService datosNotificacionesService;
		
	@RequestMapping(value = "fichaCalendario/getCalendarType",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCalendarType(HttpServletRequest request) {
		ComboDTO response = fichaCalendarioService.getCalendarType(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
		
	@RequestMapping(value = "fichaCalendario/updatePermissions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updatePermissions(@RequestBody PermisosCalendarioDTO permisosCalendarioDTO,
			HttpServletRequest request) {
		UpdateResponseDTO response = fichaCalendarioService.updatePermissions(permisosCalendarioDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/saveCalendar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveCalendar(@RequestBody CalendarItem calendarItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaCalendarioService.saveCalendar(calendarItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/updateCalendar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateCalendar(@RequestBody CalendarItem calendarItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = fichaCalendarioService.updateCalendar(calendarItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/getProfilesPermissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisosPerfilesCalendarDTO> getProfilesPermissions(String idCalendario, HttpServletRequest request) {
		PermisosPerfilesCalendarDTO response = fichaCalendarioService.getProfilesPermissions(idCalendario, request);
		return new ResponseEntity<PermisosPerfilesCalendarDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/getCalendar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CalendarDTO> getCalendar(String idCalendario, HttpServletRequest request) {
		CalendarDTO response = fichaCalendarioService.getCalendar(idCalendario, request);
		return new ResponseEntity<CalendarDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/getCalendarNotifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NotificacionEventoDTO> getCalendarNotifications(String idCalendario, HttpServletRequest request) {
		NotificacionEventoDTO response = datosNotificacionesService.getCalendarNotifications(idCalendario, request);
		return new ResponseEntity<NotificacionEventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/getHistoricCalendarNotifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NotificacionEventoDTO> getHistoricCalendarNotifications(String idCalendario, HttpServletRequest request) {
		NotificacionEventoDTO response = datosNotificacionesService.getHistoricCalendarNotifications(idCalendario, request);
		return new ResponseEntity<NotificacionEventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/deleteNotification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteNotification(@RequestBody NotificacionEventoDTO notificacionDTO, HttpServletRequest request) {
		UpdateResponseDTO response = datosNotificacionesService.deleteNotification(notificacionDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
}
