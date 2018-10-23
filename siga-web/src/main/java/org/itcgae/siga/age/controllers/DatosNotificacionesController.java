package org.itcgae.siga.age.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.age.service.IDatosNotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatosNotificacionesController {
	
	@Autowired
	private IDatosNotificacionesService datosNotificacionesService;
		
	
	@RequestMapping(value = "datosNotificaciones/getTypeNotifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeNotifications(HttpServletRequest request) {
		ComboDTO response = datosNotificacionesService.getTypeNotifications(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "datosNotificaciones/getMeasuredUnit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getMeasuredUnit(HttpServletRequest request) {
		ComboDTO response = datosNotificacionesService.getMeasuredUnit(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "datosNotificaciones/getTypeWhere", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeWhere(HttpServletRequest request) {
		ComboDTO response = datosNotificacionesService.getTypeWhere(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "datosNotificaciones/saveNotification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveNotification(@RequestBody NotificacionEventoItem notificacionItem, HttpServletRequest request) {
		InsertResponseDTO response = datosNotificacionesService.saveNotification(notificacionItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "datosNotificaciones/getTemplates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTemplates(HttpServletRequest request) {
		ComboDTO response = datosNotificacionesService.getTemplates(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "datosNotificaciones/getTypeSend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeSend(String idPlantillaEnvio, HttpServletRequest request) {
		ComboDTO response = datosNotificacionesService.getTypeSend(idPlantillaEnvio, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "datosNotificaciones/updateNotification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateNotification(@RequestBody NotificacionEventoItem notificacionItem, HttpServletRequest request) {
		UpdateResponseDTO response = datosNotificacionesService.updateNotification(notificacionItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	
	
}
