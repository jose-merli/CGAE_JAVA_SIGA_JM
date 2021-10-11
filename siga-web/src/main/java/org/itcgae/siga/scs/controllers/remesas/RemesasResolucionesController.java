package org.itcgae.siga.scs.controllers.remesas;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.scs.services.ejg.IRemesasResoluciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/remesasResoluciones")
public class RemesasResolucionesController {
	
	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	@Autowired
	private IRemesasResoluciones remesasResoluciones;
	
	@RequestMapping(value = "/buscarRemesasResoluciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemesaResolucionDTO> buscarRemesas(@RequestBody RemesasResolucionItem remesasResolucionItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método buscarRemesasResoluciones");  
		RemesaResolucionDTO response = remesasResoluciones.buscarRemesasResoluciones(remesasResolucionItem, request);
		LOGGER.info("Termina el método buscarRemesasResolucionesbuscarRemesasResoluciones");
		return new ResponseEntity<RemesaResolucionDTO>(response, HttpStatus.OK);
	}

}
