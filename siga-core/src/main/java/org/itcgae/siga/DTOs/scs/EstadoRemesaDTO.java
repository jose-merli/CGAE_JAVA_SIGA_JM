package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstadoRemesaDTO {
	
	private List<EstadoRemesaItem> estadoRemesaItem = new ArrayList<EstadoRemesaItem>();
	private Error error = null;
	
	@JsonProperty("estadoRemesaItem")
	public List<EstadoRemesaItem> getEstadoRemesaItem() {
		return estadoRemesaItem;
	}
	
	public void setEstadoRemesaItem(List<EstadoRemesaItem> estadoRemesaItem) {
		this.estadoRemesaItem = estadoRemesaItem;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
}
