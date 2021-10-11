package org.itcgae.siga.scs.controllers.remesas;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.RemesaAccionItem;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.db.entities.AdmContador;
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
	
	@RequestMapping(value = "/getUltimoRegitroRemesa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContador> getUltimoRegitroRemesa(HttpServletRequest request) {
		AdmContador response = busquedaRemesas.getUltimoRegitroRemesa(request);
		return new ResponseEntity<AdmContador>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/guardarRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarRemesa(@RequestBody RemesasItem remesasItem, HttpServletRequest request) {
		UpdateResponseDTO response = busquedaRemesas.guardarRemesa(remesasItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/getEJGRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EJGRemesaDTO> getEJGRemesa(@RequestBody RemesasItem remesasItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método buscarRemesas");
		EJGRemesaDTO response = busquedaRemesas.getEJGRemesa(remesasItem, request);
		LOGGER.info("Termina el método buscarRemesas");
		return new ResponseEntity<EJGRemesaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/borrarExpedientesRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarExpedientesRemesa(@RequestBody List<EJGRemesaItem> remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método borrarExpedientesRemesa");
		DeleteResponseDTO response = busquedaRemesas.borrarExpedientesRemesa(remesasBusquedaItem, request);
		LOGGER.info("Termina el método borrarExpedientesRemesa");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAcciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CheckAccionesRemesasDTO> getAcciones(@RequestBody RemesasItem remesasItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método checkAcciones");
		CheckAccionesRemesasDTO response = busquedaRemesas.getAcciones(remesasItem, request);
		LOGGER.info("Termina el método checkAcciones");
		return new ResponseEntity<CheckAccionesRemesasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ejecutaOperacionRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> ejecutaOperacionRemesa(@RequestBody RemesaAccionItem remesaAccionItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método ejecutaOperacionRemesa");
		InsertResponseDTO response = busquedaRemesas.ejecutaOperacionRemesa(remesaAccionItem, request);
		LOGGER.info("Termina el método ejecutaOperacionRemesa");
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
}