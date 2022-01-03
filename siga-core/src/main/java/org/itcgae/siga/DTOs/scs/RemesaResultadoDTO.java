package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemesaResultadoDTO {
	
	private List<RemesasResultadoItem> remesasResultadosItem = new ArrayList<RemesasResultadoItem>();
	private Error error = null;
	
	@JsonProperty("remesasResultadosItems")
	public List<RemesasResultadoItem> getRemesasResultadosItem() {
		return remesasResultadosItem;
	}
	
	public void setRemesasResultadosItem(List<RemesasResultadoItem> remesasResultadosItems) {
		this.remesasResultadosItem = remesasResultadosItems;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}

}
