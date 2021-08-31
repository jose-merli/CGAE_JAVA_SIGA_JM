package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstadoEjgDTO {
	private List<EstadoEjgItem> estadoEjgItems = new ArrayList<EstadoEjgItem>();
	private Error error = null;
	  

	public EstadoEjgDTO estadoEjgItems(List<EstadoEjgItem> estadoEjgItems) {
	    this.estadoEjgItems = estadoEjgItems;
	    return this;
	}
	  
	@JsonProperty("estadoEjgItems")  
	public List<EstadoEjgItem> getEstadoEjgItems() {
		return estadoEjgItems;
	}
	public void setEstadoEjgItems(List<EstadoEjgItem> estadoEjgItems) {
		this.estadoEjgItems = estadoEjgItems;
	}
	
	public EstadoEjgDTO error(Error error) {
	    this.error = error;
	    return this;
	  }
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
