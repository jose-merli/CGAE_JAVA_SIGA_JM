package org.itcgae.siga.scs.controllers.guardia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.FileDataDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioDTO;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.guardia.CargasMasivasGuardiaService;
import org.itcgae.siga.scs.services.oficio.IGestionCargasMasivasOficio;
import org.itcgae.siga.scs.services.oficio.IGestionInscripcionesService;
import org.itcgae.siga.scs.services.oficio.IGestionTurnosService;
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
@RequestMapping(value = "/guardia")
public class CargasMasivasGuardiaController {

	@Autowired
	private CargasMasivasGuardiaService cargasMasivasGuardiaService;
	
	@RequestMapping(value = "/cargasMasivasGuardia/descargarModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> descargarModelo(HttpServletRequest request,@RequestBody String[] turnos) throws IOException, EncryptedDocumentException, InvalidFormatException {
		ResponseEntity<InputStreamResource> response = cargasMasivasGuardiaService.descargarModelo(request, turnos[0], turnos[1], turnos[2]);
		return response;
	}
	
	@RequestMapping(value = "/cargasMasivasGuardia/uploadFileI", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> uploadFileI( @RequestParam("fechaSolicitud") String fechaSolicitud, MultipartHttpServletRequest request) throws IllegalStateException, IOException, BusinessException, ParseException{
		DeleteResponseDTO response = cargasMasivasGuardiaService.uploadFileI(fechaSolicitud, request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/cargasMasivasGuardia/uploadFileGC", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> uploadFileGC(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		DeleteResponseDTO response = cargasMasivasGuardiaService.uploadFileGC(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/cargasMasivasGuardia/uploadFileC", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> uploadFileC(@RequestParam("fechaDesde") String fechaDesde, @RequestParam("fechaHasta") String fechaHasta, @RequestParam("observaciones") String observaciones, MultipartHttpServletRequest request) throws IllegalStateException, IOException, Exception{
		DeleteResponseDTO response;
		try {
			response = cargasMasivasGuardiaService.uploadFileC(request, fechaDesde, fechaHasta, observaciones);
			if (response != null && response.getStatus().equals(SigaConstants.OK))
				return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
			else
				return null;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value = "/cargasMasivasGuardia/download", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<InputStreamResource> downloadLogFile(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request) throws SigaExceptions {
		ResponseEntity<InputStreamResource> response = cargasMasivasGuardiaService.downloadLogFile(cargaMasivaItem, request);
		return response;
	}
}
