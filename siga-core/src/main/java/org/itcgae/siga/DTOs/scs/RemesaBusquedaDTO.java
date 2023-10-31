package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemesaBusquedaDTO {
	
	private List<RemesasItem> remesasItem = new ArrayList<RemesasItem>();
	private Error error = null;
	
	@JsonProperty("remesasItems")
	public List<RemesasItem> getRemesasItem() {
		return remesasItem;
	}
	
	public void setRemesasItem(List<RemesasItem> remesasItems) {
		this.remesasItem = remesasItems;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}

}
