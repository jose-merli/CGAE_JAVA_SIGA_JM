package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;

public interface IFacturacionRapidaService {

    ComboDTO getSeleccionSerieFacturacion(HttpServletRequest request, String idInstitucion, String idPeticion) throws Exception;

    Resource facturacionRapidaCompra(HttpServletRequest request, String idInstitucion, String idPeticion, String idSerieFacturacion) throws Exception;

    Resource facturacionRapidaCompraCertificados(HttpServletRequest request, String idInstitucion, String idSolicitudCertificado, String idSerieFacturacion) throws Exception;
}
