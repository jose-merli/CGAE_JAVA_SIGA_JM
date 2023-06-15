package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ConceptoPagoDTO {

	private List<ConceptoPagoItem> listaConceptos = new ArrayList<ConceptoPagoItem>();
	private Error error = null;

	public List<ConceptoPagoItem> getListaConceptos() {
		return listaConceptos;
	}

	public void setListaConceptos(List<ConceptoPagoItem> listaConceptos) {
		this.listaConceptos = listaConceptos;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ConceptoPagoDTO [listaConceptos=" + listaConceptos + ", error=" + error + "]";
	}

}
