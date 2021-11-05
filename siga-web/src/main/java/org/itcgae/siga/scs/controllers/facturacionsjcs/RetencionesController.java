package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.db.entities.FcsRetencionesJudiciales;
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

    @PostMapping("/searchRetencion")
    public ResponseEntity<RetencionDTO> searchRetencion(@RequestBody RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request) {
        RetencionDTO response = iRetencionesService.searchRetencion(retencionesRequestDTO, request);
        return new ResponseEntity<RetencionDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteRetenciones")
    public ResponseEntity<DeleteResponseDTO> deleteRetenciones(@RequestBody List<RetencionesItem> retencionesItemList, HttpServletRequest request) {
        DeleteResponseDTO response = iRetencionesService.deleteRetenciones(retencionesItemList, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/saveOrUpdateRetencion")
    public ResponseEntity<InsertResponseDTO> saveOrUpdateRetencion(@RequestBody FcsRetencionesJudiciales retencion, HttpServletRequest request) {
        InsertResponseDTO response = iRetencionesService.saveOrUpdateRetencion(retencion, request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/searchRetencionesAplicadas")
    public ResponseEntity<RetencionesAplicadasDTO> searchRetencionesAplicadas(@RequestBody RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request) {
        RetencionesAplicadasDTO response = iRetencionesService.searchRetencionesAplicadas(retencionesRequestDTO, request);
        return new ResponseEntity<RetencionesAplicadasDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/getAplicacionesRetenciones")
    public ResponseEntity<AplicacionRetencionDTO> getAplicacionesRetenciones(@RequestBody AplicacionRetencionRequestDTO aplicacionRetencionRequestDTO, HttpServletRequest request) {
        AplicacionRetencionDTO response = iRetencionesService.getAplicacionesRetenciones(aplicacionRetencionRequestDTO, request);
        return new ResponseEntity<AplicacionRetencionDTO>(response, HttpStatus.OK);
    }

}
