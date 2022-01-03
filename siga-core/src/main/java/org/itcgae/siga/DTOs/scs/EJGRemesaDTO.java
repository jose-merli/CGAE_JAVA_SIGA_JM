package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EJGRemesaDTO {
	private List<EJGRemesaItem> ejgRemesa = new ArrayList<EJGRemesaItem>();
	private Error error = null;
	
	@JsonProperty("ejgRemesa")
	public List<EJGRemesaItem> getRemesasItem() {
		return ejgRemesa;
	}
	
	public void setEJGRemesaItem(List<EJGRemesaItem> ejgRemesa) {
		this.ejgRemesa = ejgRemesa;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
}
