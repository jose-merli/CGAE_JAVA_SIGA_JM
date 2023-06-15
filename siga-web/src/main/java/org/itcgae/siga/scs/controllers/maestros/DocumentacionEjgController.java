package org.itcgae.siga.scs.controllers.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.scs.services.maestros.IBusquedaDocumentacionEjgService;
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
public class DocumentacionEjgController {
	@Autowired
	private IBusquedaDocumentacionEjgService busquedaDocumentacionEjgService;
	
	
	@RequestMapping(value = "/busquedaDocumentacionEjg/searchDocumento",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentacionEjgDTO> searchDocumento(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		DocumentacionEjgDTO response = busquedaDocumentacionEjgService.searchDocumento(documentacionejgItem, request);
		return new ResponseEntity<DocumentacionEjgDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaDocumentacionEjg/deleteTipoDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteTipoDoc(@RequestBody DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaDocumentacionEjgService.deleteTipoDoc(documentacionejgDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/busquedaDocumentacionEjg/searchDocumentos",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentacionEjgDTO> searchDocumentos(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		DocumentacionEjgDTO response = busquedaDocumentacionEjgService.searchDocumentos(documentacionejgItem, request);
		return new ResponseEntity<DocumentacionEjgDTO>(response, HttpStatus.OK);
	}
	
	//creacion y update de TipoDoc
	@RequestMapping(value = "/gestionDocumentacionEjg/updateTipoDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateTipoDoc(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaDocumentacionEjgService.updateTipoDoc(documentacionejgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@RequestMapping(value = "/gestionDocumentacionEjg/createTipoDoc",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createTipoDoc(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		InsertResponseDTO response = busquedaDocumentacionEjgService.createTipoDoc(documentacionejgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	//Delete Doc
	@RequestMapping(value = "/gestionDocumentacionEjg/deleteDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteDoc(@RequestBody DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaDocumentacionEjgService.deleteDoc(documentacionejgDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
		//creacion y update de Doc
		@RequestMapping(value = "/gestionDocumentacionEjg/updateDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> updateDoc(@RequestBody DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {

			UpdateResponseDTO response = busquedaDocumentacionEjgService.updateDoc(documentacionejgDTO, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		@RequestMapping(value = "/gestionDocumentacionEjg/createDoc",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<InsertResponseDTO> createDoc(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
			InsertResponseDTO response = busquedaDocumentacionEjgService.createDoc(documentacionejgItem, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	/*
	//creacion y update de Documentos
	@RequestMapping(value = "/areasMaterias/createMaterias",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createMaterias(@RequestBody AreasItem areasItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaAreasService.createMaterias(areasItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@RequestMapping(value = "/areasMaterias/updateMaterias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateMaterias(@RequestBody AreasDTO areasDTO, HttpServletRequest request) {

		UpdateResponseDTO response = fichaAreasService.updateMaterias(areasDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	*/
	
	
	/*@RequestMapping(value = "/busquedaDocumentacionEjg/activateCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> judgedActivate(@RequestBody DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaDocumentacionEjgService.deleteTipoDoc(documentacionejgDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	/*
	@RequestMapping(value = "/busquedaDocumentacionEjg/provinces", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getProvinces(HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosIntegrantesService.getProvinces(request);
		return new ResponseEntity<ComboDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaDocumentacionEjg/population", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPopulation(String idProvincia, String dataFilter, HttpServletRequest request) { 
		ComboDTO response = tarjetaDatosDireccionesService.getPoblacion(request,idProvincia, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/busquedaDocumentacionEjg/activateCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> activateJudged(@RequestBody DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {

		UpdateResponseDTO response = busquedaDocumentacionEjgsService.activateCourt(documentacionejgDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionDocumentacionEjg/searchProcess", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcedimientoDTO> searchProcess(HttpServletRequest request) { 
		ProcedimientoDTO response = gestionDocumentacionEjgService.searchProcess(request);
		return new ResponseEntity<ProcedimientoDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionDocumentacionEjg/searchProcCourt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcedimientoDTO> searchProcJudged(@RequestParam("idDocumentacionEjg") String idDocumentacionEjg, HttpServletRequest request) { 
		ProcedimientoDTO response = gestionDocumentacionEjgService.searchProcCourt(idDocumentacionEjg, request);
		return new ResponseEntity<ProcedimientoDTO >(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gestionDocumentacionEjg/updateCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateJudged(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {

		UpdateResponseDTO response = gestionDocumentacionEjgService.updateCourt(documentacionejgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionDocumentacionEjg/createCourt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createCourt(@RequestBody DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {

		InsertResponseDTO response = gestionDocumentacionEjgService.createCourt(documentacionejgItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/gestionDocumentacionEjg/associateProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> associateProcess(@RequestBody ProcedimientoDTO procedimientoDTO, HttpServletRequest request) {

		UpdateResponseDTO response = gestionDocumentacionEjgService.associateProcess(procedimientoDTO, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	*/
}
