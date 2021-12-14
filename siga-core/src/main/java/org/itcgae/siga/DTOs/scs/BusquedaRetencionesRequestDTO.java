package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BusquedaRetencionesRequestDTO {

    private List<Short> idInstitucionList;
    private List<Integer> idPartidaPresupuestariaList;
    private List<Short> idGrupoFacturacionList;
    private List<Short> idHitoGeneralList;
    private String nombre;
    private List<Short> idEstadoCertificacionList;
    private Date fechaDesde;
    private Date fechaHasta;
}
