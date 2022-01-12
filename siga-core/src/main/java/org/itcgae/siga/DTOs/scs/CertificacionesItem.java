package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class CertificacionesItem {

    private String idCertificacion;
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
    private String idPartidaPresupuestaria;
    private String nombrePartidaPresupuestaria;
    private String idFacturacion;
    private String idInstitucion;
    private Date fechaModificacion;
    private String usuarioModificacion;

}
