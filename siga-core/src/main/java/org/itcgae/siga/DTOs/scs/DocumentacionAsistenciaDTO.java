package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class DocumentacionAsistenciaDTO {

    private List<DocumentacionAsistenciaItem> documentacionAsistenciaItems = new ArrayList<DocumentacionAsistenciaItem>();
    private Error error = null;

    public DocumentacionAsistenciaDTO documentacionAsistenciaItems(List<DocumentacionAsistenciaItem> documentacionAsistenciaItems) {
        this.documentacionAsistenciaItems = documentacionAsistenciaItems;
        return this;
    }

    @JsonProperty("documentacionAsistenciaItems")
    public List<DocumentacionAsistenciaItem> getDocumentacionAsistenciaItems() {
        return documentacionAsistenciaItems;
    }

    public void setDocumentacionAsistenciaItems(List<DocumentacionAsistenciaItem> documentacionAsistenciaItems) {
        this.documentacionAsistenciaItems = documentacionAsistenciaItems;
    }

    public DocumentacionAsistenciaDTO error(Error error) {
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
