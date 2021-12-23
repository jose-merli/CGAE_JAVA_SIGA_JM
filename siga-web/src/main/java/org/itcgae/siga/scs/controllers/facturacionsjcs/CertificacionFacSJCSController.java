package org.itcgae.siga.scs.controllers.facturacionsjcs;


import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @PostMapping(path = "/descargaInformeCAM")
    public ResponseEntity<Resource> descargaInformeCAM(@RequestParam("idFacturacion") String idFacturacion, @RequestParam("tipoFichero") String tipoFichero, HttpServletRequest request) {
        ResponseEntity<Resource> response = null;
        Resource resource = null;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.getInformeCAM(idFacturacion, tipoFichero, request);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            response = ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (Exception e) {
            error = true;
        }

        if (error) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }

    @PostMapping(value = "/subirFicheroCAM", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UpdateResponseDTO> subirFicheroCAM(@RequestParam("idFacturacion") String idFacturacion, @RequestPart MultipartFile fichero, MultipartHttpServletRequest request) {
        UpdateResponseDTO response = iCertificacionFacSJCSService.subirFicheroCAM(idFacturacion, fichero, request);
        if (response.getStatus().equals(SigaConstants.OK))
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getComboEstadosCertificaciones")
    ResponseEntity<ComboDTO> getComboEstadosCertificaciones(HttpServletRequest request) {
        ComboDTO response = iCertificacionFacSJCSService.getComboEstadosCertificaciones(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/buscarCertificaciones")
    ResponseEntity<CertificacionesDTO> buscarCertificaciones(@RequestBody BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, HttpServletRequest request) {
        CertificacionesDTO response = iCertificacionFacSJCSService.buscarCertificaciones(busquedaRetencionesRequestDTO, request);
        return new ResponseEntity<CertificacionesDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/validaCatalunya", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateResponseDTO> validaCatalunya(
            @RequestBody GestionEconomicaCatalunyaItem gestEcom, HttpServletRequest request) {
        UpdateResponseDTO response = iCertificacionFacSJCSService.validaCatalunya(gestEcom, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/eliminarCertificaciones")
    ResponseEntity<DeleteResponseDTO> eliminarCertificaciones(@RequestBody List<CertificacionesItem> certificacionesItemList, HttpServletRequest request) {
        DeleteResponseDTO response = iCertificacionFacSJCSService.eliminarCertificaciones(certificacionesItemList, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/getEstadosCertificacion")
    ResponseEntity<EstadoCertificacionDTO> getEstadosCertificacion(@RequestParam("idCertificacion") String idCertificacion, HttpServletRequest request) {
        EstadoCertificacionDTO response = iCertificacionFacSJCSService.getEstadosCertificacion(idCertificacion, request);
        return new ResponseEntity<EstadoCertificacionDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/createOrUpdateCertificacion")
    ResponseEntity<InsertResponseDTO> nuevaCertificacion(@RequestBody CertificacionesItem certificacionesItem, HttpServletRequest request) {
        InsertResponseDTO response = iCertificacionFacSJCSService.createOrUpdateCertificacion(certificacionesItem, request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/reabrirCertificacion")
    ResponseEntity<UpdateResponseDTO> reabrirCertificacion(@RequestBody CertificacionesItem certificacionesItem, HttpServletRequest request) {
        UpdateResponseDTO response = iCertificacionFacSJCSService.reabrirCertificacion(certificacionesItem, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

}
