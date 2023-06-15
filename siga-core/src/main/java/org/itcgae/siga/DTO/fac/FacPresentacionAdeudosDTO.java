package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacPresentacionAdeudosDTO {
	private List<FacPresentacionAdeudosItem> facPresentacionAdeudosItems = new ArrayList<>();
	private Error error = null;
	
	public List<FacPresentacionAdeudosItem> getFacPresentacionAdeudosItems() {
		return facPresentacionAdeudosItems;
	}
	
	public void setFacPresentacionAdeudosItems(List<FacPresentacionAdeudosItem> FacPresentacionAdeudosItems) {
		this.facPresentacionAdeudosItems = FacPresentacionAdeudosItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, facPresentacionAdeudosItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacPresentacionAdeudosDTO other = (FacPresentacionAdeudosDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(facPresentacionAdeudosItems, other.facPresentacionAdeudosItems);
	}
	
	@Override
	public String toString() {
		return "FacPresentacionAdeudosDTO [FacPresentacionAdeudosItems=" + facPresentacionAdeudosItems + ", error=" + error + "]";
	}
	
}
