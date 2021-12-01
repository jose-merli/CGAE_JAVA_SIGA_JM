package org.itcgae.siga.scs.controllers.facturacionsjcs;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificaciones")
public class CertificacionFacSJCSController {

    @Autowired
    private ICertificacionFacSJCSService iCertificacionFacSJCSService;

    @PostMapping("/tramitarCertificacion")
    ResponseEntity<InsertResponseDTO> tramitarCertificacion(@RequestBody FcsFacturacionjg fcsFacturacionjg, HttpServletRequest request) {
        InsertResponseDTO response = iCertificacionFacSJCSService.tramitarCertificacion(fcsFacturacionjg.getIdfacturacion().toString(), request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    
    @RequestMapping(path = "/descargaInforme", method = RequestMethod.GET)
    public ResponseEntity<Resource> descargaInforme(@RequestParam String idFacturacion, String tipoFichero, HttpServletRequest request)  {
		ResponseEntity<Resource> response = null;
		Boolean error = false;
		File file = iCertificacionFacSJCSService.getInforme(idFacturacion, tipoFichero, request);
		if (file != null) {
			try (FileInputStream inputStream = new FileInputStream(file)) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
				InputStreamResource resource;
				resource = new InputStreamResource(inputStream);
				response = ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
			} catch (IOException e1) {
				error = true;
			}
		} else {
			error = true;
		}

		if (error) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return response;
    }
    
}
