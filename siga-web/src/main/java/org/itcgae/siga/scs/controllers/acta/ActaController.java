package org.itcgae.siga.scs.controllers.acta;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.ScsActacomision;
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


	@RequestMapping(value = "/filtros-acta/borrarActas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> borrarActas(@RequestBody List<ActasItem> actasItem, HttpServletRequest request)
			throws SigaExceptions {
		DeleteResponseDTO response = actas.borrarActas(actasItem, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/comboSufijoActa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboSufijoActa(HttpServletRequest request) throws SigaExceptions {
		ComboDTO response = actas.comboSufijoActa(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/getNumActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> getNumActa(@RequestBody ActasItem actasItem, HttpServletRequest request)
			throws SigaExceptions {
		String response = actas.getNumActa(actasItem, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-acta/getNumActa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> getNumActa( HttpServletRequest request)
			throws SigaExceptions {
		String response = actas.getNumActa(request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/filtros-acta/getActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ScsActacomision> getActa(@RequestBody ActasItem actasItem, HttpServletRequest request)
			throws SigaExceptions {
		ScsActacomision response = actas.getActa(actasItem, request);
		return new ResponseEntity<ScsActacomision>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/guardarActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> guardarActa(@RequestBody ActasItem actasItem, HttpServletRequest request)
			throws SigaExceptions {
		InsertResponseDTO response = actas.guardarActa(actasItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/anadirEJGPendientesCAJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> anadirEJGPendientesCAJG(@RequestBody ActasItem actasItem,
			HttpServletRequest request) throws SigaExceptions {
		UpdateResponseDTO response = actas.anadirEJGPendientesCAJG(actasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/abrirActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> abrirActa(@RequestBody ActasItem actasItem, HttpServletRequest request)
			throws SigaExceptions {
		UpdateResponseDTO response = actas.abrirActa(actasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/cerrarActa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cerrarActa(@RequestBody ActasItem actasItem, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO response = actas.cerrarActa(actasItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtros-acta/busquedaActas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ActasDTO> busquedaActas(@RequestBody ActasItem actasItem, HttpServletRequest request)
			throws SigaExceptions {
		ActasDTO response = actas.busquedaActas(actasItem, request);
		return new ResponseEntity<ActasDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/filtros-acta/getAbreviatura", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CenInstitucion> getAbreviatura(HttpServletRequest request)
			throws SigaExceptions {
		CenInstitucion response = actas.getAbreviatura(request);
		return new ResponseEntity<CenInstitucion>(response, HttpStatus.OK);
	}
}
