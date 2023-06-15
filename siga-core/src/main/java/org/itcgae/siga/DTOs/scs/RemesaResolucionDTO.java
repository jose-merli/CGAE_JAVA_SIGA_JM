package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemesaResolucionDTO {

	private List<RemesasResolucionItem> remesasResolucionItem = new ArrayList<RemesasResolucionItem>();
	private Error error = null;
	

	@JsonProperty("remesasResolucionItem")
	public List<RemesasResolucionItem> getRemesasResolucionItem() {
		return remesasResolucionItem;
	}

	public void setRemesasResolucionItem(List<RemesasResolucionItem> remesasResolucionItem) {
		this.remesasResolucionItem = remesasResolucionItem;
	}

	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
}
