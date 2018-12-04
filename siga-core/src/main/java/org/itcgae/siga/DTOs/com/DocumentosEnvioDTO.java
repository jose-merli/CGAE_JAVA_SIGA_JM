package org.itcgae.siga.DTOs.com;
import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DocumentosEnvioDTO {
	
	private List<DocumentoEnvioItem> documentoEnvioItem = new ArrayList<DocumentoEnvioItem>();
	private Error error = null;
	
	
	public List<DocumentoEnvioItem> getDocumentoEnvioItem() {
		return documentoEnvioItem;
	}
	public void setDocumentoEnvioItem(List<DocumentoEnvioItem> documentoEnvioItem) {
		this.documentoEnvioItem = documentoEnvioItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
}
