package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTOs.gen.ComboDTO;

import javax.servlet.http.HttpServletRequest;

public interface IFacturacionRapidaService {

    ComboDTO getSeleccionSerieFacturacion(HttpServletRequest request, String idInstitucion, String idPeticion) throws Exception;

    void facturacionRapidaCompra(HttpServletRequest request, String idInstitucion, String idPeticion, String idSerieFacturacion) throws Exception;
}
