package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class TarjetaJustificacionActuacionAsistenciaItem {
    private String estado;
    private String observaciones;
    private String fechaJustificacion;
    private String anulada;
    private String validada;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaJustificacion() {
        return fechaJustificacion;
    }

    public void setFechaJustificacion(String fechaJustificacion) {
        this.fechaJustificacion = fechaJustificacion;
    }

    public String getAnulada() {
        return anulada;
    }

    public void setAnulada(String anulada) {
        this.anulada = anulada;
    }

    public String getValidada() {
        return validada;
    }

    public void setValidada(String validada) {
        this.validada = validada;
    }

    @Override
    public String toString() {
        return "TarjetaJustificacionActuacionAsistenciaItem{" +
                "estado='" + estado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", fechaJustificacion='" + fechaJustificacion + '\'' +
                ", anulada='" + anulada + '\'' +
                ", validada='" + validada + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarjetaJustificacionActuacionAsistenciaItem that = (TarjetaJustificacionActuacionAsistenciaItem) o;
        return Objects.equals(getEstado(), that.getEstado()) &&
                Objects.equals(getObservaciones(), that.getObservaciones()) &&
                Objects.equals(getFechaJustificacion(), that.getFechaJustificacion()) &&
                Objects.equals(getAnulada(), that.getAnulada()) &&
                Objects.equals(getValidada(), that.getValidada());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEstado(), getObservaciones(), getFechaJustificacion(), getAnulada(), getValidada());
    }
}
