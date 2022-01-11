package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MovimientoVarioDTO {

    private String motivo;
    private String descripcion;
    private Short idInstitucion;
    private Long idMovimiento;
    private BigDecimal cantidad;
    private Integer idFacturacion;
    private Short idGrupoFacturacion;
    private BigDecimal importeAplicado;
    private Date fechaModificacion;
    private Date fechaAlta;
    private Integer usuModificacion;

}
