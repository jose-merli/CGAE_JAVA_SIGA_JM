package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturaFacturacionProgramadaDTO {

    private Short idInstitucion;
    private String idFactura;
    private String numeroFactura;
    private Long idPersona;
    private Short idEstadoPdf;
}
