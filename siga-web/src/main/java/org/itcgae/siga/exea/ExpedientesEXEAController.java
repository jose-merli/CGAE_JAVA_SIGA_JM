package org.itcgae.siga.exea;

import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

}
