package org.itcgae.siga.scs.controllers.remesas;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.scs.services.ejg.IRemesasResoluciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	
	@RequestMapping(value ="/descargarRemesasResolucion", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarRemesasResolucion(@RequestBody List<RemesasResolucionItem> remesasResolucionItem, HttpServletRequest request){
		LOGGER.info("Entra en el método descargarRemesasResolucion");  
		ResponseEntity<InputStreamResource> response = remesasResoluciones.descargarRemesasResolucion(remesasResolucionItem, request);
		LOGGER.info("Termina en el método descargarRemesasResolucion");  
		return response;	
	}
	
	@RequestMapping(value = "/obtenerOperacionTipoAccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EcomOperacionTipoaccionDTO> obtenerOperacionTipoAccion(HttpServletRequest request) {
		LOGGER.info("Entra en el método obtenerOperacionTipoAccion");
		EcomOperacionTipoaccionDTO response = remesasResoluciones.obtenerOperacionTipoAccion(request);
		LOGGER.info("Termina el método obtenerOperacionTipoAccion");
		return new ResponseEntity<EcomOperacionTipoaccionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/obtenerResoluciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EcomOperacionTipoaccionDTO> obtenerResoluciones(HttpServletRequest request) {
		LOGGER.info("Entra en el método obtenerResoluciones");
		EcomOperacionTipoaccionDTO response = remesasResoluciones.obtenerResoluciones(request);
		LOGGER.info("Termina el método obtenerResoluciones");
		return new ResponseEntity<EcomOperacionTipoaccionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/guardarRemesaResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarRemesa(@RequestParam int idRemesaResolucion,
													@RequestParam("observaciones") String observaciones,
													@RequestParam("idTipoRemesa") int idTipoRemesa,
													@RequestParam("nombreFichero") String nombreFichero,
													@RequestParam("fechaResolucion") String fechaResolucion,
													MultipartHttpServletRequest  request) throws IllegalStateException, IOException{
		LOGGER.info("----ENTRA METODO GUARDAR RESOLUCION-----");
		RemesasResolucionItem remesasResolucionItem = new RemesasResolucionItem();
		
		remesasResolucionItem.setIdTipoRemesa(idTipoRemesa);
		if(idRemesaResolucion != 0) remesasResolucionItem.setIdRemesa(idRemesaResolucion);
		if(observaciones.length() == 0) observaciones = null;
		remesasResolucionItem.setObservaciones(observaciones);
		
		try {
			Date dateC = new SimpleDateFormat("dd/mm/yyyy").parse(fechaResolucion);
			remesasResolucionItem.setFechaResolucion(dateC);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		UpdateResponseDTO response = remesasResoluciones.guardarRemesaResolucion(remesasResolucionItem,  request);
		
		LOGGER.info("----SALIDA METODO GUARDAR RESOLUCION-----");
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/actualizarRemesaResolucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizarRemesaResolucion(@RequestBody RemesasResolucionItem remesasResolucionItem, HttpServletRequest request) {
		LOGGER.info("----ENTRA METODO ACTUALIZAR RESOLUCION-----");
		
		UpdateResponseDTO response = remesasResoluciones.actualizarRemesaResolucion(remesasResolucionItem,  request);
		
		LOGGER.info("----SALIDA METODO ACTUALIZAR RESOLUCION-----");
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/recuperarDatosContador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContador> recuperarDatosContador(HttpServletRequest request) {
		AdmContador response = remesasResoluciones.recuperarDatosContador(request);
		return new ResponseEntity<AdmContador>(response, HttpStatus.OK);
	}
}
