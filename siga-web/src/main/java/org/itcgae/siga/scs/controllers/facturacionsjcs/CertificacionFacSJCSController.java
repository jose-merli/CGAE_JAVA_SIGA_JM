package org.itcgae.siga.scs.controllers.facturacionsjcs;


import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/certificaciones")
public class CertificacionFacSJCSController {

    @Autowired
    private ICertificacionFacSJCSService iCertificacionFacSJCSService;

    @PostMapping("/tramitarCertificacion")
    ResponseEntity<InsertResponseDTO> tramitarCertificacion(@RequestBody TramitarCerttificacionRequestDTO tramitarCerttificacionRequestDTO, HttpServletRequest request) {
        InsertResponseDTO response = iCertificacionFacSJCSService.tramitarCertificacion(tramitarCerttificacionRequestDTO, request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/descargaInformeCAM")
    public ResponseEntity<Resource> descargaInformeCAM(@RequestBody DescargaInfomreCAMItem descargaInfomreCAMItem, HttpServletRequest request) {
        ResponseEntity<Resource> response = null;
        Resource resource;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.getInformeCAM(descargaInfomreCAMItem, request);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
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
    ResponseEntity<UpdateResponseDTO> subirFicheroCAM(MultipartHttpServletRequest request) {
        UpdateResponseDTO response = iCertificacionFacSJCSService.subirFicheroCAM(request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
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

    @PostMapping("/buscarErroresCAM")
    ResponseEntity<PcajgAlcActErrorCamDTO> buscarErroresCAM(@RequestBody String idCertificacion, HttpServletRequest request) {
    	PcajgAlcActErrorCamDTO response = iCertificacionFacSJCSService.buscarErroresCAM(idCertificacion, request);
        return new ResponseEntity<PcajgAlcActErrorCamDTO>(response, HttpStatus.OK);
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


    @PostMapping(path = "/descargaErrorValidacion")
    public ResponseEntity<Resource> descargaErrorValidacion(@RequestBody GestionEconomicaCatalunyaItem gestionVo, HttpServletRequest request) {
        ResponseEntity<Resource> response = null;
        Resource resource = null;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.descargaErrorValidacion(gestionVo, request);
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

    @PostMapping(value = "/enviaRespuestaCICAC_ICA", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateResponseDTO> enviaRespuestaCICAC_ICA(
            @RequestBody GestionEconomicaCatalunyaItem gestEcom, HttpServletRequest request) {
        UpdateResponseDTO response = iCertificacionFacSJCSService.enviaRespuestaCICAC_ICA(gestEcom, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(path = "/descargarCertificacionesXunta")
    public ResponseEntity<Resource> descargarCertificacionesXunta(@RequestBody DescargaCertificacionesXuntaItem descargaItem, HttpServletRequest request) {
        ResponseEntity<Resource> response = null;
        Resource resource = null;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.descargarCertificacionesXunta(descargaItem, request);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documentos.zip");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            response = ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("application/zip")).body(resource);
        } catch (Exception e) {
            error = true;
        }

        if (error) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }


    @RequestMapping(value = "/buscarFactCertificaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FacturacionDTO> buscarFacturaciones(@RequestBody String idCertificacion,
                                                       HttpServletRequest request) {
        FacturacionDTO response = iCertificacionFacSJCSService.getFactCertificaciones(idCertificacion, request);
        return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/saveFactCertificacion")
    ResponseEntity<InsertResponseDTO> saveFactCertificacion(@RequestBody CertificacionesItem certificacionesItem, HttpServletRequest request) {
        InsertResponseDTO response = iCertificacionFacSJCSService.saveFactCertificacion(certificacionesItem, request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/delFactCertificacion")
    ResponseEntity<DeleteResponseDTO> delFactCertificacion(@RequestBody List<CertificacionesItem> certificacionesItemList, HttpServletRequest request) {
        DeleteResponseDTO response = iCertificacionFacSJCSService.delFactCertificacion(certificacionesItemList, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/reabrirfacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> reabrirFacturacion(@RequestBody List<CertificacionesItem> certificacionesItemList,
                                                         HttpServletRequest request) {
        InsertResponseDTO response = iCertificacionFacSJCSService.reabrirFacturacion(certificacionesItemList, request);
        if (response.getError().getCode() == 200) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/accionXuntaEnvios")
    ResponseEntity<UpdateResponseDTO> accionXuntaEnvios(@RequestBody EnvioXuntaItem envioItem, HttpServletRequest request) {
        UpdateResponseDTO response = iCertificacionFacSJCSService.accionXuntaEnvios(envioItem, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
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

    @GetMapping("/getMvariosAsociadosCertificacion")
    ResponseEntity<MovimientosVariosAsoCerDTO> getMvariosAsociadosCertificacion(@RequestParam("idCertificacion") String idCertificacion, HttpServletRequest request) {
        MovimientosVariosAsoCerDTO response = iCertificacionFacSJCSService.getMvariosAsociadosCertificacion(idCertificacion, request);
        return new ResponseEntity<MovimientosVariosAsoCerDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/getMvariosAplicadosEnPagosEjecutadosPorPeriodo")
    ResponseEntity<MovimientosVariosApliCerDTO> getMvariosAplicadosEnPagosEjecutadosPorPeriodo(@RequestBody MovimientosVariosApliCerRequestDTO movimientosVariosApliCerRequestDTO, HttpServletRequest request) {
        MovimientosVariosApliCerDTO response = iCertificacionFacSJCSService.getMvariosAplicadosEnPagosEjecutadosPorPeriodo(movimientosVariosApliCerRequestDTO, request);
        return new ResponseEntity<MovimientosVariosApliCerDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/descargarLogReintegrosXunta")
    ResponseEntity<Resource> descargarLogReintegrosXunta(@RequestBody DescargaReintegrosXuntaDTO descargaReintegrosXuntaDTO, HttpServletRequest request) {

        ResponseEntity<Resource> response = null;
        Resource resource;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.descargarLogReintegrosXunta(descargaReintegrosXuntaDTO.getIdFactsList(), request);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
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

    @PostMapping(value = "/descargarInformeIncidencias")
    ResponseEntity<Resource> descargarInformeReintegrosXunta(@RequestBody DescargaCertificacionesXuntaItem descargarInformeIncidencias, HttpServletRequest request) {

        ResponseEntity<Resource> response = null;
        Resource resource;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.descargarInformeIncidencias(descargarInformeIncidencias.getListaIdFacturaciones(), request);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
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

    @PostMapping(value = "/descargaGeneral")
    ResponseEntity<Resource> descargaGeneral(@RequestBody DescargaCertificacionesGeneralDTO descargaDTO, HttpServletRequest request) {

        ResponseEntity<Resource> response = null;
        Resource resource;
        Boolean error = false;

        try {
            resource = iCertificacionFacSJCSService.descargaGeneral(descargaDTO.getIdCertificacion(),descargaDTO.getIdEstadoCertificacion(), request);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
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
    
    @GetMapping("/perteneceInstitucionCAMoXunta")
    ResponseEntity<StringDTO> perteneceInstitucionCAMoXunta(HttpServletRequest request) {
        StringDTO response = new StringDTO();
        try {
            response = iCertificacionFacSJCSService.perteneceInstitucionCAMoXunta(request);
        } catch (Exception e) {
            return new ResponseEntity<StringDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }
}
