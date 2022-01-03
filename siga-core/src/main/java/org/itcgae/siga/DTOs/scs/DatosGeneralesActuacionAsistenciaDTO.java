package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class DatosGeneralesActuacionAsistenciaDTO {

    private List<DatosGeneralesActuacionAsistenciaItem> datosGeneralesActuacionAsistenciaItems = new ArrayList<DatosGeneralesActuacionAsistenciaItem>();
    private Error error = null;

    public DatosGeneralesActuacionAsistenciaDTO documentacionAsistenciaItems(List<DatosGeneralesActuacionAsistenciaItem> datosGeneralesActuacionAsistenciaItems) {
        this.datosGeneralesActuacionAsistenciaItems = datosGeneralesActuacionAsistenciaItems;
        return this;
    }

    @JsonProperty("datosGeneralesActuacionAsistenciaItems")
    public List<DatosGeneralesActuacionAsistenciaItem> getDatosGeneralesActuacionAsistenciaItems() {
        return datosGeneralesActuacionAsistenciaItems;
    }

    public void setDatosGeneralesActuacionAsistenciaItems(List<DatosGeneralesActuacionAsistenciaItem> datosGeneralesActuacionAsistenciaItems) {
        this.datosGeneralesActuacionAsistenciaItems = datosGeneralesActuacionAsistenciaItems;
    }

    public DatosGeneralesActuacionAsistenciaDTO error(Error error) {
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
