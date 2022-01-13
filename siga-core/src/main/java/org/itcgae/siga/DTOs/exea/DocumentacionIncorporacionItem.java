package org.itcgae.siga.DTOs.exea;

public class DocumentacionIncorporacionItem {

    private String idDocumentacion;
    private String nombreDoc;
    private String obligatorio;
    private String observaciones;
    private String codDocEXEA;
    private String documento;
    private String tipoColegiacion;
    private String tipoSolicitud;
    private String idModalidad;
    private String idFichero;

    public String getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(String idFichero) {
        this.idFichero = idFichero;
    }

    public String getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(String idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getTipoColegiacion() {
        return tipoColegiacion;
    }

    public void setTipoColegiacion(String tipoColegiacion) {
        this.tipoColegiacion = tipoColegiacion;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getCodDocEXEA() {
        return codDocEXEA;
    }

    public void setCodDocEXEA(String codDocEXEA) {
        this.codDocEXEA = codDocEXEA;
    }

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
