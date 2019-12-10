package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.scs.services.componentesGenerales.BuscadorProcuradoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/component")
public class BuscadorProcuradoresController {
	
	
	@Autowired
	private BuscadorProcuradoresService buscadorProcuradoresService;
	

	@PostMapping("/buscadorProcuradores/searchProcuradores")
	public ResponseEntity<ProcuradorDTO> searchProcuradores(HttpServletRequest request, @RequestBody ProcuradorItem procuradorItem) {
		ProcuradorDTO response = buscadorProcuradoresService.searchProcuradores(request, procuradorItem);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}
	
}
