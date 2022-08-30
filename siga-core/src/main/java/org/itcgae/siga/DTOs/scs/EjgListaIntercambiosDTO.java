package org.itcgae.siga.DTOs.scs;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EjgListaIntercambiosDTO {
    private List<EjgListaIntercambiosItem> ejgListaIntercambiosItems = new ArrayList<>();
    private Error error = null;

    public List<EjgListaIntercambiosItem> getEjgListaIntercambiosItems() {
        return ejgListaIntercambiosItems;
    }

    public void setEjgListaIntercambiosItems(List<EjgListaIntercambiosItem> ejgListaIntercambiosItems) {
        this.ejgListaIntercambiosItems = ejgListaIntercambiosItems;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EjgListaIntercambiosDTO that = (EjgListaIntercambiosDTO) o;
        return Objects.equals(ejgListaIntercambiosItems, that.ejgListaIntercambiosItems) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ejgListaIntercambiosItems, error);
    }

    @Override
    public String toString() {
        return "EjgListaIntercambiosDTO{" +
                "ejgListaIntercambiosItems=" + ejgListaIntercambiosItems +
                ", error=" + error +
                '}';
    }
}
