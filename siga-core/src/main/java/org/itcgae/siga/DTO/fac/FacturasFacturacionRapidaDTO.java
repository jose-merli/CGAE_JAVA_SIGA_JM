package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturasFacturacionRapidaDTO {

    private Short idInstitucion;
    private Long idPersona;
    private String idFactura;
    private String numeroFactura;
    private Long idSerieFacturacion;
    private Long idProgramacion;
    private Short estado;
    private Long idPeticion;
}
