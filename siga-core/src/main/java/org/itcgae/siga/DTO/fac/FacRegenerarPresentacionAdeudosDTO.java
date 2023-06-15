package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class FacRegenerarPresentacionAdeudosDTO {
	private List<FacRegenerarPresentacionAdeudosItem> facRegenerarPresentacionAdeudosItems = new ArrayList<>();
	private Error error = null;
	
	public List<FacRegenerarPresentacionAdeudosItem> getFacRegenerarPresentacionAdeudosItems() {
		return facRegenerarPresentacionAdeudosItems;
	}

	public void setFacRegenerarPresentacionAdeudosItems(
			List<FacRegenerarPresentacionAdeudosItem> facRegenerarPresentacionAdeudosItems) {
		this.facRegenerarPresentacionAdeudosItems = facRegenerarPresentacionAdeudosItems;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		return Objects.hash(error, facRegenerarPresentacionAdeudosItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacRegenerarPresentacionAdeudosDTO other = (FacRegenerarPresentacionAdeudosDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(facRegenerarPresentacionAdeudosItems, other.facRegenerarPresentacionAdeudosItems);
	}
	
	@Override
	public String toString() {
		return "FacRRegenerarPresentacionAdeudosDTO [FacRegenerarPresentacionAdeudosItems=" + facRegenerarPresentacionAdeudosItems + ", error=" + error + "]";
	}
	
}
