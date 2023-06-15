package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.List;
import java.util.Objects;

public class FacFacturacionprogramadaDTO {

    private Error error = null;
    private String message = null;
    private List<FacFacturacionprogramadaItem> facturacionprogramadaItems;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FacFacturacionprogramadaItem> getFacturacionprogramadaItems() {
        return facturacionprogramadaItems;
    }

    public void setFacturacionprogramadaItems(List<FacFacturacionprogramadaItem> facturacionprogramadaItems) {
        this.facturacionprogramadaItems = facturacionprogramadaItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacFacturacionprogramadaDTO that = (FacFacturacionprogramadaDTO) o;
        return Objects.equals(error, that.error) &&
                Objects.equals(message, that.message) &&
                Objects.equals(facturacionprogramadaItems, that.facturacionprogramadaItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, message, facturacionprogramadaItems);
    }

    @Override
    public String toString() {
        return "FacFacturacionprogramadaDTO{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", facturacionprogramadaItems=" + facturacionprogramadaItems +
                '}';
    }

}
