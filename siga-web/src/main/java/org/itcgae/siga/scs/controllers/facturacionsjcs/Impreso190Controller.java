package org.itcgae.siga.scs.controllers.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.scs.services.facturacionsjcs.IImpreso190Service;
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
public class Impreso190Controller {

	@Autowired
	private IImpreso190Service iImpreso190Service;
	
	
	@RequestMapping(value="/facturacionsjcs/impreso190generar", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Impreso190DTO> impreso190generar(@RequestBody Impreso190Item impreso190Item, HttpServletRequest request) throws Exception{
		Impreso190DTO response = iImpreso190Service.impreso190generar(impreso190Item, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/facturacionsjcs/impreso190descargar",method = RequestMethod.POST)
	ResponseEntity<InputStreamResource> impreso190descargar(@RequestBody List<Impreso190Item> impreso190Item, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = iImpreso190Service.impreso190descargar(impreso190Item, request);
		return response;
	}
	
	@RequestMapping(value = "/facturacionsjcs/searchImpreso190",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Impreso190DTO> searchImpreso190(@RequestBody String[]  anio, HttpServletRequest request) throws Exception {
		Impreso190DTO response = iImpreso190Service.searchImpreso190(anio, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/facturacionsjcs/deleteImpreso190",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Impreso190DTO> deleteImpreso190(@RequestBody List<Impreso190Item> impreso190Item, HttpServletRequest request) throws Exception {
		Impreso190DTO response = iImpreso190Service.deleteImpreso190(impreso190Item, request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/facturacionsjcs/searchConfImpreso190",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Impreso190DTO> getConfImpreso190(HttpServletRequest request) throws Exception {
		Impreso190DTO response = iImpreso190Service.getConfImpreso190(request);
		if (response.getError().getCode() == 200) {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<Impreso190DTO>(response, HttpStatus.FORBIDDEN);
        }
	}
	
	@RequestMapping(value = "/facturacionsjcs/getComboAnioImpreso190",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboAnio(HttpServletRequest request) throws Exception {
		ComboDTO response = iImpreso190Service.getComboAnio(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
}
