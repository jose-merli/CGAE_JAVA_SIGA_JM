package org.itcgae.siga.DTOs.scs;

import lombok.Data;

@Data
public class AplicacionRetencionItem {

    private String anioMes;
    private String tipoRetencion;
    private String importeAntAplicaRetencion;
    private String importeAntRetenido;
    private String importeAplicaRetencion;
    private String importeRetenido;
    private String importeTotAplicaRetencion;
    private String importeTotRetenido;
    private String importeSmi;
    private String colegiado;
    private String nombrePago;

}
