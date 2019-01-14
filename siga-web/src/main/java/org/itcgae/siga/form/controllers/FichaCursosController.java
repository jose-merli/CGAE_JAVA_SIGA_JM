package org.itcgae.siga.form.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.form.CargaMasivaInscripcionesDTO;
import org.itcgae.siga.DTOs.form.CertificadoCursoDTO;
import org.itcgae.siga.DTOs.form.CertificadoCursoItem;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.form.PreciosCursoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.age.service.IFichaEventosService;
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
	
	@Autowired
	private IFichaEventosService fichaEventosService;
	
			
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
	ResponseEntity<UpdateResponseDTO> uploadFile(@RequestParam("idCurso") int idCurso, MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = fichaCursosService.uploadFileExcel(idCurso, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "fichaCursos/getMassiveLoadInscriptions",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CargaMasivaInscripcionesDTO> getMassiveLoadInscriptions(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		CargaMasivaInscripcionesDTO response = fichaCursosService.getMassiveLoadInscriptions(request, idCurso);
		return new ResponseEntity<CargaMasivaInscripcionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/deleteInscriptionsCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteInscriptionsCourse(@RequestBody CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.deleteInscriptionsCourse(cargaMasivaInscripcionesDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/autovalidateInscriptionsCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> autovalidateInscriptionsCourse(@RequestBody CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.autovalidateInscriptionsCourse(cargaMasivaInscripcionesDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getTopicsCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTopicsCourse(HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getTopicsCourse(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getTopicsSpecificCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getTopicsSpecificCourse(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getTopicsSpecificCourse(request, idCurso);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/cancelCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cancelCourse(@RequestBody CursoDTO cursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.cancelCourse(cursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/finishCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> finishCourse(@RequestBody CursoDTO cursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.finishCourse(cursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getPricesCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PreciosCursoDTO> getPricesCourse(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		PreciosCursoDTO response = fichaCursosService.getPricesCourse(request, idCurso);
		return new ResponseEntity<PreciosCursoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getQualificationsCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getQualificationsCourse(HttpServletRequest request) {
		ComboDTO response = fichaCursosService.getQualificationsCourse(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getTypesCertificatesCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CertificadoCursoDTO> getTypesCertificatesCourse(HttpServletRequest request) {
		CertificadoCursoDTO response = fichaCursosService.getTypesCertificatesCourse(request);
		return new ResponseEntity<CertificadoCursoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/getCertificatesCourse",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CertificadoCursoDTO> getCertificatesCourse(@RequestParam("idCurso") String idCurso, HttpServletRequest request) {
		CertificadoCursoDTO response = fichaCursosService.getCertificatesCourse(request, idCurso);
		return new ResponseEntity<CertificadoCursoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/saveCertificateCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> saveCertificateCourse(@RequestBody CertificadoCursoItem certificadoCursoItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaCursosService.saveCertificateCourse(certificadoCursoItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/updateCertificatesCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateCertificatesCourse(@RequestBody CertificadoCursoDTO certificadoCursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.updateCertificatesCourse(certificadoCursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/deleteCertificatesCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> deleteCertificatesCourse(@RequestBody CertificadoCursoDTO certificadoCursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.deleteCertificatesCourse(certificadoCursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/duplicateSessionsCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> duplicateSessionCourse(@RequestBody EventoItem eventoItem, HttpServletRequest request) {
		InsertResponseDTO response = fichaEventosService.saveEventCalendar(eventoItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/cancelSessionsCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> cancelSessionsCourse(@RequestBody EventoDTO eventoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaEventosService.deleteEventCalendar(eventoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/releaseCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> releaseCourse(@RequestBody CursoDTO cursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.releaseCourse(cursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaCursos/announceCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> announceCourse(@RequestBody CursoDTO cursoDTO, HttpServletRequest request) {
		UpdateResponseDTO response = fichaCursosService.announceCourse(cursoDTO, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
}
