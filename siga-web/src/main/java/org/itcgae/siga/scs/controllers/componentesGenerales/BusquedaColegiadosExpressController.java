package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.scs.services.componentesGenerales.IBusquedaColegiadosExpressService;
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
public class BusquedaColegiadosExpressController {

	@Autowired
	private IBusquedaColegiadosExpressService busquedaColegiadosExpressService;

	@RequestMapping(value = "/componenteGeneralJG/busquedaColegiado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadoJGDTO> busquedaColegiadosExpress(@RequestParam("colegiadoJGItem") String colegiadoJGItem,
			HttpServletRequest request) {
		ColegiadoJGDTO response = busquedaColegiadosExpressService.busquedaColegiadosExpress(colegiadoJGItem, request);
		return new ResponseEntity<ColegiadoJGDTO>(response, HttpStatus.OK);
	}

	// busquedaColegiadoEJG
	@RequestMapping(value = "/componenteGeneralJG/busquedaColegiadoEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ColegiadosSJCSDTO> busquedaColegiadoEJG(@RequestBody ColegiadosSJCSItem datos,
			HttpServletRequest request) {
		ColegiadosSJCSDTO response = busquedaColegiadosExpressService.busquedaColegiadoEJG(datos, request);
		return new ResponseEntity<ColegiadosSJCSDTO>(response, HttpStatus.OK);
	}

	// combo para los turnos en la busqueda de colegiadoEJG
	@RequestMapping(value = "/componenteGeneralJG/comboTurnos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(String pantalla, String idTurno, HttpServletRequest request) {
		ComboDTO response = busquedaColegiadosExpressService.comboTurnos(pantalla, idTurno, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/componenteGeneralJG/getTipoTurno", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> comboTurnos(String idTurno, HttpServletRequest request) {
		String response = busquedaColegiadosExpressService.getTipoTurnos( idTurno, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}