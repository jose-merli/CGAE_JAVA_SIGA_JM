package org.itcgae.siga.fac.controllers;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.fac.services.IFacturacionRapidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/facturacionRapidaPyS")
public class FacturacionRapidaController {

    @Autowired
    private IFacturacionRapidaService facturacionRapidaService;

    @GetMapping("/getSeleccionSerieFacturacion")
    ResponseEntity<ComboDTO> getSeleccionSerieFacturacion(HttpServletRequest request, @RequestParam String idInstitucion,
                                                          @RequestParam String idPeticion) {

        ComboDTO response = new ComboDTO();

        try {

            response = facturacionRapidaService.getSeleccionSerieFacturacion(request, idInstitucion, idPeticion);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setError(UtilidadesString.creaError(e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
