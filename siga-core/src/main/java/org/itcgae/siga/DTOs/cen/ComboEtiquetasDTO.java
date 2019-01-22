package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboEtiquetasDTO {

	private List<ComboEtiquetasItem> comboEtiquetasItems = new ArrayList<ComboEtiquetasItem>();
	private Error error = null;
	
	@JsonProperty("comboEtiquetasItems")
	public List<ComboEtiquetasItem> getComboEtiquetasItems() {
		return comboEtiquetasItems;
	}
	public void setComboEtiquetasItems(List<ComboEtiquetasItem> comboEtiquetasItems) {
		this.comboEtiquetasItems = comboEtiquetasItems;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
