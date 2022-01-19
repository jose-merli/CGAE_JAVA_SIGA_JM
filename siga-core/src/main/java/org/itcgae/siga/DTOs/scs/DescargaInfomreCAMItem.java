package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DescargaInfomreCAMItem {

    private String idEstadoCertificacion;
    private Short idInstitucion;
    private List<String> listaIdFacturaciones = new ArrayList<>();
    private String tipoFichero;
}
