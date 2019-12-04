package org.itcgae.siga.scs.controllers.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ComisariaDTO;
import org.itcgae.siga.DTO.scs.ComisariaItem;
import org.itcgae.siga.DTO.scs.FacturacionDTO;
import org.itcgae.siga.DTO.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTO.scs.FacturacionItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacturacionController {

	@Autowired
	private IFacturacionServices facturacionServices;

	@RequestMapping(value="/facturacionsjcs/buscarfacturaciones", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDTO> buscarFacturaciones(@RequestBody FacturacionItem facturacionItem, HttpServletRequest request){
		FacturacionDTO response = facturacionServices.buscarFacturaciones(facturacionItem, request);
		return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/facturacionsjcs/eliminarFacturacion", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacturacionDeleteDTO> eliminarFacturaciones(@RequestBody int idFactura, HttpServletRequest request){
		FacturacionDeleteDTO response = facturacionServices.eliminarFacturaciones(idFactura, request);
		if (response.getError().getCode() == 200) {
			return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
