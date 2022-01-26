package org.itcgae.siga.DTOs.exea;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class DocumentacionIncorporacionDTO {

    private List<DocumentacionIncorporacionItem> documentacionIncorporacionItem = new ArrayList<DocumentacionIncorporacionItem>();
    private Error error = null;


    /**
     *
     **/
    public DocumentacionIncorporacionDTO documentacionIncorporacionItem(List<DocumentacionIncorporacionItem> documentacionIncorporacionItem) {
        this.documentacionIncorporacionItem = documentacionIncorporacionItem;
        return this;
    }

    @JsonProperty("documentacionIncorporacionItem")
    public List<DocumentacionIncorporacionItem> getDocumentacionIncorporacionItem() {
        return documentacionIncorporacionItem;
    }

    public void setDocumentacionIncorporacionItem(List<DocumentacionIncorporacionItem> documentacionIncorporacionItem) {
        this.documentacionIncorporacionItem = documentacionIncorporacionItem;
    }


    /**
     *
     **/
    public DocumentacionIncorporacionDTO error(Error error) {
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
