package org.itcgae.siga.scs.controllers.remesas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.scs.services.ejg.IRemesasResultados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/remesasResultado")
public class RemesasResultadoController {
	
	private Logger LOGGER = Logger.getLogger(RemesasResultadoController.class);

	@Autowired
	private IRemesasResultados remesasResultados;
	
	@RequestMapping(value = "/buscarRemesasResultado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RemesaResultadoDTO> buscarRemesasResultado(@RequestBody RemesasResultadoItem remesasResultadoItem, HttpServletRequest request) {
		LOGGER.info("Entra en el método buscarRemesasResultado");
		RemesaResultadoDTO response = remesasResultados.buscarRemesasResultado(remesasResultadoItem, request);
		LOGGER.info("Termina el método buscarRemesasResultado");
		return new ResponseEntity<RemesaResultadoDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/descargarFicheros", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarFicheros(@RequestBody List<RemesasResultadoItem> listaRemesasResultadoItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = remesasResultados.descargarFicheros(listaRemesasResultadoItem,request);
		return response;
	}
	
	@RequestMapping(value = "/recuperarDatosContador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AdmContador> recuperarDatosContador(HttpServletRequest request) {
		AdmContador response = remesasResultados.recuperarDatosContador(request);
		return new ResponseEntity<AdmContador>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/guardarRemesaResultado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarRemesa(@RequestParam int idRemesaResolucion,
													@RequestParam("observaciones") String observaciones,
													@RequestParam("nombreFichero") String nombreFichero,
													@RequestParam("fechaCarga") String fechaCarga,
													@RequestParam("fechaResolucion") String fechaResolucion,
													MultipartHttpServletRequest  request){
		LOGGER.info("----ENTRA METODO GUARDAR RESULTADO-----");
		RemesasResolucionItem remesasResolucionItem = new RemesasResolucionItem();
		
		if(idRemesaResolucion != 0) {
			remesasResolucionItem.setIdRemesaResolucion(idRemesaResolucion);
		}
		remesasResolucionItem.setObservaciones(observaciones);
		remesasResolucionItem.setNombreFichero(nombreFichero);
	
		try {
			Date dateB = new SimpleDateFormat("dd/mm/yyyy")
					.parse(fechaCarga);
			Date dateC = new SimpleDateFormat("dd/mm/yyyy")
					.parse(fechaResolucion);
			remesasResolucionItem.setFechaCarga(dateB);
			remesasResolucionItem.setFechaResolucion(dateC);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UpdateResponseDTO response = remesasResultados.guardarRemesaResultado(remesasResolucionItem,  request);
		
		LOGGER.info("----SALIDA METODO GUARDAR RESULTADO-----");
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
