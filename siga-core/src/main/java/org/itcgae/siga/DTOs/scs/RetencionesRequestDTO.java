package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class RetencionesRequestDTO {

    private String nColegiado;
    private String tiposRetencion;
    private String idDestinatarios;
    private Date fechaInicio;
    private Date fechaFin;
    private String idPagos;
    private Date fechaAplicacionDesde;
    private Date fechaAplicacionHasta;
    private boolean historico;
    private String idRetenciones;
    private String numeroAbono;
    private String idPersona;
}
