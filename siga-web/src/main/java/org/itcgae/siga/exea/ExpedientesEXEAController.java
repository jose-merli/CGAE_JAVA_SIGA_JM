package org.itcgae.siga.exea;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionAsistenciaItem;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/expedientesEXEA")
public class ExpedientesEXEAController {

    @Autowired
    private ExpedientesEXEAService expedientesEXEAService;

    @GetMapping(value = "/activoEXEA")
    public ResponseEntity<StringDTO> isEXEActivoInstitucion(HttpServletRequest request) {
        StringDTO response = null;
        try {
            response = expedientesEXEAService.isEXEActivoInstitucion(request);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/tokenTramitesEXEA")
    public ResponseEntity<StringDTO> getTokenLoginEXEA(HttpServletRequest request) {
        StringDTO response = null;
        try {
            response = expedientesEXEAService.getTokenLoginEXEA(request);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/expedientesSIGA/{idPersona}")
    public ResponseEntity<ExpedienteDTO> getExpedientesSIGAColegiado(HttpServletRequest request, @PathVariable("idPersona") String idPersona) {
        ExpedienteDTO response = null;
        try {
            response = expedientesEXEAService.getExpedientesSIGAColegiado(request, idPersona);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<ExpedienteDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{dniColegiado}")
    public ResponseEntity<ExpedienteDTO> getExpedientesEXEAPersonalColegio(HttpServletRequest request, @PathVariable("dniColegiado") String dniColegiado) {
        ExpedienteDTO response = null;
        try {
            response = expedientesEXEAService.getExpedientesEXEAPersonalColegio(request, dniColegiado);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<ExpedienteDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/detalle")
    public ResponseEntity<ExpedienteDTO> getDetalleExpedienteEXEA(HttpServletRequest request, @RequestParam("numExp") String numExpedienteEXEA, @RequestParam(required = false) String identificacionColegiado) {
        ExpedienteDTO response = null;
        try {
            response = expedientesEXEAService.getDetalleExpedienteEXEA(request, numExpedienteEXEA, identificacionColegiado);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<ExpedienteDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/paramsDocEXEA")
    public ResponseEntity<StringDTO> getParamsDocumentacionEXEA(HttpServletRequest request) {
        StringDTO response = null;
        try {
            response = expedientesEXEAService.getParamsDocumentacionEXEA(request);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/sincronizarDocumentacionEXEA")
    public ResponseEntity<InsertResponseDTO> sincronizarDocumentacionEXEA(HttpServletRequest request, @RequestBody List<DocumentacionIncorporacionItem> documentosEXEA) {
        InsertResponseDTO response = null;
        try {
            response = expedientesEXEAService.sincronizarDocumentacionEXEA(request,documentosEXEA);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/subirDocumentoSolIncorp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InsertResponseDTO> subirDocumentoSolIncorp(MultipartHttpServletRequest request) {
        InsertResponseDTO response = null;
        try {
            response = expedientesEXEAService.subirDocumentoSolIncorp(request);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/eliminarDocumentoSolIncorp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteResponseDTO> eliminarDocumentoSolIncorp(HttpServletRequest request, @RequestParam String idSolicitud, @RequestBody List<DocumentacionIncorporacionItem> documentos) {
        DeleteResponseDTO response = null;
        try {
            response = expedientesEXEAService.eliminarDocumentoSolIncorp(request,idSolicitud,documentos);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/descargarDocumentoSolIncorp", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InputStreamResource> descargarDocumentosSolIncorp(
            @RequestBody List<DocumentacionIncorporacionItem> documentos, HttpServletRequest request) {
        ResponseEntity<InputStreamResource> response = expedientesEXEAService.descargarDocumentosSolIncorp(documentos,request);
        return response;
    }

    @PostMapping(value = "/getJustificante", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InputStreamResource> getJustificante(HttpServletRequest request, @RequestBody String claveConsulta) {
        ResponseEntity<InputStreamResource> response = expedientesEXEAService.getJustificante(request, claveConsulta);
        return response;
    }


    @PostMapping(value = "/iniciarTramiteColegiacionEXEA", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateResponseDTO> iniciarTramiteColegiacionEXEA(HttpServletRequest request, @RequestBody String idSolicitud) {
        UpdateResponseDTO response = null;
        try {
            response = expedientesEXEAService.iniciarTramiteColegiacionEXEA(request, idSolicitud);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }


}
