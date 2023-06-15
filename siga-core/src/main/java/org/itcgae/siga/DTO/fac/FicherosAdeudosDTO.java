package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class FicherosAdeudosDTO {
	private List<FicherosAdeudosItem> ficherosAdeudosItems = new ArrayList<>();
	private Error error = null;
	
	public List<FicherosAdeudosItem> getFicherosAdeudosItems() {
		return ficherosAdeudosItems;
	}
	
	public void setFicherosAdeudosItems(List<FicherosAdeudosItem> ficherosAdeudosItems) {
		this.ficherosAdeudosItems = ficherosAdeudosItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, ficherosAdeudosItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FicherosAdeudosDTO other = (FicherosAdeudosDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(ficherosAdeudosItems, other.ficherosAdeudosItems);
	}
	
	@Override
	public String toString() {
		return "FicherosAdeudosDTO [ficherosAdeudosItems=" + ficherosAdeudosItems + ", error=" + error + "]";
	}
	
}
