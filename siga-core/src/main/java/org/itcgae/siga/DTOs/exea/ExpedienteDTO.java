package org.itcgae.siga.DTOs.exea;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class ExpedienteDTO {

    private List<ExpedienteItem> expedienteItem = new ArrayList<ExpedienteItem>();
    private Error error = null;


    /**
     *
     **/
    public ExpedienteDTO expedienteItem(List<ExpedienteItem> expedienteItem) {
        this.expedienteItem = expedienteItem;
        return this;
    }

    @JsonProperty("expedienteItem")
    public List<ExpedienteItem> getExpedienteItem() {
        return expedienteItem;
    }

    public void setExpedienteItem(List<ExpedienteItem> expedienteItem) {
        this.expedienteItem = expedienteItem;
    }


    /**
     *
     **/
    public ExpedienteDTO error(Error error) {
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
