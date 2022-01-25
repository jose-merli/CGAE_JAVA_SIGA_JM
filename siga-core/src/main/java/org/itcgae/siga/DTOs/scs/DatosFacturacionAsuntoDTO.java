package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatosFacturacionAsuntoDTO {

    private String idObjeto;
    private String nombre;
    private String tipo;
    private String importe;
    private String idPartidaPresupuestaria;
    private List<DatosPagoAsuntoDTO> datosPagoAsuntoDTOList = new ArrayList<>();
}
