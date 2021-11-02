package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.List;
import java.util.Objects;

public class ContadorSeriesDTO {

    private List<ContadorSeriesItem> contadorSeriesItems;
    private Error error = null;

    public List<ContadorSeriesItem> getContadorSeriesItems() {
        return contadorSeriesItems;
    }

    public void setContadorSeriesItems(List<ContadorSeriesItem> contadorSeriesItems) {
        this.contadorSeriesItems = contadorSeriesItems;
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
        ContadorSeriesDTO that = (ContadorSeriesDTO) o;
        return Objects.equals(contadorSeriesItems, that.contadorSeriesItems) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contadorSeriesItems, error);
    }

    @Override
    public String toString() {
        return "ContadorSeriesDTO{" +
                "contadorSeriesItems=" + contadorSeriesItems +
                ", error=" + error +
                '}';
    }

}
