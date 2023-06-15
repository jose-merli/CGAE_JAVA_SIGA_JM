package org.itcgae.siga.scs.controllers.oficio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
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
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
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
@RequestMapping(value = "/oficio")
public class CargasMasivasOficioController {

	@Autowired
	private IGestionCargasMasivasOficio iGestionCargasMasivasOficio;
	
	@RequestMapping(value = "/cargasMasivasOficio/descargarModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> descargarModelo(HttpServletRequest request,@RequestBody String[] turnos) throws IOException, EncryptedDocumentException, InvalidFormatException {
		ResponseEntity<InputStreamResource> response = iGestionCargasMasivasOficio.descargarModelo(request, turnos[0], turnos[1], turnos[2]);
		return response;
	}
	
	@RequestMapping(value = "/cargasMasivasOficio/uploadFileIT", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> uploadFileIT(MultipartHttpServletRequest request) throws Exception{
		DeleteResponseDTO response = iGestionCargasMasivasOficio.uploadFileIT(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/cargasMasivasOficio/uploadFileBT", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DeleteResponseDTO> uploadFileBT(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		DeleteResponseDTO response = iGestionCargasMasivasOficio.uploadFileBT(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/cargasMasivasOficio/download", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<InputStreamResource> downloadLogFile(@RequestBody CargaMasivaItem cargaMasivaItem, HttpServletRequest request) throws SigaExceptions {
		ResponseEntity<InputStreamResource> response = iGestionCargasMasivasOficio.downloadLogFile(cargaMasivaItem, request);
		return response;
	}
}
