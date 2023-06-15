package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ZonasDTO;
import org.itcgae.siga.DTOs.scs.ZonasItem;
import org.itcgae.siga.scs.services.maestros.IFichaZonasService;
import org.itcgae.siga.scs.services.maestros.IGestionZonasService;
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
@RequestMapping(value = "/maestros")
public class ZonasSubzonasController {

	@Autowired
	private IGestionZonasService gestionZonasService;
	
	@Autowired
	private IFichaZonasService fichaZonasService;
	
		
	@RequestMapping(value = "/gestionZonas/searchZones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ZonasDTO> searchZones(@RequestBody ZonasItem zonasItem, HttpServletRequest request) {
		ZonasDTO response = gestionZonasService.searchZones(zonasItem, request);
		return new ResponseEntity<ZonasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaZonas/partidosJudiciales",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPartidosJudiciales(HttpServletRequest request) {
		ComboDTO response = fichaZonasService.getPartidoJudicial(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaZonas/searchSubzones",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ZonasDTO> searchSubzones(@RequestParam("idZona") String idZona, String idSubZona, HttpServletRequest request) {
		ZonasDTO response = fichaZonasService.searchSubzones(idZona,idSubZona, request);
		return new ResponseEntity<ZonasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaZonas/createGroupZone",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createGroupZone(@RequestBody ZonasItem zonasItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaZonasService.createGroupZone(zonasItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/fichaZonas/searchGroupZone",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ZonasItem> searchGroupZone(@RequestParam("idZona") String idZona, HttpServletRequest request) {
		ZonasItem response = fichaZonasService.searchGroupZone(idZona, request);
		return new ResponseEntity<ZonasItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fichaZonas/updateGroupZone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateGroupZone(@RequestBody ZonasItem zonaItem, HttpServletRequest request) {

		UpdateResponseDTO response = fichaZonasService.updateGroupZone(zonaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/fichaZonas/createZone",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createZone(@RequestBody ZonasItem zonasItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaZonasService.createZone(zonasItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/fichaZonas/updateZones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateZones(@RequestBody ZonasDTO zonasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = fichaZonasService.updateZones(zonasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/fichaZonas/deleteZones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteZones(@RequestBody ZonasDTO zonasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = fichaZonasService.deleteZones(zonasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/fichaZonas/deleteGroupZones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteGroupZones(@RequestBody ZonasDTO zonasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionZonasService.deleteGroupZones(zonasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/fichaZonas/activateGroupZones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateGroupZones(@RequestBody ZonasDTO zonasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionZonasService.activateGroupZones(zonasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
