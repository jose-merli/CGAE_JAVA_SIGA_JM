package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DocumentoActDesignaDTO {

	List<DocumentoActDesignaItem> listaDocumentoActDesignaItem = new ArrayList<DocumentoActDesignaItem>();
	Error error = null;

	/**
	 * @return the listaDocumentoActDesignaItem
	 */
	public List<DocumentoActDesignaItem> getListaDocumentoActDesignaItem() {
		return listaDocumentoActDesignaItem;
	}

	/**
	 * @param listaDocumentoActDesignaItem the listaDocumentoActDesignaItem to set
	 */
	public void setListaDocumentoActDesignaItem(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem) {
		this.listaDocumentoActDesignaItem = listaDocumentoActDesignaItem;
	}

	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "DocumentoActDesignaDTO [listaDocumentoActDesignaItem=" + listaDocumentoActDesignaItem + ", error="
				+ error + "]";
	}

}
