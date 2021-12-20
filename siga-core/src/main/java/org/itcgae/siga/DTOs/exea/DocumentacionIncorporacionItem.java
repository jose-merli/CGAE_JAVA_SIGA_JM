package org.itcgae.siga.DTOs.exea;

public class DocumentacionIncorporacionItem {

    private String idDocumentacion;
    private String nombreDoc;
    private String obligatorio;
    private String observaciones;
    private String documento;

    public String getIdDocumentacion() {
        return idDocumentacion;
    }

    public void setIdDocumentacion(String idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(String obligatorio) {
        this.obligatorio = obligatorio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
