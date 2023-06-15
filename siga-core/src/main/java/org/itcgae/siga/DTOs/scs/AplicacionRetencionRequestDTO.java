package org.itcgae.siga.DTOs.scs;

import lombok.Data;

@Data
public class AplicacionRetencionRequestDTO {

    private String idPersona;
    private String fechaPagoDesde;
    private String fechaPagoHasta;

}
