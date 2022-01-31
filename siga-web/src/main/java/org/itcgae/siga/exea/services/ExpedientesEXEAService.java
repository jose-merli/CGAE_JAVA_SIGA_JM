package org.itcgae.siga.exea.services;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ExpedientesEXEAService {

    StringDTO isEXEActivoInstitucion(HttpServletRequest request);
    ExpedienteDTO getExpedientesSIGAColegiado (HttpServletRequest request, String idPersona);
    StringDTO getTokenLoginEXEA(HttpServletRequest request);
    ExpedienteDTO getExpedientesEXEAPersonalColegio(HttpServletRequest request, String identificacionColegiado);
    ExpedienteDTO getDetalleExpedienteEXEA(HttpServletRequest request, String numExpedienteEXEA, String identificacionColegiado);
    StringDTO getParamsDocumentacionEXEA(HttpServletRequest request);
    InsertResponseDTO sincronizarDocumentacionEXEA(HttpServletRequest request, List<DocumentacionIncorporacionItem> documentacionEXEA);
    InsertResponseDTO subirDocumentoSolIncorp(MultipartHttpServletRequest request);
    ResponseEntity<InputStreamResource> descargarDocumentosSolIncorp(List<DocumentacionIncorporacionItem> documentos, HttpServletRequest request);
    ResponseEntity<InputStreamResource> getJustificante(HttpServletRequest request, String claveConsulta);
    DeleteResponseDTO eliminarDocumentoSolIncorp(HttpServletRequest request, String idSolicitud, List<DocumentacionIncorporacionItem> documentos);
    UpdateResponseDTO iniciarTramiteColegiacionEXEA(HttpServletRequest request, String idSolicitud);
}
