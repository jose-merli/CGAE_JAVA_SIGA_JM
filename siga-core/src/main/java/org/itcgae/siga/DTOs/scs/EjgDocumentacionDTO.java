package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EjgDocumentacionDTO {
	private List<EjgDocumentacionItem> ejgDocItems = new ArrayList<EjgDocumentacionItem>();
	 private Error error = null;
	
	 /**
	   * 
	   **/
	  public EjgDocumentacionDTO ejgDocItems(List<EjgDocumentacionItem> ejgDocItems) {
	    this.ejgDocItems = ejgDocItems;
	    return this;
	  }
	  
	  @JsonProperty("ejgDocItems")  
	  public List<EjgDocumentacionItem> getEjgDocItems() {
		return ejgDocItems;
	}
	public void setEjgDocItems(List<EjgDocumentacionItem> ejgDocItems) {
		this.ejgDocItems = ejgDocItems;
	}
	 /**
	   * 
	   **/
	  public EjgDocumentacionDTO error(Error error) {
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
	public String toString() {
		return "EjgDocumentacionObject [ejgDocItems=" + ejgDocItems + ", error=" + error + "]";
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

}
