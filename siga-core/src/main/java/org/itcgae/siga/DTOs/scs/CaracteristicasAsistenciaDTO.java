package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class CaracteristicasAsistenciaDTO {

    private List<CaracteristicasAsistenciaItem> caracteristicasAsistenciaItems = new ArrayList<CaracteristicasAsistenciaItem>();
    private Error error = null;

    public CaracteristicasAsistenciaDTO documentacionAsistenciaItems(List<CaracteristicasAsistenciaItem> caracteristicasAsistenciaItems) {
        this.caracteristicasAsistenciaItems = caracteristicasAsistenciaItems;
        return this;
    }

    @JsonProperty("caracteristicasAsistenciaItems")
    public List<CaracteristicasAsistenciaItem> getCaracteristicasAsistenciaItems() {
        return caracteristicasAsistenciaItems;
    }

    public void setCaracteristicasAsistenciaItems(List<CaracteristicasAsistenciaItem> caracteristicasAsistenciaItems) {
        this.caracteristicasAsistenciaItems = caracteristicasAsistenciaItems;
    }

    public CaracteristicasAsistenciaDTO error(Error error) {
        this.error = error;
        return this;
    }

    @JsonProperty("error")
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}
