package org.itcgae.siga.cen.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
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
public class CargasMasivasGFController {

	@Autowired
	ICargasMasivasGFService cargasMasivasGFService;

	@Autowired
	SigaConstants sigaConstants;

	@RequestMapping(value = "cargasMasivas/descargarEtiquetas", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> generateExcelEtiquetas(HttpServletRequest request) throws SigaExceptions {
		ResponseEntity<InputStreamResource> response = cargasMasivasGFService.generateExcelEtiquetas();
		return response;
	}
	
	@RequestMapping(value = "cargasMasivas/searchEtiquetas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CargaMasivaDTO> searchEtiquetas(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request) {
		CargaMasivaDTO response = cargasMasivasGFService.searchEtiquetas(cargaMasivaItem, request);
		return new ResponseEntity<CargaMasivaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "cargasMasivasEtiquetas/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = cargasMasivasGFService.uploadFileExcel(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "cargasMasivas/downloadOriginalFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadOriginalFile(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request,
			HttpServletResponse response) throws SigaExceptions {
		ResponseEntity<InputStreamResource> res = cargasMasivasGFService.downloadOriginalFile(cargaMasivaItem, request);
		return res;
	}
	
	@RequestMapping(value = "cargasMasivas/downloadLogFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadLogFile(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request,
			HttpServletResponse response) throws SigaExceptions {
		ResponseEntity<InputStreamResource> res = cargasMasivasGFService.downloadLogFile(cargaMasivaItem, request);
		return res;
	}

	
}
