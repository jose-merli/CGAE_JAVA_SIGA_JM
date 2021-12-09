package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IVADTO {
	private List<IVAItem> ivaItems = new ArrayList<>();
	private Error error = null;
	
	public List<IVAItem> getIVAItems() {
		return ivaItems;
	}
	
	public void setIVAItems(List<IVAItem> ivaItems) {
		this.ivaItems = ivaItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, ivaItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IVADTO other = (IVADTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(ivaItems, other.ivaItems);
	}
	
	@Override
	public String toString() {
		return "IVADTO [IVAItem=" + ivaItems + ", error=" + error + "]";
	}
	
}
