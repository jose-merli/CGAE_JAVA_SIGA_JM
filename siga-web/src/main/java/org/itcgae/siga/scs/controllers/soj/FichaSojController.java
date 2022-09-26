package org.itcgae.siga.scs.controllers.soj;

import org.itcgae.siga.DTOs.scs.FichaSojDTO;
import org.itcgae.siga.DTOs.scs.FichaSojItem;
import org.itcgae.siga.scs.services.soj.ISojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value ="/soj")
public class FichaSojController {

    @Autowired
    private ISojService sojService;

    @RequestMapping(value = "/getDetallesSoj",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FichaSojDTO> getDetallesSoj(@RequestBody FichaSojItem fichaSojItem, HttpServletRequest request) {
        FichaSojDTO response = new FichaSojDTO();
        try {
            response = sojService.getDetallesSoj(fichaSojItem, request);
            return new ResponseEntity<FichaSojDTO>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<FichaSojDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
