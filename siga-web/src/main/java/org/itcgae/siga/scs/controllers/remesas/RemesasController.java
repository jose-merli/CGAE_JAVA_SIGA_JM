package org.itcgae.siga.scs.controllers.remesas;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.scs.services.ejg.IBusquedaRemesas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/remesasEnvio")
public class RemesasController {

	private Logger LOGGER = Logger.getLogger(RemesasController.class);

	@Autowired
	private IBusquedaRemesas busquedaRemesas;

	@RequestMapping(value = "/comboEstadoRemesa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstado(HttpServletRequest request) {
		ComboDTO response = busquedaRemesas.comboEstado(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscarRemesas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemesaBusquedaDTO> buscarRemesas(@RequestBody RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método buscarRemesas");
		RemesaBusquedaDTO response = busquedaRemesas.buscarRemesas(remesasBusquedaItem, request);
		LOGGER.info("Termina el método buscarRemesas");
		return new ResponseEntity<RemesaBusquedaDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/borrarRemesas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarRemesas(@RequestBody List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método borrarRemesas");
		DeleteResponseDTO response = busquedaRemesas.borrarRemesas(remesasBusquedaItem, request);
		LOGGER.info("Termina el método borrarRemesas");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listadoEstadosRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EstadoRemesaDTO> listadoEstadosRemesa(@RequestBody RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método listadoEstadosRemesa");
		EstadoRemesaDTO response = busquedaRemesas.listadoEstadoRemesa(remesasBusquedaItem, request);
		LOGGER.info("Termina el método listadoEstadosRemesa");
		return new ResponseEntity<EstadoRemesaDTO>(response, HttpStatus.OK);
	}
	
}