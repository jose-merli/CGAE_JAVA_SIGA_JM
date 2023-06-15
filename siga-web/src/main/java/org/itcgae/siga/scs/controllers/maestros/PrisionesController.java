package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.PrisionDTO;
import org.itcgae.siga.DTOs.scs.PrisionItem;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.scs.services.maestros.IBusquedaPrisionesService;
import org.itcgae.siga.scs.services.maestros.IGestionPrisionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/maestros")
public class PrisionesController {

	@Autowired
	private IBusquedaPrisionesService busquedaPrisionesService;
	
	@Autowired
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;
	
	@Autowired
	private ITarjetaDatosIntegrantesService tarjetaDatosIntegrantesService;
	
	@Autowired
	private IGestionPrisionesService gestionPrisionesService;
	
	@RequestMapping(value = "/busquedaPrisiones/searchPrisiones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PrisionDTO> searchCourt(@RequestBody PrisionItem prisionItem, HttpServletRequest request) {
		PrisionDTO response = busquedaPrisionesService.searchPrisiones(prisionItem, request);
		return new ResponseEntity<PrisionDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaPrisiones/provinces", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaPrisiones/population", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPopulation(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request,idProvincia, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaPrisiones/deletePrisiones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deletePrisiones(@RequestBody PrisionDTO prisionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaPrisionesService.deletePrisiones(prisionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/busquedaPrisiones/activatePrisiones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activatePrisiones(@RequestBody PrisionDTO prisionDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaPrisionesService.activatePrisiones(prisionDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionPrisiones/updatePrision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updatePrision(@RequestBody PrisionItem prisionItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionPrisionesService.updatePrision(prisionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionPrisiones/createPrision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createPrision(@RequestBody PrisionItem prisionItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionPrisionesService.createPrision(prisionItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
}
