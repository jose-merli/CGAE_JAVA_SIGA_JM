package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class CertificacionesItem {

    private Date fechaDesde;
    private Date fechaHasta;
    private String periodo;
    private String nombre;
    private String turno;
    private String guardia;
    private String ejg;
    private String soj;
    private String total;
    private String idEstadoCertificacion;
    private String estado;

}
