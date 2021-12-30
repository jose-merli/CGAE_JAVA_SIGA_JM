package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EnvioXuntaItem {

    private Short idInstitucion;
    private List<String> listaIdFacturaciones = new ArrayList<>();
    private Integer codigoOperacion;

}
