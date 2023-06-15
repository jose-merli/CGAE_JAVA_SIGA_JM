package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class DocumentacionAsistenciaItem {

    private String idDocumentacion;
    private String idTipoDoc;
    private String fechaEntrada;
    private String asociado;
    private String nombreFichero;
    private String observaciones;
    private String idFichero;
    private String descAsociado;
    private String descTipoDoc;
    private String idPersona;

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getDescAsociado() {
        return descAsociado;
    }

    public void setDescAsociado(String descAsociado) {
        this.descAsociado = descAsociado;
    }

    public String getDescTipoDoc() {
        return descTipoDoc;
    }

    public void setDescTipoDoc(String descTipoDoc) {
        this.descTipoDoc = descTipoDoc;
    }

    public String getIdDocumentacion() {
        return idDocumentacion;
    }

    public void setIdDocumentacion(String idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    public String getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(String idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getAsociado() {
        return asociado;
    }

    public void setAsociado(String asociado) {
        this.asociado = asociado;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(String idFichero) {
        this.idFichero = idFichero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentacionAsistenciaItem that = (DocumentacionAsistenciaItem) o;
        return Objects.equals(getIdDocumentacion(), that.getIdDocumentacion()) &&
                Objects.equals(getIdTipoDoc(), that.getIdTipoDoc()) &&
                Objects.equals(getFechaEntrada(), that.getFechaEntrada()) &&
                Objects.equals(getAsociado(), that.getAsociado()) &&
                Objects.equals(getNombreFichero(), that.getNombreFichero()) &&
                Objects.equals(getObservaciones(), that.getObservaciones()) &&
                Objects.equals(getIdFichero(), that.getIdFichero());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDocumentacion(), getIdTipoDoc(), getFechaEntrada(), getAsociado(), getNombreFichero(), getObservaciones(), getIdFichero());
    }
}
