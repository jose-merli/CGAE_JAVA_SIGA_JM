package org.itcgae.siga.scs.controllers.intercambios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.scs.services.intercambios.ICargaDesignaProcuradores;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/cargaDesignaProcuradores")
public class CargaDesignaProcuradoresController {
	
	private Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private ICargaDesignaProcuradores iCargaDesignaProcuradores;
	
	
	@RequestMapping(value = "/buscarDesignaProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemesaResolucionDTO> buscarDesignaProcuradores(@RequestBody RemesasResolucionItem remesasResolucionItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método buscarDesignaProcuradores");  
		RemesaResolucionDTO response = new RemesaResolucionDTO();
	
		try {
			response = iCargaDesignaProcuradores.buscarDesignaProcuradores(remesasResolucionItem, request);
			return new ResponseEntity<RemesaResolucionDTO>(response, HttpStatus.OK);
		}catch(Exception e) {
			response.setError(UtilidadesString.creaError(e.toString()));
			LOGGER.info("Termina el método buscarDesignaProcuradores");
			return new ResponseEntity<RemesaResolucionDTO>(response, HttpStatus.OK);
		}
	}

	
	@RequestMapping(value = "/obtenerOperacionTipoAccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EcomOperacionTipoaccionDTO> obtenerOperacionTipoAccion(HttpServletRequest request) {
		LOGGER.info("Entra en el método obtenerOperacionTipoAccion");
		EcomOperacionTipoaccionDTO response = iCargaDesignaProcuradores.obtenerOperacionTipoAccion(request);
		LOGGER.info("Termina el método obtenerOperacionTipoAccion");
		return new ResponseEntity<EcomOperacionTipoaccionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/obtenerDesignaProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EcomOperacionTipoaccionDTO> obtenerDesignaProcuradores(HttpServletRequest request) {
		LOGGER.info("Entra en el método obtenerDesignaProcuradores");
		EcomOperacionTipoaccionDTO response = iCargaDesignaProcuradores.obtenerDesignaProcuradores(request);
		LOGGER.info("Termina el método obtenerDesignaProcuradores");
		return new ResponseEntity<EcomOperacionTipoaccionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recuperarDatosContador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContador> recuperarDatosContador(HttpServletRequest request) {
		AdmContador response = iCargaDesignaProcuradores.recuperarDatosContador(request);
		return new ResponseEntity<AdmContador>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/guardarDesignaProcuradores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarRemesa(@RequestParam int idRemesaResolucion,
													@RequestParam("observaciones") String observaciones,
													@RequestParam("nombreFichero") String nombreFichero,
													@RequestParam("fechaResolucion") String fechaResolucion,
													MultipartHttpServletRequest request ){
		LOGGER.info("----ENTRA METODO GUARDAR DESIGNA PROCURADORES-----");
		RemesasResolucionItem remesasResolucionItem = new RemesasResolucionItem();
		
		if(idRemesaResolucion != 0) {
			remesasResolucionItem.setIdRemesa(idRemesaResolucion);
		}
		remesasResolucionItem.setObservaciones(observaciones);
		remesasResolucionItem.setNombreFichero(nombreFichero);
	
		try {
			Date dateC = new SimpleDateFormat("dd/mm/yyyy").parse(fechaResolucion);
			remesasResolucionItem.setFechaResolucion(dateC);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		UpdateResponseDTO response = iCargaDesignaProcuradores.guardarDesignaProcuradores(remesasResolucionItem,  request);
		
		LOGGER.info("----SALIDA METODO DESIGNA PROCURADORES-----");
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
