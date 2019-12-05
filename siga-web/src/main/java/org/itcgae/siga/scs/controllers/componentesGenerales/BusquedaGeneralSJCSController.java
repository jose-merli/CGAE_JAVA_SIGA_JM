package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaGeneralSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/componenteGeneralJG")
public class BusquedaGeneralSJCSController {
	
	
	@Autowired
	private BusquedaGeneralSJCSService busquedaGeneralSJCSService;
	

	@PostMapping("/busquedaGeneralSJCS")
	public ResponseEntity<ColegiadosSJCSDTO> searchGeneralSJCS(HttpServletRequest request, @RequestBody ColegiadosSJCSItem colegiadosSJCSItem) {
		ColegiadosSJCSDTO response = busquedaGeneralSJCSService.searchColegiadosSJCS(request, colegiadosSJCSItem);
		return new ResponseEntity<ColegiadosSJCSDTO>(response, HttpStatus.OK);
	}
	
}
