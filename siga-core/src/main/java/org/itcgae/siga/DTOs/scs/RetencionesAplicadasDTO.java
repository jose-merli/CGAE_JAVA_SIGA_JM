package org.itcgae.siga.DTOs.scs;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

@Data
public class RetencionesAplicadasDTO {

    private List<RetencionesAplicadasItem> retencionesAplicadasItemList = new ArrayList<>();
    private Error error = null;

}
