package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

}
