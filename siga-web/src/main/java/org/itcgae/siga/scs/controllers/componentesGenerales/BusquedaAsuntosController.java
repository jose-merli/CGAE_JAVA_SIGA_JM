package org.itcgae.siga.scs.controllers.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaAsuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/gestionJusticiables")
public class BusquedaAsuntosController {
	
	@Autowired
	private BusquedaAsuntosService busquedaAsuntosService;
	
	@PostMapping("/busquedaClaveAsuntosEJG")
	public ResponseEntity<AsuntosJusticiableDTO> searchClaveAsuntosEJG(HttpServletRequest request, @RequestBody AsuntosJusticiableItem asuntosJusticiableItem) {
		AsuntosJusticiableDTO response = busquedaAsuntosService.searchClaveAsuntosEJG(request, asuntosJusticiableItem);
		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping("/busquedaClaveAsuntosAsistencias")
	public ResponseEntity<AsuntosJusticiableDTO> searchClaveAsuntosAsistencias(HttpServletRequest request, @RequestBody AsuntosJusticiableItem asuntosJusticiableItem) {
		AsuntosJusticiableDTO response = busquedaAsuntosService.searchClaveAsuntosAsistencias(request, asuntosJusticiableItem);
		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping("/busquedaClaveAsuntosDesignaciones")
	public ResponseEntity<AsuntosJusticiableDTO> searchClaveAsuntosDesignaciones(HttpServletRequest request, @RequestBody AsuntosJusticiableItem asuntosJusticiableItem) {
		AsuntosJusticiableDTO response = busquedaAsuntosService.searchClaveAsuntosDesignaciones(request, asuntosJusticiableItem);
		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping("/busquedaClaveAsuntosSOJ")
	public ResponseEntity<AsuntosJusticiableDTO> searchClaveAsuntosSOJ(HttpServletRequest request, @RequestBody AsuntosJusticiableItem asuntosJusticiableItem) {
		AsuntosJusticiableDTO response = busquedaAsuntosService.searchClaveAsuntosSOJ(request, asuntosJusticiableItem);
		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}

	// copiar EJG a un SOJ
	@RequestMapping(value = "/copyEjg2Soj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> copyEjg2Soj(@RequestBody List<String> datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = busquedaAsuntosService.copyEjg2Soj(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// copiar EJG a una asistencia
	@RequestMapping(value = "/copyEjg2Asis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> copyEjg2Asis(@RequestBody List<String> datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = busquedaAsuntosService.copyEjg2Asis(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// copiar EJG a una designacion
	@RequestMapping(value = "/copyEjg2Designa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> copyEjg2Designa(@RequestBody List<String> datos, HttpServletRequest request) throws Exception {
		InsertResponseDTO response = busquedaAsuntosService.copyEjg2Designa(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// copiar la designacion a un SOJ
	@RequestMapping(value = "/copyDesigna2Soj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> copyDesigna2Soj(@RequestBody List<String> datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = busquedaAsuntosService.copyDesigna2Soj(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// copiar la designacion a una asistencia
	@RequestMapping(value = "/copyDesigna2Asis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> copyDesigna2Asis(@RequestBody List<String> datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO response = busquedaAsuntosService.copyDesigna2Asis(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// copiar la designacion a un EJG
	@RequestMapping(value = "/copyDesigna2Ejg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> copyDesigna2Ejg(@RequestBody List<String> datos, HttpServletRequest request) throws Exception {
		InsertResponseDTO response = busquedaAsuntosService.copyDesigna2Ejg(datos, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}