package org.itcgae.siga.DTOs.scs;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

@Data
public class FacturacionesAsuntoDTO {

    private List<DatosFacturacionAsuntoDTO> datosFacturacionAsuntoDTOList = new ArrayList<>();
    private DatosMovimientoVarioDTO datosMovimientoVarioDTO;
    private Error error = null;
}
