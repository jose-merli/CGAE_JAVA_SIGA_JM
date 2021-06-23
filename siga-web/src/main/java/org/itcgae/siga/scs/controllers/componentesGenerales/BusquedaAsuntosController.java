package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaAsuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}