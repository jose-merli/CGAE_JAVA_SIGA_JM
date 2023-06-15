package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DocumentoDesignaDTO {

	private List<DocumentoDesignaItem> listaDocumentoDesignaItem = new ArrayList<DocumentoDesignaItem>();
	private Error error = null;

	/**
	 * @return the listaDocumentoDesignaItem
	 */
	public List<DocumentoDesignaItem> getListaDocumentoDesignaItem() {
		return listaDocumentoDesignaItem;
	}

	/**
	 * @param listaDocumentoDesignaItem the listaDocumentoDesignaItem to set
	 */
	public void setListaDocumentoDesignaItem(List<DocumentoDesignaItem> listaDocumentoDesignaItem) {
		this.listaDocumentoDesignaItem = listaDocumentoDesignaItem;
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
		return "DocumentoDesignaDTO [listaDocumentoDesignaItem=" + listaDocumentoDesignaItem + ", error=" + error + "]";
	}

}
