package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class ListaGuardiasItem {

    private String idLista;
    private String nombre;
    private String lugar;
    private String idTipo;
    private String idGrupoZona;
    private String idZona;
    private String observaciones;
    private String tipoDesc;

    public String getTipoDesc() {
        return tipoDesc;
    }

    public void setTipoDesc(String tipoDesc) {
        this.tipoDesc = tipoDesc;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {
        this.idLista = idLista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getIdGrupoZona() {
        return idGrupoZona;
    }

    public void setIdGrupoZona(String idGrupoZona) {
        this.idGrupoZona = idGrupoZona;
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaGuardiasItem that = (ListaGuardiasItem) o;
        return Objects.equals(getIdLista(), that.getIdLista()) &&
                Objects.equals(getNombre(), that.getNombre()) &&
                Objects.equals(getLugar(), that.getLugar()) &&
                Objects.equals(getIdTipo(), that.getIdTipo()) &&
                Objects.equals(getIdGrupoZona(), that.getIdGrupoZona()) &&
                Objects.equals(getIdZona(), that.getIdZona());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdLista(), getNombre(), getLugar(), getIdTipo(), getIdGrupoZona(), getIdZona());
    }

    @Override
    public String toString() {
        return "ListaGuardiasItem{" +
                "idLista='" + idLista + '\'' +
                ", nombre='" + nombre + '\'' +
                ", lugar='" + lugar + '\'' +
                ", idTipo='" + idTipo + '\'' +
                ", idGrupoZona='" + idGrupoZona + '\'' +
                ", idZona='" + idZona + '\'' +
                '}';
    }
}
