package org.itcgae.siga.DTOs.scs;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

@Data
public class EstadoCertificacionDTO {

    private List<EstadoCertificacionItem> estadoCertificacionItemList = new ArrayList<>();
    private Error error = null;
}
