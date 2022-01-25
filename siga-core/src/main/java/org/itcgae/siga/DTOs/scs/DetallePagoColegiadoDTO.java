package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePagoColegiadoDTO {

    private Short idInstitucion;
    private Integer idPagos;
    private BigDecimal totalImporteSjcs;
    private BigDecimal importeTotalRetenciones;
    private BigDecimal importeTotalMovimientos;
    private BigDecimal totalImporteIrpf;
    private Long idPersonaSjcs;
    private Long idPerDestino;
    private Short idCuenta;
    private BigDecimal tipoIrpf;
    private BigDecimal importeTotalOficio;
    private BigDecimal importeTotalAsistencia;
    private BigDecimal importeTotalEjg;
    private BigDecimal importeTotalSoj;
    private String destinatario;
    private String formaDePago;
    private String nombreBanco;
    private String numeroCuenta;
}
