package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class AdmConsultaInformeConsultaDTO {

    private Short idInstitucion;
    private Integer idPlantilla;
    private Short idInstitucionConsulta;
    private Long idConsulta;
    private String nombre;
    private String variasLineas;
    private String descripcion;
    private String general;
    private String sentencia;
    private Short idModulo;
}
