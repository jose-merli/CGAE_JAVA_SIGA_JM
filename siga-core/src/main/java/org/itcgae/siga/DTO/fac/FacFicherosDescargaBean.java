package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.io.File;

@Data
public class FacFicherosDescargaBean {

    private File fichero;
    private String nombreFacturaFichero;
    private Integer formatoDescarga;

}
