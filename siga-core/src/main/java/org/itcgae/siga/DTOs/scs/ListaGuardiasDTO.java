package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaGuardiasDTO {

    private List<ListaGuardiasItem> listaGuardiasItems = new ArrayList<ListaGuardiasItem>();
    private org.itcgae.siga.DTOs.gen.Error error = null;


    @JsonProperty("listaGuardiasItems")
    public List<ListaGuardiasItem> getListaGuardiasItems() {
        return listaGuardiasItems;
    }

    public void setListaGuardiasItems(List<ListaGuardiasItem> listaGuardiasItems) {
        this.listaGuardiasItems = listaGuardiasItems;
    }

    @JsonProperty("error")
    public org.itcgae.siga.DTOs.gen.Error getError() {
        return error;
    }
    public void setError(org.itcgae.siga.DTOs.gen.Error error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaGuardiasDTO that = (ListaGuardiasDTO) o;
        return Objects.equals(getListaGuardiasItems(), that.getListaGuardiasItems()) &&
                Objects.equals(getError(), that.getError());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListaGuardiasItems(), getError());
    }

    @Override
    public String toString() {
        return "ListaGuardiasDTO{" +
                "listaGuardiasItems=" + listaGuardiasItems +
                ", error=" + error +
                '}';
    }
}
