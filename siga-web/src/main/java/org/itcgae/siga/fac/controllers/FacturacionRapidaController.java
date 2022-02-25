package org.itcgae.siga.fac.controllers;

import org.itcgae.siga.DTO.fac.FacturacionRapidaRequestDTO;
import org.itcgae.siga.DTO.fac.ListaCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.fac.services.IFacturacionRapidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @PostMapping("/facturacionRapidaCompra")
    ResponseEntity<Resource> facturacionRapidaCompra(HttpServletRequest request, @RequestBody FacturacionRapidaRequestDTO facturacionRapidaRequestDTO) {

        try {
            Resource resource = facturacionRapidaService.facturacionRapidaCompra(request, facturacionRapidaRequestDTO.getIdInstitucion(),
                    facturacionRapidaRequestDTO.getIdPeticion(), facturacionRapidaRequestDTO.getIdSerieFacturacion());


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/facturacionRapidaCompraCertificados")
    ResponseEntity<Resource> facturacionRapidaCompraCertificados(HttpServletRequest request, @RequestBody FacturacionRapidaRequestDTO facturacionRapidaRequestDTO) {

        try {
            Resource resource = facturacionRapidaService.facturacionRapidaCompraCertificados(request, facturacionRapidaRequestDTO.getIdInstitucion(),
                    facturacionRapidaRequestDTO.getIdSolicitudCertificado(), facturacionRapidaRequestDTO.getIdSerieFacturacion());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/descargarFacturas")
    ResponseEntity<Resource> descargarFacturasBySolicitud(HttpServletRequest request, @RequestBody List<ListaCompraProductosItem> listaPeticiones) {

        try {
            Resource resource = this.facturacionRapidaService.descargarFacturasBySolicitud(request, listaPeticiones);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
           // headers.add(HttpHeaders.CONTENT_DISPOSITION, "tipoFichero=" + (resource.getFilename().contains("pdf") ? "pdf" : "zip"));
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
