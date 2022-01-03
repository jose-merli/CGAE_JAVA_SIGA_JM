package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;


public class ListaMonederoDTO {
    private List<ListaMonederosItem> monederoItems = new ArrayList<>();
    private Error error = null;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<ListaMonederosItem> getMonederoItems() {
		return monederoItems;
	}
	public void setMonederoItems(List<ListaMonederosItem> monederoItems) {
		this.monederoItems = monederoItems;
	}
}
