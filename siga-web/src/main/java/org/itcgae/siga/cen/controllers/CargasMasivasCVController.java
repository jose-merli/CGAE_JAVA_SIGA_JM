package org.itcgae.siga.cen.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.cen.services.ICargasMasivasCVService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class CargasMasivasCVController {

	@Autowired
	private ICargasMasivasCVService iCargasMasivasCVService;
	
	@RequestMapping(value = "cargaMasivaDatosCurriculares/generateExcelCV", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> generateExcelCV(HttpServletRequest request) throws SigaExceptions {
		ResponseEntity<InputStreamResource> response = iCargasMasivasCVService.generateExcelCV();
		return response;
	}

	@RequestMapping(value = "cargaMasivaDatosCurriculares/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = iCargasMasivasCVService.uploadFile(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "cargaMasivaDatosCurriculares/searchCV",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CargaMasivaDTO> searchCV(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request) {
		CargaMasivaDTO response = iCargasMasivasCVService.searchCV(cargaMasivaItem, request);
		return new ResponseEntity<CargaMasivaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "cargaMasivaDatosCurriculares/downloadOriginalFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadOriginalFile(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request) throws SigaExceptions {
		ResponseEntity<InputStreamResource> response = iCargasMasivasCVService.downloadOriginalFile(cargaMasivaItem, request);
		return response;
	}
	
	@RequestMapping(value = "cargaMasivaDatosCurriculares/downloadLogFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadLogFile(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request) throws SigaExceptions {
		ResponseEntity<InputStreamResource> response = iCargasMasivasCVService.downloadLogFile(cargaMasivaItem, request);
		return response;
	}
}
