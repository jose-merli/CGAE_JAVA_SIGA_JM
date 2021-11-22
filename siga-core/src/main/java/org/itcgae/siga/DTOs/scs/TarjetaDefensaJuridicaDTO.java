package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class TarjetaDefensaJuridicaDTO {

    private List<TarjetaDefensaJuridicaItem> responseItems = new ArrayList<TarjetaDefensaJuridicaItem>();
    private Error error;
    /**
     * @return the responseItems
     */
    @JsonProperty("tarjetaDefensaJuridicaItems")
    public List<TarjetaDefensaJuridicaItem> getResponseItems() {
        return responseItems;
    }
    /**
     * @param responseItems the responseItems to set
     */
    public void setResponseItems(List<TarjetaDefensaJuridicaItem> responseItems) {
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
