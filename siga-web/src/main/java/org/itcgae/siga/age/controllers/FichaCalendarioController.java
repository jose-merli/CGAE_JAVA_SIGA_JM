package org.itcgae.siga.age.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.PermisoCalendarioItem;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
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
	private IGestionUsuariosGruposService gestionUsuariosGruposService;
	
	@RequestMapping(value = "fichaCalendario/getCalendarType",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getCalendarType(HttpServletRequest request) {
		ComboDTO response = fichaCalendarioService.getCalendarType(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCalendario/profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getUsersProfile(HttpServletRequest request) {
		ComboDTO response = gestionUsuariosGruposService.getUsersProfile(request);
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
	
	@RequestMapping(value = "fichaCalendario/getProfilesPermissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PermisosPerfilesCalendarDTO> getProfilesPermissions(String idCalendario, HttpServletRequest request) {
		PermisosPerfilesCalendarDTO response = fichaCalendarioService.getProfilesPermissions(idCalendario, request);
		return new ResponseEntity<PermisosPerfilesCalendarDTO>(response, HttpStatus.OK);
	}
	
}
