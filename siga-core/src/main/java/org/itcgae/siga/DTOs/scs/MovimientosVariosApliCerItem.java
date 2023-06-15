package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MovimientosVariosApliCerItem {

    private String idInstitucion;
    private String idMovimiento;
    private String idPersona;
    private String numColegiado;
    private String apellidos1;
    private String apellidos2;
    private String apellidos;
    private String nombre;
    private String descripcion;
    private Date fechaAlta;
    private BigDecimal importeAplicado;
    private String asunto;
}

