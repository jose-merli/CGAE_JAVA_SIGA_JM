package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturacionRapidaRequestDTO {

    private String idInstitucion;
    private String idPeticion; // Se rellena solo cuando vengamos de una compra
    private String idSolicitudCertificado; // Se rellena solo cuando vengamos de un certificado
    private String idSerieFacturacion;
}
