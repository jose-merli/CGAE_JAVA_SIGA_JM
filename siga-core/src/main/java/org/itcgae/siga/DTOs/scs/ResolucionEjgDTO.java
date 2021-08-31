package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResolucionEjgDTO {
	  private List<ResolucionEJGItem> resolucionItems = new ArrayList<ResolucionEJGItem>();
	  private Error error = null;
	
	  /**
	   * 
	   **/
	  public ResolucionEjgDTO resolucionItems(List<ResolucionEJGItem> resolucionItems) {
	    this.resolucionItems = resolucionItems;
	    return this;
	  }
	  
	  @JsonProperty("resolucionItems")	  
	  public List<ResolucionEJGItem> getResolucionItems() {
		return resolucionItems;
	}
	public void setResolucionItems(List<ResolucionEJGItem> resolucionItems) {
		this.resolucionItems = resolucionItems;
	}
	
	 /**
	   * 
	   **/
	  public ResolucionEjgDTO error(Error error) {
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
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
}
