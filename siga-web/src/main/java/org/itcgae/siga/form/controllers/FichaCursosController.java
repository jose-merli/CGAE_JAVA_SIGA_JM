package org.itcgae.siga.form.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class FichaCursosController {
	
	@Autowired
	private IFichaCursosService fichaCursosService;
	
			
	@RequestMapping(value = "fichaCursos/getTrainersCourse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FormadorCursoDTO> getTrainersCourse(String idCurso, HttpServletRequest request) {
		FormadorCursoDTO response = fichaCursosService.getTrainersCourse(idCurso, request);
		return new ResponseEntity<FormadorCursoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getRolesTrainers",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getRolesTrainers(HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getRolesTrainers(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getTypeCostTrainers",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTypeCostTrainers(HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getTypeCostTrainers(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/updateTrainersCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateTrainersCourse(@RequestBody FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.updateTrainersCourse(formadorCursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "fichaCursos/saveTrainersCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveTrainersCourse(@RequestBody FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {
		InsertResponseDTO response = fichaCursosService.saveTrainersCourse(formadorCursoDTO, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/deleteTrainersCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteTrainersCourse(@RequestBody FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.deleteTrainersCourse(formadorCursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getSessionsCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EventoDTO> getSessionsCourse(@RequestBody CursoItem cursoItem, HttpServletRequest request) {
		EventoDTO response = fichaCursosService.getSessionsCourse(cursoItem, request);
		return new ResponseEntity<EventoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/saveCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveCourse(@RequestBody CursoItem cursoItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaCursosService.saveCourse(cursoItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/updateCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updatedCourse(@RequestBody CursoItem cursoItem, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.updateCourse(cursoItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/releaseOrAnnounceCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> releaseOrAnnounceCourse(@RequestBody CursoDTO cursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.releaseOrAnnounceCourse(cursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/searchCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CursoItem> searchCourse(@RequestBody String idCurso, HttpServletRequest request) {
		CursoItem response = fichaCursosService.searchCourse(idCurso, request);
		return new ResponseEntity<CursoItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getServicesCourse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getServicesCourse(HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getServicesCourse(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getServicesSpecificCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getServicesSpecificCourse(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getServicesSpecificCourse(request, idCurso);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getCountIncriptions",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InscripcionItem> getCountIncriptions(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		InscripcionItem response = fichaCursosService.getCountIncriptions(request, idCurso);
		return new ResponseEntity<InscripcionItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/downloadTemplateFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadTemplateFile(@RequestBody CursoItem cursoItem, HttpServletRequest request,
			HttpServletResponse response) throws SigaExceptions {
		ResponseEntity<InputStreamResource> res = fichaCursosService.generateExcelInscriptions(cursoItem, request);
		return res;
	}
	
	@RequestMapping(value = "fichaCursos/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = fichaCursosService.uploadFileExcel(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
}
