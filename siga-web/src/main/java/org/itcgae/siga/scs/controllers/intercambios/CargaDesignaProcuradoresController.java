package org.itcgae.siga.scs.controllers.intercambios;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.scs.services.intercambios.ICargaDesignaProcuradores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cargaDesignaProcuradores")
public class CargaDesignaProcuradoresController {
	
	private Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private ICargaDesignaProcuradores iCargaDesignaProcuradores;
	
	
	@RequestMapping(value = "/buscarDesignaProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemesaResolucionDTO> buscarDesignaProcuradores(@RequestBody RemesasResolucionItem remesasResolucionItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método buscarDesignaProcuradores");  
		RemesaResolucionDTO response = iCargaDesignaProcuradores.buscarDesignaProcuradores(remesasResolucionItem, request);
		LOGGER.info("Termina el método buscarDesignaProcuradores");
		return new ResponseEntity<RemesaResolucionDTO>(response, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/obtenerTipoPCAJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> obtenerOperacionTipoAccion(HttpServletRequest request) {
		LOGGER.info("Entra en el método obtenerOperacionTipoAccion");
		String response = iCargaDesignaProcuradores.obtenerTipoPCAJG(request);
		LOGGER.info("Termina el método obtenerOperacionTipoAccion");
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recuperarDatosContador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContador> recuperarDatosContador(HttpServletRequest request) {
		AdmContador response = iCargaDesignaProcuradores.recuperarDatosContador(request);
		return new ResponseEntity<AdmContador>(response, HttpStatus.OK);
	}

}
