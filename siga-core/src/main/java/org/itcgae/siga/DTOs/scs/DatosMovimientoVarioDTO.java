package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatosMovimientoVarioDTO {

    private String idObjeto;
    private String nombre;
    private String tipo;
    private String importe;
    private long numAplicaciones;
    private List<DatosPagoAsuntoDTO> datosPagoAsuntoDTOList = new ArrayList<>();
}
