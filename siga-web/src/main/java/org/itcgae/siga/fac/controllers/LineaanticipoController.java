package org.itcgae.siga.fac.controllers;


import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederoDTO;
import org.itcgae.siga.fac.services.ILineaanticipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LineaanticipoController {

    @Autowired
    private ILineaanticipoService walletService;

    @GetMapping(value = "/pys/getMonedero")
    ResponseEntity<ListaMonederoDTO> getMonederos(HttpServletRequest request, @RequestBody FiltroMonederoItem filtroMonederoItem) throws Exception {
        ListaMonederoDTO response = walletService.listarMonederos(request, filtroMonederoItem);
        return new ResponseEntity<>(response, response.getError() == null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
