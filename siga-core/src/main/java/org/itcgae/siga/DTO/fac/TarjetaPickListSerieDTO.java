package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.List;
import java.util.Objects;

public class TarjetaPickListSerieDTO {
    private String status = new String();
    private Error error = null;

    private String idSerieFacturacion;
    private List<ComboItem> seleccionados;
    private List<ComboItem> noSeleccionados;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getIdSerieFacturacion() {
        return idSerieFacturacion;
    }

    public void setIdSerieFacturacion(String idSerieFacturacion) {
        this.idSerieFacturacion = idSerieFacturacion;
    }

    public List<ComboItem> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<ComboItem> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public List<ComboItem> getNoSeleccionados() {
        return noSeleccionados;
    }

    public void setNoSeleccionados(List<ComboItem> noSeleccionados) {
        this.noSeleccionados = noSeleccionados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarjetaPickListSerieDTO that = (TarjetaPickListSerieDTO) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
                Objects.equals(seleccionados, that.seleccionados) &&
                Objects.equals(noSeleccionados, that.noSeleccionados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, idSerieFacturacion, seleccionados, noSeleccionados);
    }

    @Override
    public String toString() {
        return "TarjetaPickListSerieDTO{" +
                "status='" + status + '\'' +
                ", error=" + error +
                ", idSerieFacturacion='" + idSerieFacturacion + '\'' +
                ", seleccionados=" + seleccionados +
                ", noSeleccionados=" + noSeleccionados +
                '}';
    }
}
