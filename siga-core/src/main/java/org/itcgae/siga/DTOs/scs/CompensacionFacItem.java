package org.itcgae.siga.DTOs.scs;

import lombok.Data;

@Data
public class CompensacionFacItem {

    private boolean compensar;
    private String idPersona;
    private String numColegiado;
    private String nombre;
    private String idFactura;
    private String numeroFactura;
    private String fechaFactura;
    private String importeTotalFactura;
    private String importePendienteFactura;
    private String importeCompensado;
    private String importePagado;
}
