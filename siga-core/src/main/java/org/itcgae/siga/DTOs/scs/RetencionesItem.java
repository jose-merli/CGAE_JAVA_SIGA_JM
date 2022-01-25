package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class RetencionesItem {

    private String idInstitucion;
    private String idPersona;
    private String idRetencion;
    private String nColegiado;
    private String tipoRetencion;
    private String nombre;
    private String idDestinatario;
    private String nombreDestinatario;
    private Date fechaInicio;
    private Date fechaFin;
    private String importe;
    private String retencionAplicada;
    private String restante;

}

