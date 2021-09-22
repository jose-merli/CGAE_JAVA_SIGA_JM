package org.itcgae.siga.scs.controllers.remesas;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
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
import org.springframework.web.bind.annotation.RestController;

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

}
