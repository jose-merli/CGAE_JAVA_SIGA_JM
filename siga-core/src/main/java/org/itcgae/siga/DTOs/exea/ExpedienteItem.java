package org.itcgae.siga.DTOs.exea;

import java.util.Objects;

public class ExpedienteItem {

    private String idTipoExpediente;
    private String tipoExpediente;
    private String anioExpediente;
    private String numExpediente;
    private String estadoExpediente;
    private String relacion;
    private String fechaApertura;
    private String idInstitucionTipoExpediente;
    private boolean exea;

    public String getIdInstitucionTipoExpediente() {
        return idInstitucionTipoExpediente;
    }

    public void setIdInstitucionTipoExpediente(String idInstitucionTipoExpediente) {
        this.idInstitucionTipoExpediente = idInstitucionTipoExpediente;
    }

    public String getIdTipoExpediente() {
        return idTipoExpediente;
    }

    public void setIdTipoExpediente(String idTipoExpediente) {
        this.idTipoExpediente = idTipoExpediente;
    }

    public String getAnioExpediente() {
        return anioExpediente;
    }

    public void setAnioExpediente(String anioExpediente) {
        this.anioExpediente = anioExpediente;
    }

    public String getTipoExpediente() {
        return tipoExpediente;
    }

    public void setTipoExpediente(String tipoExpediente) {
        this.tipoExpediente = tipoExpediente;
    }

    public String getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

    public String getEstadoExpediente() {
        return estadoExpediente;
    }

    public void setEstadoExpediente(String estadoExpediente) {
        this.estadoExpediente = estadoExpediente;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public boolean isExea() {
        return exea;
    }

    public void setExea(boolean exea) {
        this.exea = exea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpedienteItem that = (ExpedienteItem) o;
        return isExea() == that.isExea() &&
                Objects.equals(getTipoExpediente(), that.getTipoExpediente()) &&
                Objects.equals(getNumExpediente(), that.getNumExpediente()) &&
                Objects.equals(getEstadoExpediente(), that.getEstadoExpediente()) &&
                Objects.equals(getRelacion(), that.getRelacion()) &&
                Objects.equals(getFechaApertura(), that.getFechaApertura());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTipoExpediente(), getNumExpediente(), getEstadoExpediente(), getRelacion(), getFechaApertura(), isExea());
    }

    @Override
    public String toString() {
        return "ExpedienteItem{" +
                "tipoExpediente='" + tipoExpediente + '\'' +
                ", numExpediente='" + numExpediente + '\'' +
                ", estadoExpediente='" + estadoExpediente + '\'' +
                ", relacion='" + relacion + '\'' +
                ", fechaApertura='" + fechaApertura + '\'' +
                ", exea=" + exea +
                '}';
    }
}
