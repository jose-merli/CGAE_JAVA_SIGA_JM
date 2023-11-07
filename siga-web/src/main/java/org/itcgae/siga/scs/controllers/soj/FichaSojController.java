package org.itcgae.siga.scs.controllers.soj;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionAsistenciaItem;
import org.itcgae.siga.DTOs.scs.DocumentacionSojDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionSojItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.FichaSojDTO;
import org.itcgae.siga.DTOs.scs.FichaSojItem;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.services.soj.ISojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/soj")
public class FichaSojController {

	@Autowired
	private ISojService sojService;

	// Obtener Detalles Soj
	@RequestMapping(value = "/getDetallesSoj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FichaSojDTO> getDetallesSoj(@RequestBody FichaSojItem fichaSojItem, HttpServletRequest request) {
		FichaSojDTO response = new FichaSojDTO();
		try {
			response = sojService.getDetallesSoj(fichaSojItem, request);
			return new ResponseEntity<FichaSojDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<FichaSojDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Asociar SOJ
		@RequestMapping(value = "/asociarEJGaSOJ", produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> asociarSOJ(@RequestBody List<String> datos, HttpServletRequest request) {
			UpdateResponseDTO response = sojService.asociarEJGaSOJ(datos, request);

			if (response.getStatus().equals(SigaConstants.OK))
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	// Guardar Datos Generales
	@RequestMapping(value = "/guardarDatosGenerales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarDatosGenerales(@RequestBody FichaSojItem fichaSojItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = null;
		try {
			response = sojService.guardarDatosGenerales(fichaSojItem, request);
			if(response.getStatus().equals(SigaConstants.OK)) {
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			}else {
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Asociar SOJ
	@RequestMapping(value = "/asociarSOJ", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarSOJ(@RequestBody FichaSojItem sojItem,HttpServletRequest request){
		UpdateResponseDTO response = null;
		try {
			response = sojService.asociarSOJ(request,sojItem);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// Desasociar SOJ
	@RequestMapping(value = "/desasociarSOJ", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> desasociarSOJ(@RequestBody FichaSojItem sojItem,HttpServletRequest request) {
		UpdateResponseDTO response = null;
		try {
			response = sojService.desasociarSOJ(request,sojItem);
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Guardar Servicios de Tramitacion
	@RequestMapping(value = "/guardarServiciosTramitacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarServiciosTramitacion(@RequestBody FichaSojItem sojItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = sojService.guardarServiciosTramitacion(request,sojItem);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	// Subir Documentos SOJ
	@RequestMapping(value = "/subirDocumentoSOJ", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> subirDocumentoSOJ(@RequestBody List<DocumentacionSojItem> documentacionesSOJ,HttpServletRequest request) {
		InsertResponseDTO response = null;
		try {
			response = sojService.subirDocumentoSOJ(documentacionesSOJ,request);
		}catch(Exception e) {
			throw e;
		}
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	// Obtener Documentos SOJ.
	@RequestMapping(value = "/getDocumentosSOJ", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentacionSojDTO> getDocumentosSOJ(@RequestBody FichaSojItem fichaSojItem, HttpServletRequest request) {
		DocumentacionSojDTO response = new DocumentacionSojDTO();
		try {
			response = sojService.getDocumentosSOJ(fichaSojItem, request);
			return new ResponseEntity<DocumentacionSojDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<DocumentacionSojDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Eliminar Documentos SOJ
	@RequestMapping(value = "/eliminarDocumentoSOJ",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarDocumentoSOJ(HttpServletRequest request,@RequestBody List<DocumentacionSojItem> documentos) {
		DeleteResponseDTO response = null;
		try {
			response = sojService.eliminarDocumentoSOJ(request,documentos);
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
