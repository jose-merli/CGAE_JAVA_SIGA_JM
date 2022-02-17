package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.io.File;

@Data
public class DocumentoDTO {

    private File documento;
    private String descripcion;
    private Object beanAsociado;

    public DocumentoDTO(File ficheroPdf, String descripcion) {
        this.documento = ficheroPdf;
        this.descripcion = descripcion;
    }

    public DocumentoDTO(File documento, String descripcion, Object beanAsociado) {
        this.documento = documento;
        this.descripcion = descripcion;
        this.beanAsociado = beanAsociado;
    }
}
