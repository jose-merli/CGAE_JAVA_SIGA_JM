package org.itcgae.siga.DTOs.scs;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class FichaSojDTO {
    private List<FichaSojItem> fichaSojItems = new ArrayList<FichaSojItem>();
    private Error error = null;

    public List<FichaSojItem> getFichaSojItems() {
        return fichaSojItems;
    }

    public void setFichaSojItems(List<FichaSojItem> fichaSojItems) {
        this.fichaSojItems = fichaSojItems;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
