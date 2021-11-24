package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class HistoricoActuacionAsistenciaItem implements Comparable<HistoricoActuacionAsistenciaItem> {

    private Date fecha;
    private String accion;
    private String usuario;
    private String observaciones;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    @Override
    public int compareTo(HistoricoActuacionAsistenciaItem that) {
        if (this.getFecha() == null && that.getFecha() == null) {
            return 0;
        } else if (this.getFecha() == null) {
            return 1;
        } else if (that.getFecha() == null) {
            return -1;
        } else {
            return that.getFecha().compareTo(this.getFecha());
        }
    }
}
