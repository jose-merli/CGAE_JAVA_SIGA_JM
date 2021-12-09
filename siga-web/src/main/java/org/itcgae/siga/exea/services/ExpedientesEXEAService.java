package org.itcgae.siga.exea.services;

import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;

import javax.servlet.http.HttpServletRequest;

public interface ExpedientesEXEAService {

    public StringDTO isEXEActivoInstitucion(HttpServletRequest request);
    public ExpedienteDTO getExpedientesSIGAColegiado (HttpServletRequest request, String idPersona);
    public StringDTO getTokenLoginEXEA(HttpServletRequest request);
    public ExpedienteDTO getExpedientesEXEAPersonalColegio(HttpServletRequest request, String identificacionColegiado);

}
