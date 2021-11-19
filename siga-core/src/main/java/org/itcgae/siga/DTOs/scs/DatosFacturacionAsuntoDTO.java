package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatosFacturacionAsuntoDTO {

    private String idFacturacion;
    private String nombre;
    private String tipo;
    private String importe;
    private List<DatosPagoAsuntoDTO> datosPagoAsuntoDTOList = new ArrayList<>();
}
