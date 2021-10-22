package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class RetencionesAplicadasItem {

    private String tipoRetencion;
    private String numColegiado;
    private String nombre;
    private String apellidos1;
    private String apellidos2;
    private String idDestinatario;
    private String nombreDestinatario;
    private String desDestinatario;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaRetencion;
    private String importeRetenido;
    private String idPagosjg;
    private String mes;
    private String anio;
    private String pagoRelacionado;
    private String abonoRelacionado;
    private String idCobro;
    private String idRetencion;
    private String idInstitucion;
    private String idPersona;
    private String fechaDesde;
    private String fechaHasta;

}
