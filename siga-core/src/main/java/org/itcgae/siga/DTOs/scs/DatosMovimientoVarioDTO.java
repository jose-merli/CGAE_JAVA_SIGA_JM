package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatosMovimientoVarioDTO {

    private String idMovimiento;
    private String descripcion;
    private String tipo;
    private String importe;
    private List<DatosPagoAsuntoDTO> datosPagoAsuntoDTOList = new ArrayList<>();
}
