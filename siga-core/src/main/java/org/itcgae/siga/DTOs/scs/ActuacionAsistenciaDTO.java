package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class ActuacionAsistenciaDTO {

    private List<ActuacionAsistenciaItem> actuacionAsistenciaItems = new ArrayList<ActuacionAsistenciaItem>();
    private Error error = null;

    public ActuacionAsistenciaDTO documentacionAsistenciaItems(List<ActuacionAsistenciaItem> actuacionAsistenciaItems) {
        this.actuacionAsistenciaItems = actuacionAsistenciaItems;
        return this;
    }

    @JsonProperty("actuacionAsistenciaItems")
    public List<ActuacionAsistenciaItem> getActuacionAsistenciaItems() {
        return actuacionAsistenciaItems;
    }

    public void setActuacionAsistenciaItems(List<ActuacionAsistenciaItem> actuacionAsistenciaItems) {
        this.actuacionAsistenciaItems = actuacionAsistenciaItems;
    }

    public ActuacionAsistenciaDTO error(Error error) {
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
