package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EcomOperacionTipoaccionDTO {
	
	private List<EcomOperacionTipoaccion> ecomOperacionTipoaccion = new ArrayList<EcomOperacionTipoaccion>();
	private Error error = null;
	private String status = new String();
	
	@JsonProperty("ecomOperacionTipoaccion")
	public void setEcomOperacionTipoaccion(List<EcomOperacionTipoaccion> ecomOperacionTipoaccion) {
		this.ecomOperacionTipoaccion = ecomOperacionTipoaccion;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public List<EcomOperacionTipoaccion> getEcomOperacionTipoaccion() {
		return ecomOperacionTipoaccion;
	}

	  @JsonProperty("status")
	  public String getStatus() {
	    return status;
	  }
	  public void setStatus(String status) {
	    this.status = status;
	  }

}
