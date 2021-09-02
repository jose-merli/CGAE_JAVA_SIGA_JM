package org.itcgae.siga.scs.controllers.acta;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;

import org.itcgae.siga.scs.services.acta.IBusquedaActa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/acta")
public class ActaController {
	

	@Autowired
	private IBusquedaActa actas;

	@RequestMapping(value = "/filtros-acta/busquedaActas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ActasDTO> busquedaActas(@RequestBody ActasItem actasItem, HttpServletRequest request) {
		ActasDTO response = actas.busquedaActas(actasItem, request);
		return new ResponseEntity<ActasDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/borrarActas", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarActas(@RequestBody ActasItem actasItem, HttpServletRequest request) {
		DeleteResponseDTO response = actas.borrarActas(actasItem, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/comboSufijoActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboSufijoActa(HttpServletRequest request) {
		ComboDTO response = actas.comboSufijoActa(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/guardarActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> guardarActa(@RequestBody ActasItem actasItem, HttpServletRequest request) {
		InsertResponseDTO response = actas.guardarActa(actasItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/anadirEJGPendientesCAJG", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> anadirEJGPendientesCAJG(@RequestBody ActasItem actasItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = actas.anadirEJGPendientesCAJG(actasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/abrirActa", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> abrirActa(@RequestBody ActasItem actasItem, HttpServletRequest request) {
		UpdateResponseDTO response = actas.abrirActa(actasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/cerrarActa", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cerrarActa(@RequestBody ActasItem actasItem, HttpServletRequest request) {
		UpdateResponseDTO response = actas.cerrarActa(actasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

}
