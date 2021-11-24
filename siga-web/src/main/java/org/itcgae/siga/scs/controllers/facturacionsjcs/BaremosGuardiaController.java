package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.scs.services.facturacionsjcs.IBaremosGuardiaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baremosGuardia")

public class BaremosGuardiaController {

	@Autowired
	private IBaremosGuardiaServices baremosGuardiaServices;

	@GetMapping("/buscar")
	ResponseEntity<BaremosGuardiaDTO> buscarCartasfacturacion(
			@RequestParam("idTurno") Integer idTurno,
			@RequestParam("idGuardia") Integer idGuardia,
			HttpServletRequest request){
		BaremosGuardiaDTO response = baremosGuardiaServices.searchBaremosGuardia(idTurno,idGuardia,request);
		return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.OK);
	}
	
}
