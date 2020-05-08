package org.itcgae.siga.form.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.FicheroDTO;
import org.itcgae.siga.DTOs.cen.MandatosDownloadDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.form.services.IFichaInscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class FichaInscripcionController {

	@Autowired
	private IFichaInscripcionService fichaInscripcionService;
	
	@RequestMapping(value = "fichaInscripcion/searchCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CursoItem> searchCourse(@RequestBody String idCurso, HttpServletRequest request) {
		CursoItem response = fichaInscripcionService.searchCourse(idCurso, request);
		return new ResponseEntity<CursoItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/saveInscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> saveInscripcion(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) {
		ComboDTO response = fichaInscripcionService.saveInscripcion(inscripcionItem, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/updateInscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateInscripcion(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) {
		UpdateResponseDTO response = fichaInscripcionService.updateInscripcion(inscripcionItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/guardarPersona",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> guardarPersona(@RequestBody AsociarPersonaDTO asociarPersona, HttpServletRequest request) { 
		InsertResponseDTO response = fichaInscripcionService.guardarPersona(asociarPersona, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/generarSolicitudCertificados",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> generarSolicitudCertificados(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) { 
		InsertResponseDTO response = fichaInscripcionService.generarSolicitudCertificados(inscripcionItem, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/checkMinimaAsistencia",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> checkMinimaAsitencia(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) { 
		String response = fichaInscripcionService.compruebaMinimaAsistencia(inscripcionItem, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/getPaymentMode",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPaymentMode(HttpServletRequest request) {
		ComboDTO response = fichaInscripcionService.getPaymentMode(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaInscripcion/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = fichaInscripcionService.uploadFile(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	@RequestMapping(value = "fichaInscripcion/downloadFile", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document" })
    public ResponseEntity<byte[]> downloadFile(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request, HttpServletResponse response) {
	 	FicheroDTO ficheroDTO = fichaInscripcionService.downloadFile(inscripcionItem,request,response);
		HttpHeaders respuestaHeader = new HttpHeaders();
        respuestaHeader.add("content-disposition", "attachment; filename= " + ficheroDTO.getFileName()); 
		return new ResponseEntity<byte[]>(ficheroDTO.getFile(),respuestaHeader, HttpStatus.OK);
    }
 
 
@RequestMapping(value = "fichaInscripcion/fileDownloadInformation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboItem> fileDownloadInformation(@RequestBody InscripcionItem inscripcionItem, HttpServletRequest request) { 
	ComboItem response = fichaInscripcionService.fileDownloadInformation(inscripcionItem, request);
	return new ResponseEntity<ComboItem>(response, HttpStatus.OK);
}
}
