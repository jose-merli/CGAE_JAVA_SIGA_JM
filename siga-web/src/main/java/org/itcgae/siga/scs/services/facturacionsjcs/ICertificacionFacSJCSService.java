package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface ICertificacionFacSJCSService {

    InsertResponseDTO tramitarCertificacion(String idFacturacion, HttpServletRequest request);
}
