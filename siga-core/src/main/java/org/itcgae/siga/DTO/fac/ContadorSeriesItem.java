package org.itcgae.siga.DTO.fac;

import java.util.Objects;

public class ContadorSeriesItem {

    private String idSerieFacturacion;
    private Boolean facturaRectificativa;

    private String idContador;
    private String nombre;
    private String prefijo;
    private String sufijo;
    private String contador;

    public String getIdContador() {
        return idContador;
    }

    public void setIdContador(String idContador) {
        this.idContador = idContador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getIdSerieFacturacion() {
        return idSerieFacturacion;
    }

    public void setIdSerieFacturacion(String idSerieFacturacion) {
        this.idSerieFacturacion = idSerieFacturacion;
    }

    public Boolean getFacturaRectificativa() {
        return facturaRectificativa;
    }

    public void setFacturaRectificativa(Boolean facturaRectificativa) {
        this.facturaRectificativa = facturaRectificativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContadorSeriesItem that = (ContadorSeriesItem) o;
        return Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
                Objects.equals(facturaRectificativa, that.facturaRectificativa) &&
                Objects.equals(idContador, that.idContador) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(prefijo, that.prefijo) &&
                Objects.equals(sufijo, that.sufijo) &&
                Objects.equals(contador, that.contador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSerieFacturacion, facturaRectificativa, idContador, nombre, prefijo, sufijo, contador);
    }

    @Override
    public String toString() {
        return "ContadorSeriesItem{" +
                "idSerieFacturacion='" + idSerieFacturacion + '\'' +
                ", facturaRectificativa=" + facturaRectificativa +
                ", idContador='" + idContador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", prefijo='" + prefijo + '\'' +
                ", sufijo='" + sufijo + '\'' +
                ", contador='" + contador + '\'' +
                '}';
    }
}
