package org.itcgae.siga.scs.controllers.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
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
	ResponseEntity<BaremosRequestDTO> searchBaremosGuardia(@RequestBody BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request) {
		BaremosRequestDTO response = baremosGuardiaServices.searchBaremosGuardia(baremosGuardiaItem, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<BaremosRequestDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaremosRequestDTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/getGuardiasByConf",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaremosGuardiaDTO> getGuardiasByConf(HttpServletRequest request) {
		BaremosGuardiaDTO response = baremosGuardiaServices.getGuardiasByConf(request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/getTurnoForGuardia",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTurnoForGuardia(HttpServletRequest request) {
		ComboDTO response = baremosGuardiaServices.getTurnoForGuardia(request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<ComboDTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/saveBaremo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaremosGuardiaDTO> insertBaremosGuardia(@RequestBody List<List<BaremosGuardiaItem>> baremosGuardiaItem, HttpServletRequest request) {
		BaremosGuardiaDTO response = baremosGuardiaServices.saveBaremo(baremosGuardiaItem, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.FORBIDDEN);
        }
		
	}
	
	@RequestMapping(value = "/getBaremo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaremosGuardiaDTO> getBaremo(@RequestBody BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request) {
		BaremosGuardiaDTO response = baremosGuardiaServices.getBaremo(baremosGuardiaItem, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.FORBIDDEN);
        }
	}

	@RequestMapping(value = "/getTurnoGuarConf",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaremosGuardiaDTO> getTurnoGuarConf(@RequestBody String[] configuracion,HttpServletRequest request) {
		BaremosGuardiaDTO response = baremosGuardiaServices.getTurnoGuarConf(configuracion,request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<BaremosGuardiaDTO>(response, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/baremosFichaGuardia",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaremosRequestDTO> searchBaremosFichaGuardia(HttpServletRequest request, @RequestParam String idGuardia) {
		BaremosRequestDTO response = baremosGuardiaServices.searchBaremosFichaGuardia(idGuardia, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<BaremosRequestDTO>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<BaremosRequestDTO>(response, HttpStatus.FORBIDDEN);
		}
	}
	
}
