package org.itcgae.siga.DTO.fac;

import java.util.Objects;

public class UsosSufijosItem {

    private String tipo;
    private String idSerieFacturacion;
    private String bancosCodigo;
    private String abreviatura;
    private String descripcion;
    private String idSufijo;
    private String sufijo;
    private String numPendientes;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdSerieFacturacion() {
        return idSerieFacturacion;
    }

    public void setIdSerieFacturacion(String idSerieFacturacion) {
        this.idSerieFacturacion = idSerieFacturacion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public String getNumPendientes() {
        return numPendientes;
    }

    public void setNumPendientes(String numPendientes) {
        this.numPendientes = numPendientes;
    }

    public String getIdSufijo() {
        return idSufijo;
    }

    public String getBancosCodigo() {
        return bancosCodigo;
    }

    public void setBancosCodigo(String bancosCodigo) {
        this.bancosCodigo = bancosCodigo;
    }

    public void setIdSufijo(String idSufijo) {
        this.idSufijo = idSufijo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsosSufijosItem that = (UsosSufijosItem) o;
        return Objects.equals(tipo, that.tipo) &&
                Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
                Objects.equals(bancosCodigo, that.bancosCodigo) &&
                Objects.equals(abreviatura, that.abreviatura) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(idSufijo, that.idSufijo) &&
                Objects.equals(sufijo, that.sufijo) &&
                Objects.equals(numPendientes, that.numPendientes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, idSerieFacturacion, bancosCodigo, abreviatura, descripcion, idSufijo, sufijo, numPendientes);
    }

    @Override
    public String toString() {
        return "UsosSufijosItem{" +
                "tipo='" + tipo + '\'' +
                ", idSerieFacturacion='" + idSerieFacturacion + '\'' +
                ", bancosCodigo='" + bancosCodigo + '\'' +
                ", abreviatura='" + abreviatura + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idSufijo='" + idSufijo + '\'' +
                ", sufijo='" + sufijo + '\'' +
                ", numPendientes='" + numPendientes + '\'' +
                '}';
    }
}
