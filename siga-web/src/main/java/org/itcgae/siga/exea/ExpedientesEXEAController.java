package org.itcgae.siga.exea;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ExpedienteDTO> getDetalleExpedienteEXEA(HttpServletRequest request, @RequestParam("numExp") String numExpedienteEXEA) {
        ExpedienteDTO response = null;
        try {
            response = expedientesEXEAService.getDetalleExpedienteEXEA(request, numExpedienteEXEA);
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





}
