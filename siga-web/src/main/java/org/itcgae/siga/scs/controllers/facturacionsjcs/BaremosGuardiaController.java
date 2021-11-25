package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.scs.services.facturacionsjcs.IBaremosGuardiaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baremosGuardia")

public class BaremosGuardiaController {

	@Autowired
	private IBaremosGuardiaServices baremosGuardiaServices;
	
	@RequestMapping(value = "/buscar",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaremosRequestDTO> searchImpreso190(@RequestBody BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request) {
		BaremosRequestDTO response = baremosGuardiaServices.searchBaremosGuardia(baremosGuardiaItem, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<BaremosRequestDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaremosRequestDTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
}
