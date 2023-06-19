package org.itcgae.siga.cen.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
//import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.cen.services.IFichaDatosColegialesService;
import org.itcgae.siga.commons.constants.SigaConstants;
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
public class FichaDatosColegialesController {
	
	
	@Autowired
	private IFichaDatosColegialesService fichaDatosColegiales;
	
	@RequestMapping(value = "/fichaDatosColegiales/tratamiento",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelPerson(HttpServletRequest request) {
		ComboDTO response = fichaDatosColegiales.getTratamiento(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/tipoSeguro",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeInsurances(HttpServletRequest request) {
		ComboDTO response = fichaDatosColegiales.getTypeInsurances(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> searchColegialesData(@RequestParam("numPagina") int numPagina, @RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		ColegiadoDTO response = fichaDatosColegiales.datosColegialesSearch(numPagina, colegiadoItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/cuentaContableSJCSSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> searchCuentaContableSJCS(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		StringDTO response = fichaDatosColegiales.getCuentaContableSJCS(colegiadoItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesSearchActual", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> searchColegialesDataActuak(@RequestParam("numPagina") int numPagina, @RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		ColegiadoDTO response = fichaDatosColegiales.datosColegialesSearchActual(numPagina, colegiadoItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesSearchHistor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> datosColegialesSearchHistor(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		ColegiadoDTO response = fichaDatosColegiales.datosColegialesSearchHistor(colegiadoItem, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/sendMailsOtherCentres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoDTO> sendMailsOtherCentres(@RequestBody String[] centresToSendMails, HttpServletRequest request) { 
		ColegiadoDTO response = fichaDatosColegiales.sendMailsOtherCentres(centresToSendMails, request);
		return new ResponseEntity<ColegiadoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> searchColegialesData(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosColegiales.datosColegialesUpdate(colegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesInsertEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> datosColegialesInsertEstado(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		InsertResponseDTO response = fichaDatosColegiales.datosColegialesInsertEstado(colegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
			else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesUpdateEstados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> datosColegialesUpdateEstados(@RequestBody List<ColegiadoItem> listColegiadoItem, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosColegiales.datosColegialesUpdateEstados(listColegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesDeleteEstado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> datosColegialesDeleteEstado(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosColegiales.datosColegialesDeleteEstado(colegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/getNumColegiado",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> getNumColegiado(HttpServletRequest request) {
		StringDTO response = fichaDatosColegiales.getNumColegiado(request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/searchTurnosGuardias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<StringDTO> searchTurnosGuardias(@RequestBody ColegiadoItem colegiadoItem, HttpServletRequest request) { 
		StringDTO response = fichaDatosColegiales.getTurnosGuardias(colegiadoItem, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaDatosColegiales/datosColegialesUpdateMasivo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> datosColegialesUpdateMasivo(@RequestBody ColegiadoItemDTO listColegiadoItem, HttpServletRequest request) { 
		UpdateResponseDTO response = fichaDatosColegiales.datosColegialesUpdateMasivo(listColegiadoItem, request);
		if(response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
