package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FaseFacturacionProgramadaItem {

    private String orden;
    private String nombreFase;
    private String fechaProgramacion;
    private String puestoEnCola;

}
