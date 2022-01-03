package org.itcgae.siga.scs.controllers.remesas;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorItem;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.RemesaAccionItem;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.scs.services.ejg.IBusquedaRemesas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
	List<String> cabecera = Arrays.asList(HttpHeaders.CONTENT_DISPOSITION);

	@Autowired
	private IBusquedaRemesas busquedaRemesas;

	@RequestMapping(value = "/comboEstadoRemesa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstado(HttpServletRequest request) {
		ComboDTO response = busquedaRemesas.comboEstado(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscarRemesas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemesaBusquedaDTO> buscarRemesas(@RequestBody RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método buscarRemesas");
		RemesaBusquedaDTO response = busquedaRemesas.buscarRemesas(remesasBusquedaItem, request);
		LOGGER.debug("Termina el método buscarRemesas");
		return new ResponseEntity<RemesaBusquedaDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/borrarRemesas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarRemesas(@RequestBody List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método borrarRemesas");
		DeleteResponseDTO response = busquedaRemesas.borrarRemesas(remesasBusquedaItem, request);
		LOGGER.debug("Termina el método borrarRemesas");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listadoEstadosRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EstadoRemesaDTO> listadoEstadosRemesa(@RequestBody RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método listadoEstadosRemesa");
		EstadoRemesaDTO response = busquedaRemesas.listadoEstadoRemesa(remesasBusquedaItem, request);
		LOGGER.debug("Termina el método listadoEstadosRemesa");
		return new ResponseEntity<EstadoRemesaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUltimoRegitroRemesa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContador> getUltimoRegitroRemesa(HttpServletRequest request) {
		AdmContador response = busquedaRemesas.getUltimoRegitroRemesa(request);
		return new ResponseEntity<AdmContador>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/guardarRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarRemesa(@RequestBody RemesasItem remesasItem, HttpServletRequest request) {
		UpdateResponseDTO response;
		try {
			response = busquedaRemesas.guardarRemesa(remesasItem, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else {
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value = "/getEJGRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EJGRemesaDTO> getEJGRemesa(@RequestBody RemesasItem remesasItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método buscarRemesas");
		EJGRemesaDTO response = busquedaRemesas.getEJGRemesa(remesasItem, request);
		LOGGER.debug("Termina el método buscarRemesas");
		return new ResponseEntity<EJGRemesaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/borrarExpedientesRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarExpedientesRemesa(@RequestBody List<EJGRemesaItem> remesasBusquedaItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método borrarExpedientesRemesa");
		DeleteResponseDTO response = busquedaRemesas.borrarExpedientesRemesa(remesasBusquedaItem, request);
		LOGGER.debug("Termina el método borrarExpedientesRemesa");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAcciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CheckAccionesRemesasDTO> getAcciones(@RequestBody RemesasItem remesasItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método checkAcciones");
		CheckAccionesRemesasDTO response = busquedaRemesas.getAcciones(remesasItem, request);
		LOGGER.debug("Termina el método checkAcciones");
		return new ResponseEntity<CheckAccionesRemesasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ejecutaOperacionRemesa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> ejecutaOperacionRemesa(@RequestBody RemesaAccionItem remesaAccionItem, HttpServletRequest request) throws SigaExceptions {
		LOGGER.debug("Entra en el método ejecutaOperacionRemesa");
		InsertResponseDTO response = busquedaRemesas.ejecutaOperacionRemesa(remesaAccionItem, request);
		LOGGER.debug("Termina el método ejecutaOperacionRemesa");
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/descargarLogErrores", method = RequestMethod.POST, produces = "application/vnd.ms-excel")
	ResponseEntity<InputStreamResource> descargarLogErrores(@RequestBody RemesaAccionItem remesaAccionItem, HttpServletRequest request) {
		LOGGER.debug("Entra en el método descargarLogErrores");
		InputStreamResource response = busquedaRemesas.descargarLogErrores(remesaAccionItem, request);
		LOGGER.debug("Termina el método descargarLogErrores");
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ErrorRemesa" + remesaAccionItem.getIdRemesa()+ ".xls");
		header.setAccessControlExposeHeaders(cabecera);
		return new ResponseEntity<InputStreamResource>(response, header, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/descargar", method = RequestMethod.POST, produces = "application/zip")
	ResponseEntity<InputStreamResource> descargar(@RequestBody RemesaAccionItem remesaAccionItem, HttpServletRequest request) throws SigaExceptions {
		return busquedaRemesas.descargar(remesaAccionItem, request);
	}
	
}