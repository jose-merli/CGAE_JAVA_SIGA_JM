package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboPreciosSuscripcionDTO {
	private List<ComboPreciosSuscripcionItem> preciosSuscripcionItem = new ArrayList<ComboPreciosSuscripcionItem>();
	private Error error = null;
	
	@JsonProperty("preciosSuscripcionItem")
	public List<ComboPreciosSuscripcionItem> getPreciosSuscripcionItem() {
		return preciosSuscripcionItem;
	}
	
	public void setPreciosSuscripcionItem(List<ComboPreciosSuscripcionItem> preciosSuscripcionItem) {
		this.preciosSuscripcionItem = preciosSuscripcionItem;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}

}
