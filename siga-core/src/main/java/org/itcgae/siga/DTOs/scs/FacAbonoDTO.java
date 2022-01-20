package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class FacAbonoDTO {
	private List<FacAbonoItem>  listaFacAbonoItem = new ArrayList<FacAbonoItem>();
	private Error error = null;
	
	public List<FacAbonoItem> getListaFacAbonoItem() {
		return listaFacAbonoItem;
	}
	public void setListaFacAbonoItem(List<FacAbonoItem> listaFacAbonoItem) {
		this.listaFacAbonoItem = listaFacAbonoItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	

	
	
}
