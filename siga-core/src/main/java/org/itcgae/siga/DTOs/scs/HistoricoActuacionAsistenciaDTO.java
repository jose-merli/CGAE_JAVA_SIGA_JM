package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActuacionAsistenciaDTO {

    private List<HistoricoActuacionAsistenciaItem> responseItems = new ArrayList<HistoricoActuacionAsistenciaItem>();
    private Error error;
    /**
     * @return the responseItems
     */
    @JsonProperty("historicoActuacionAsistenciaItem")
    public List<HistoricoActuacionAsistenciaItem> getResponseItems() {
        return responseItems;
    }
    /**
     * @param responseItems the responseItems to set
     */
    public void setResponseItems(List<HistoricoActuacionAsistenciaItem> responseItems) {
        this.responseItems = responseItems;
    }
    /**
     * @return the error
     */
    @JsonProperty("error")
    public Error getError() {
        return error;
    }
    /**
     * @param error the error to set
     */
    public void setError(Error error) {
        this.error = error;
    }
}
