package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class DatosGeneralesActuacionAsistenciaItem {

    private String idActuacion;
    private String idCoste;
    private String descripcion;
    private String fechaActuacion;
    private boolean diaDespues;
    private String tipoActuacion;
    private String numAsunto;
    private String juzgado;
    private String comisaria;
    private String nig;
    private String prision;
    private String observaciones;
    private boolean controlCheckDiaDespues;

    public String getIdActuacion() {
        return idActuacion;
    }

    public void setIdActuacion(String idActuacion) {
        this.idActuacion = idActuacion;
    }

    public String getIdCoste() {
        return idCoste;
    }

    public void setIdCoste(String idCoste) {
        this.idCoste = idCoste;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaActuacion() {
        return fechaActuacion;
    }

    public void setFechaActuacion(String fechaActuacion) {
        this.fechaActuacion = fechaActuacion;
    }

    public boolean isDiaDespues() {
        return diaDespues;
    }

    public void setDiaDespues(boolean diaDespues) {
        this.diaDespues = diaDespues;
    }

    public String getTipoActuacion() {
        return tipoActuacion;
    }

    public void setTipoActuacion(String tipoActuacion) {
        this.tipoActuacion = tipoActuacion;
    }

    public String getNumAsunto() {
        return numAsunto;
    }

    public void setNumAsunto(String numAsunto) {
        this.numAsunto = numAsunto;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    public String getComisaria() {
        return comisaria;
    }

    public void setComisaria(String comisaria) {
        this.comisaria = comisaria;
    }

    public String getNig() {
        return nig;
    }

    public void setNig(String nig) {
        this.nig = nig;
    }

    public String getPrision() {
        return prision;
    }

    public void setPrision(String prision) {
        this.prision = prision;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatosGeneralesActuacionAsistenciaItem that = (DatosGeneralesActuacionAsistenciaItem) o;
        return isDiaDespues() == that.isDiaDespues() &&
                Objects.equals(getIdActuacion(), that.getIdActuacion()) &&
                Objects.equals(getIdCoste(), that.getIdCoste()) &&
                Objects.equals(getDescripcion(), that.getDescripcion()) &&
                Objects.equals(getFechaActuacion(), that.getFechaActuacion()) &&
                Objects.equals(getTipoActuacion(), that.getTipoActuacion()) &&
                Objects.equals(getNumAsunto(), that.getNumAsunto()) &&
                Objects.equals(getJuzgado(), that.getJuzgado()) &&
                Objects.equals(getComisaria(), that.getComisaria()) &&
                Objects.equals(getNig(), that.getNig()) &&
                Objects.equals(getPrision(), that.getPrision()) &&
                Objects.equals(getObservaciones(), that.getObservaciones());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdActuacion(), getIdCoste(), getDescripcion(), getFechaActuacion(), isDiaDespues(), getTipoActuacion(), getNumAsunto(), getJuzgado(), getComisaria(), getNig(), getPrision(), getObservaciones());
    }

    @Override
    public String toString() {
        return "DatosGeneralesActuacionAsistenciaItem{" +
                "idActuacion='" + idActuacion + '\'' +
                ", idCoste='" + idCoste + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaActuacion='" + fechaActuacion + '\'' +
                ", diaDespues=" + diaDespues +
                ", tipoActuacion='" + tipoActuacion + '\'' +
                ", numAsunto='" + numAsunto + '\'' +
                ", juzgado='" + juzgado + '\'' +
                ", comisaria='" + comisaria + '\'' +
                ", nig='" + nig + '\'' +
                ", prision='" + prision + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

	public boolean isControlCheckDiaDespues() {
		return controlCheckDiaDespues;
	}

	public void setControlCheckDiaDespues(boolean controlCheckDiaDespues) {
		this.controlCheckDiaDespues = controlCheckDiaDespues;
	}


}
