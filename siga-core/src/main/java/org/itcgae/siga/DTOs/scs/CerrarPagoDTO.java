package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.List;

@Data
public class CerrarPagoDTO {

    private String idPago;
    private List<String> idsParaEnviar;

}
