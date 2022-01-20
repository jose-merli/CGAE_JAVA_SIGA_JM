package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.List;
import java.util.Objects;

public class UsosSufijosDTO {
    private String status = new String();
    private Error error = null;
    private List<UsosSufijosItem> usosSufijosItems;

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

    public List<UsosSufijosItem> getUsosSufijosItems() {
        return usosSufijosItems;
    }

    public void setUsosSufijosItems(List<UsosSufijosItem> usosSufijosItems) {
        this.usosSufijosItems = usosSufijosItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsosSufijosDTO that = (UsosSufijosDTO) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(usosSufijosItems, that.usosSufijosItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, usosSufijosItems);
    }

    @Override
    public String toString() {
        return "UsosSufijosDTO{" +
                "status='" + status + '\'' +
                ", error=" + error +
                ", usosSufijosItems=" + usosSufijosItems +
                '}';
    }
}
