package org.itcgae.siga.DTOs.com;
import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DocumentosPlantillaDTO {
	
	private List<DocumentoPlantillaItem> documentoPlantillaItem = new ArrayList<DocumentoPlantillaItem>();
	private Error error = null;
		

	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<DocumentoPlantillaItem> getDocumentoPlantillaItem() {
		return documentoPlantillaItem;
	}
	public void setDocumentoPlantillaItem(List<DocumentoPlantillaItem> documentoPlantillaItem) {
		this.documentoPlantillaItem = documentoPlantillaItem;
	}
	
}
