package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BusquedaRetencionesRequestDTO {

    private List<String> idInstitucionList;
    private List<String> idPartidaPresupuestariaList;
    private List<String> idGrupoFacturacionList;
    private List<String> idHitoGeneralList;
    private String nombre;
    private List<String> idEstadoCertificacionList;
    private Date fechaDesde;
    private Date fechaHasta;
    private String idCertificacion;
}
