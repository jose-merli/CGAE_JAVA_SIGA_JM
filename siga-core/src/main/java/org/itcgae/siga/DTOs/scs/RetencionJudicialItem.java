package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class RetencionJudicialItem {

    private String idInstitucion;
    private String idRetencion;
    private String idPersona;
    private String idDestinatario;
    private Date fechaAlta;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoRetencion;
    private String importe;
    private String observaciones;
    private Date fechaModificacion;
    private String usuModificacion;
    private String contabilizado;
    private String esDeTurno;
    private String descDestinatario;
    private String restante;

}
