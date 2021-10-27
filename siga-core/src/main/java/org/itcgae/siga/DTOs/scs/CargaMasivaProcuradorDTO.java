package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargaMasivaProcuradorDTO {
	
	private List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem = new ArrayList<CargaMasivaProcuradorItem>();
	private Error error = null;
	
	@JsonProperty("cargaMasivaProcuradorItem")
	public List<CargaMasivaProcuradorItem> getCenCargaMasivaItem() {
		return cargaMasivaProcuradorItem;
	}
	
	public void setCargaMasivaProcuradorItem(List<CargaMasivaProcuradorItem> cargaMasivaProcuradorItem) {
		this.cargaMasivaProcuradorItem = cargaMasivaProcuradorItem;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
}
