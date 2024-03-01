package org.itcgae.siga.scs.controllers.justiciables;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.scs.JusticiableDTO;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.DTOs.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.scs.ScsUnidadfamiliarejgDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.scs.services.justiciables.IBusquedaJusticiablesService;
import org.itcgae.siga.scs.services.justiciables.IGestionJusticiableService;
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
@RequestMapping(value = "/justiciables")
public class JusticiablesController {

	@Autowired
	IBusquedaJusticiablesService busquedaJusticiablesService;

	@Autowired
	IGestionJusticiableService gestionJusticiableService;

	@RequestMapping(value = "/busquedaJusticiables/comboRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboRoles(HttpServletRequest request) {
		ComboDTO response = busquedaJusticiablesService.getComboRoles(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/getTelefonos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableTelefonoDTO> getTelefonos(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {
		JusticiableTelefonoDTO response = gestionJusticiableService.getTelefonos(justiciableItem, request);
		return new ResponseEntity<JusticiableTelefonoDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/busquedaJusticiables/searchJusticiables", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableBusquedaDTO> searchJusticiables(
			@RequestBody JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request) {
		JusticiableBusquedaDTO response = busquedaJusticiablesService.searchJusticiables(justiciableBusquedaItem,
				request);
		return new ResponseEntity<JusticiableBusquedaDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboMinusvalias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getMinusvalias(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getMinusvalias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboGruposLaborales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getGrupoLaboral(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getGruposLaborales(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboParentesco", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getParentesco(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getParentesco(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboTiposIngresos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTipoIngresos(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getTiposIngresos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboProfesiones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProfesiones(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getProfesiones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboTipoVias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoVias(HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getTipoVias(request, null);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionJusticiables/comboTipoVias2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getComboTipoVias(HttpServletRequest request, @RequestParam("idTipoViaJusticiable") String idTipoViaJusticiable) {
		ComboDTO response = gestionJusticiableService.getTipoVias(request, idTipoViaJusticiable);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/searchJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableDTO> searchJusticiable(@RequestBody JusticiableBusquedaItem justiciableBusquedaItem,
			HttpServletRequest request) {
		JusticiableDTO response = gestionJusticiableService.searchJusticiable(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/comboPoblacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPoblacion(@RequestParam("idPoblacion") String idPoblacion, HttpServletRequest request) {
		ComboDTO response = gestionJusticiableService.getPoblacion(idPoblacion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/createJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createJusticiable(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {

		InsertResponseDTO response = gestionJusticiableService.createJusticiable(justiciableItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/gestionJusticiables/updateJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateJusticiable(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.updateJusticiable(justiciableItem, true, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}
	
	@RequestMapping(value = "gestionJusticiables/updateJusticiable/datos/personales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateJusticiableDatosPersonales(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.updateJusticiableDatosPersonales(justiciableItem, true, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/gestionJusticiables/updateUnidadFamiliar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateUnidadFamiliar(@RequestBody UnidadFamiliarEJGItem unidadFamiliarItem,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.updateUnidadFamiliar(unidadFamiliarItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/gestionJusticiables/getSolicitante", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ScsUnidadfamiliarejgDTO> getSolicitante(@RequestBody EjgItem unidadFamiliarItem,
			HttpServletRequest request) {

		ScsUnidadfamiliarejgDTO response = gestionJusticiableService.getSolicitante(unidadFamiliarItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<ScsUnidadfamiliarejgDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ScsUnidadfamiliarejgDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/gestionJusticiables/updateDatosSolicitudJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosSolicitudJusticiable(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.updateJusticiable(justiciableItem, false, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/gestionJusticiables/searchAsuntosJusticiable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AsuntosJusticiableDTO> searchAsuntosJusticiable(@RequestBody String idPersonaJusticiable,
			HttpServletRequest request) {
		AsuntosJusticiableDTO response = gestionJusticiableService.searchAsuntosJusticiable(idPersonaJusticiable,
				request, "search");
		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/searchAsuntosConClave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	void searchAsuntosConClave(@RequestBody AsuntosJusticiableDTO asuntosJusticiableDTO, HttpServletRequest request) {
//		AsuntosJusticiableDTO response = gestionJusticiableService.searchAsuntos(idPersonaJusticiable, request);
//		return new ResponseEntity<AsuntosJusticiableDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/getJusticiableByNif", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableDTO> getJusticiableByNif(@RequestBody JusticiableBusquedaItem justiciableBusquedaItem,
			HttpServletRequest request) {
		JusticiableDTO response = gestionJusticiableService.getJusticiableByNif(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/getJusticiableByIdPersona", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JusticiableDTO> getJusticiableByIdPersona(
			@RequestBody JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request) {
		JusticiableDTO response = gestionJusticiableService.getJusticiableByIdPersona(justiciableBusquedaItem, request);
		return new ResponseEntity<JusticiableDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/gestionJusticiables/associateRepresentante", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> associateRepresentante(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.associateRepresentante(justiciableItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/gestionJusticiables/disassociateRepresentante", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> disassociateRepresentante(@RequestBody JusticiableItem justiciableItem,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.disassociateRepresentante(justiciableItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);

	}

	// Asociar designacion
	@RequestMapping(value = "/gestionJusticiableService/asociarDesignacion", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarDesignacion(@RequestBody ScsDefendidosdesigna datos,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.asociarDesignacion(datos, request);

		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Asociar EJG
	@RequestMapping(value = "/gestionJusticiableService/asociarEJG", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarEJG(@RequestBody List<String> itemEjg, HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.asociarEJG(itemEjg, request);

		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Asociar Asistencia
	@RequestMapping(value = "/gestionJusticiableService/asociarAsistencia", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarAsistencia(@RequestBody List<String> itemAsistencia,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.asociarAsistencia(itemAsistencia, request);

		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Asociar SOJ
	@RequestMapping(value = "/gestionJusticiableService/asociarSOJ", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarSOJ(@RequestBody List<String> itemSOJ,
			HttpServletRequest request) {

		UpdateResponseDTO response = gestionJusticiableService.asociarSOJ(itemSOJ, request);

		if (response.getStatus().equals("OK"))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
