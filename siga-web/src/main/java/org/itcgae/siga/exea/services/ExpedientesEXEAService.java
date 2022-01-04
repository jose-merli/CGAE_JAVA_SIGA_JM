package org.itcgae.siga.exea.services;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ExpedientesEXEAService {

    public StringDTO isEXEActivoInstitucion(HttpServletRequest request);
    public ExpedienteDTO getExpedientesSIGAColegiado (HttpServletRequest request, String idPersona);
    public StringDTO getTokenLoginEXEA(HttpServletRequest request);
    public ExpedienteDTO getExpedientesEXEAPersonalColegio(HttpServletRequest request, String identificacionColegiado);
    public ExpedienteDTO getDetalleExpedienteEXEA(HttpServletRequest request, String numExpedienteEXEA);
    public StringDTO getParamsDocumentacionEXEA(HttpServletRequest request);
    public InsertResponseDTO sincronizarDocumentacionEXEA (HttpServletRequest request, List<DocumentacionIncorporacionItem> documentacionEXEA);
}
