package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TramitarCerttificacionRequestDTO {

    private String idCertificacion;
    private List<FacturacionItem> facturacionItemList = new ArrayList<>();

}
