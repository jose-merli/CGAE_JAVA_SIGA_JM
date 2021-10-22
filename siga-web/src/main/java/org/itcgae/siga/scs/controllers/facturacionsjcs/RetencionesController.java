package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.scs.RetencionesDTO;
import org.itcgae.siga.DTOs.scs.RetencionesItem;
import org.itcgae.siga.DTOs.scs.RetencionesRequestDTO;
import org.itcgae.siga.scs.services.facturacionsjcs.IRetencionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/retenciones")
public class RetencionesController {

    @Autowired
    private IRetencionesService iRetencionesService;

    @PostMapping("/searchRetenciones")
    public ResponseEntity<RetencionesDTO> searchRetenciones(@RequestBody RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request) {
        RetencionesDTO response = iRetencionesService.searchRetenciones(retencionesRequestDTO, request);
        return new ResponseEntity<RetencionesDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteRetenciones")
    public ResponseEntity<DeleteResponseDTO> deleteRetenciones(@RequestBody List<RetencionesItem> retencionesItemList, HttpServletRequest request) {
        DeleteResponseDTO response = iRetencionesService.deleteRetenciones(retencionesItemList, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

}
