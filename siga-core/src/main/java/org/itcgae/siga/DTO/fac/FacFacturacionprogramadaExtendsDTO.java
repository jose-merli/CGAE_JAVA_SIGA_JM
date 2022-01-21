package org.itcgae.siga.DTO.fac;

import lombok.Data;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;

@Data
public class FacFacturacionprogramadaExtendsDTO extends FacFacturacionprogramada {

    private String nombreabreviado;
}
