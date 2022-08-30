package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

public class EjgListaIntercambiosItem {

    private Short idInstitucion;
    private Long idEcomIntercambio;
    private Long idEcomCola;

    private String descripcion;
    private String estadoRespuesta;
    private Short idEstadoRespuesta;
    private String respuesta;

    private Date fechaEnvio;
    private Date fechaRespuesta;

    public Short getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Short idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Long getIdEcomIntercambio() {
        return idEcomIntercambio;
    }

    public void setIdEcomIntercambio(Long idEcomIntercambio) {
        this.idEcomIntercambio = idEcomIntercambio;
    }

    public Long getIdEcomCola() {
        return idEcomCola;
    }

    public void setIdEcomCola(Long idEcomCola) {
        this.idEcomCola = idEcomCola;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public void setEstadoRespuesta(String estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }

    public Short getIdEstadoRespuesta() {
        return idEstadoRespuesta;
    }

    public void setIdEstadoRespuesta(Short idEstadoRespuesta) {
        this.idEstadoRespuesta = idEstadoRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EjgListaIntercambiosItem that = (EjgListaIntercambiosItem) o;
        return Objects.equals(idInstitucion, that.idInstitucion) &&
                Objects.equals(idEcomIntercambio, that.idEcomIntercambio) &&
                Objects.equals(idEcomCola, that.idEcomCola) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(estadoRespuesta, that.estadoRespuesta) &&
                Objects.equals(idEstadoRespuesta, that.idEstadoRespuesta) &&
                Objects.equals(respuesta, that.respuesta) &&
                Objects.equals(fechaEnvio, that.fechaEnvio) &&
                Objects.equals(fechaRespuesta, that.fechaRespuesta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInstitucion, idEcomIntercambio, idEcomCola, descripcion, estadoRespuesta, idEstadoRespuesta, respuesta, fechaEnvio, fechaRespuesta);
    }

    @Override
    public String toString() {
        return "EjgListaIntercambiosItem{" +
                "idInstitucion=" + idInstitucion +
                ", idEcomIntercambio=" + idEcomIntercambio +
                ", idEcomCola=" + idEcomCola +
                ", descripcion='" + descripcion + '\'' +
                ", estadoRespuesta='" + estadoRespuesta + '\'' +
                ", idEstadoRespuesta=" + idEstadoRespuesta +
                ", respuesta='" + respuesta + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", fechaRespuesta=" + fechaRespuesta +
                '}';
    }
}
