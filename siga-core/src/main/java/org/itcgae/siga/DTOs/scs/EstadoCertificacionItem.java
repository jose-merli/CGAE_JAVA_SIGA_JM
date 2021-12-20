package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;

@Data
public class EstadoCertificacionItem {

    private String idHistorico;
    private String idCertificacion;
    private String idInstitucion;
    private String proceso;
    private Date fechaEstado;
    private String idEstado;
    private String estado;
}
